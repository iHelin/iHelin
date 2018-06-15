package me.ianhe.isite.controller.admin;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author iHelin
 * @since 2017/11/14 15:50
 */
@RestController
@RequestMapping("/admin/commons")
public class AdminSystemController extends BaseAdminController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    /**
     * 系统属性
     *
     * @author iHelin
     * @since 2017/11/14 15:52
     */
    @GetMapping("props")
    public Map<String, Object> props() {
        Properties props = System.getProperties();
        return success(props);
    }

    /**
     * 请求路径
     *
     * @author iHelin
     * @since 2017/11/14 15:52
     */
    @GetMapping("mappings")
    public Map<String, Object> mappings() {
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        List<Map> mappingList = Lists.newArrayList();
        for (RequestMappingInfo key : map.keySet()) {
            Map<String, Object> modelMap = Maps.newHashMap();
            String urls = Joiner.on(',').join(key.getPatternsCondition().getPatterns());
            modelMap.put("url", urls);
            modelMap.put("method", Joiner.on(',').join(key.getMethodsCondition().getMethods()));
            modelMap.put("className", map.get(key).getBeanType().getName());
            modelMap.put("classMethod", map.get(key).getMethod().getName());
            mappingList.add(modelMap);
        }
        return success(mappingList);
    }
}
