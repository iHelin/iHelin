package me.ianhe.isite.service;

import com.google.common.collect.Maps;
import freemarker.template.Template;
import me.ianhe.isite.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * freemarker模板方法
 * 用模板中返回HTML字符串
 *
 * @author iHelin
 * @since 2017/11/14 17:35
 */
@Service
public class TemplateService {

    @Autowired
    private FreeMarkerConfigurer configurer;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String applyTemplate(String templateName) {
        Map<String, Object> res = Maps.newHashMap();
        return applyTemplate(templateName, res);
    }

    public String applyTemplate(String templateName, Map<String, Object> propMap) {
        if (RequestUtil.getRequest().isPresent()) {
            propMap.put("contextPath", RequestUtil.getRequest().get().getContextPath());
        }
        try {
            Template template = configurer.getConfiguration().getTemplate("email/" + templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, propMap);
        } catch (Exception e) {
            logger.warn("Error while process template：{} ", templateName, e);
            return "";
        }
    }

}
