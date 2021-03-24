package me.ianhe.isite.config.security;

import me.ianhe.isite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author iHelin
 * @since 2018/4/26 09:43
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    //    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//    @Autowired
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//    @Autowired
//    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
//    @Autowired
//    private UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private JwtFilter jwtFilter;

//    @Bean
//    public DefaultKaptcha defaultKaptcha() {
//        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
//        Properties properties = new Properties();
//        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
//        Config config = new Config(properties);
//        defaultKaptcha.setConfig(config);
//        return defaultKaptcha;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        CaptchaFilter captchaFilter = new CaptchaFilter();
//        captchaFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
//        http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public FilterSecurityInterceptor postProcess(FilterSecurityInterceptor interceptor) {
//                        interceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
//                        interceptor.setAccessDecisionManager(urlAccessDecisionManager);
//                        return interceptor;
//                    }
//                }).and()
//                .formLogin().loginPage(Constant.LOGIN_PAGE).loginProcessingUrl(Constant.LOGIN_PROCESSING_URL).permitAll()
//                .failureHandler(authenticationFailureHandler).successHandler(authenticationSuccessHandler).and()
//                .logout().permitAll().and()
//                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler);
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/login", "/logout", "/test/**", "/favicon.ico", "/wechat/login")
            .permitAll()
            .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
            .accessDeniedHandler(myAccessDeniedHandler)
            .authenticationEntryPoint(myAuthenticationEntryPoint);
    }

}
