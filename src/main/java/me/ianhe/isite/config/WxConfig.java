package me.ianhe.isite.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

    @Autowired
    private SystemProperties systemProperties;

    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaDefaultConfigImpl defaultConfig = new WxMaDefaultConfigImpl();
//        defaultConfig.setAppid("wx2e2c22695f0fdf64");
//        defaultConfig.setSecret("1576b5f1c4959a915a93c6d11e2507ed");
        defaultConfig.setAppid(systemProperties.getXcxAppid());
        defaultConfig.setSecret(systemProperties.getXcxSecret());
        return defaultConfig;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig wxMaConfig) {
        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }
}
