package me.ianhe.isite.service;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import me.ianhe.isite.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
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

    @Value("${template.location:templates}")
    private String tpl;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String applyTemplate(String templateName) {
        Map<String, Object> res = Maps.newHashMap();
        return applyTemplate(templateName, res);
    }

    public String applyTemplate(String templateName, Map<String, Object> propMap) {
        if (RequestUtil.getRequest().isPresent()) {
            propMap.put("contextPath", RequestUtil.getRequest().get().getContextPath());
        }
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        try {
            File dir = new File(this.getClass().getClassLoader().getResource(tpl + File.separator + "email").getFile());
            config.setDirectoryForTemplateLoading(dir);
            Template template = config.getTemplate(templateName, StandardCharsets.UTF_8.name());
            StringWriter writer = new StringWriter();
            template.process(propMap, writer);
            return writer.toString();
        } catch (Exception e) {
            logger.warn("Error while process template：{} ", templateName, e);
            return "";
        }
    }

}
