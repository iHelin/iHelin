package me.ianhe.isite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author iHelin
 * @since 2018/8/27 22:45
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ws").setViewName("webSocket");
        registry.addViewController("/test/page").setViewName("test");
    }

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new Converter<String, Date>() {
//            @Override
//            public Date convert(String source) {
//                if (!StringUtils.isEmpty(source)) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    try {
//                        return sdf.parse(source);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return null;
//            }
//        });
//    }
}
