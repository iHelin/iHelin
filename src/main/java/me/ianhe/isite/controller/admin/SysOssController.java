package me.ianhe.isite.controller.admin;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.PolicyConditions;
import com.google.common.collect.Maps;
import me.ianhe.isite.config.SystemProperties;
import me.ianhe.isite.pojo.oss.CloudStorageConfig;
import me.ianhe.isite.pojo.validator.AliyunGroup;
import me.ianhe.isite.service.SysConfigService;
import me.ianhe.isite.utils.Constant;
import me.ianhe.isite.utils.R;
import me.ianhe.isite.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 *
 * @author iHelin ihelin@outlook.com
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {

    @Autowired
    private OSS ossClient;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SystemProperties systemProperties;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public R config() {
        CloudStorageConfig config = sysConfigService.getConfigObject(Constant.CLOUD_STORAGE_CONFIG_KEY,
                CloudStorageConfig.class);
        return R.ok().put("config", config);
    }


    /**
     * 保存云存储配置信息
     */
    @PostMapping("/saveConfig")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public R saveConfig(@RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);

        //校验阿里云数据
        ValidatorUtils.validateEntity(config, AliyunGroup.class);

        sysConfigService.updateValueByKey(Constant.CLOUD_STORAGE_CONFIG_KEY, JSONUtil.toJsonStr(config));

        return R.ok();
    }

    /**
     * 服务端签名上传
     *
     * @return
     */
    @GetMapping("/policy")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public R policy() {
        Map<String, String> resultMap = Maps.newLinkedHashMap();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为2GB，即CONTENT_LENGTH_RANGE为2*1024*1024*1024。
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 2L * 1024 * 1024 * 1024);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            CloudStorageConfig config = sysConfigService.getConfigObject(Constant.CLOUD_STORAGE_CONFIG_KEY,
                    CloudStorageConfig.class);
            resultMap.put("accessKeyId", config.getAliyunAccessKeyId());
            resultMap.put("policy", encodedPolicy);
            resultMap.put("signature", postSignature);
            resultMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            logger.error("get policy error", e);
        }
        return R.ok(resultMap);
    }

}
