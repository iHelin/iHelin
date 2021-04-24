package me.ianhe.pp.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import me.ianhe.pp.pojo.oss.CloudStorageConfig;
import me.ianhe.pp.service.SysConfigService;
import me.ianhe.pp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author iHelin
 * @since 2021/3/16 13:45
 */
@Configuration
public class OSSConfig {

    @Autowired
    private SysConfigService sysConfigService;

    @Bean(destroyMethod = "shutdown")
    public OSS ossClient() {
        CloudStorageConfig config = sysConfigService.getConfigObject(Constant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        return new OSSClientBuilder()
                .build(config.getAliyunEndPoint(),
                        config.getAliyunAccessKeyId(),
                        config.getAliyunAccessKeySecret());
    }
}
