package me.ianhe.config;

import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CommonConfig {

    private static final Logger logger = LoggerFactory.getLogger(CommonConfig.class);

    public static class ConfigEntry {
        public String jdbc_url;
        public String jdbc_user;
        public String jdbc_password;
        public String jdbc_driver;

        public String admin_user;
        public String admin_password;

        public String domain_url;
        public String system_name;

        public String wx_appId;
        public String wx_appSecret;
        public String mch_id;//商户号
        public String key;//微信支付的key
    }

    public static class MailConfigEntry {
        public String mail_server;
        public String mail_port;
        public String mail_user;
        public String mail_password;
        public String mail_from_address;
        public String mail_from_name;
    }

    private static ConfigEntry configEntry;

    public static MailConfigEntry mailEntry;

    private static String webappRoot;

    private static String contextPath;

    public static String getWebappRoot() {
        return webappRoot;
    }

    public static void init(String rootPath, String contextName) {
        contextPath = contextName;
        webappRoot = rootPath;
        configEntry = loadConfig(ConfigEntry.class);

        try {
            mailEntry = loadConfig("mail_config.yml", MailConfigEntry.class);
        } catch (RuntimeException e) {
            logger.info("mail not configured", e);
        }

        if (configEntry == null || configEntry.domain_url == null) {
            throw new RuntimeException("Can not find domain_url in the config.yml.");
        }

        if (!configEntry.domain_url.startsWith("http")) {
            configEntry.domain_url = "http://" + configEntry.domain_url;
        }

        if (configEntry.domain_url.endsWith("/")) {
            configEntry.domain_url = configEntry.domain_url.substring(0, configEntry.domain_url.length() - 1);
        }
    }

    public static String getDomainUrl() {
        return configEntry.domain_url;
    }

    public static String getContextUrl() {
        if (contextPath.endsWith("/")) {
            return configEntry.domain_url + contextPath;
        } else {
            return configEntry.domain_url + contextPath + "/";
        }
    }

    public static String getContextPath() {
        return contextPath;
    }

    public static String getAdminUser() {
        return configEntry.admin_user;
    }

    public static String getAdminPassword() {
        return configEntry.admin_password;
    }

    public static String getDBUrl() {
        return configEntry.jdbc_url;
    }

    public static String getSystemName() {
        return configEntry.system_name;
    }

    public static String getDBUser() {
        return configEntry.jdbc_user;
    }

    public static String getDBPwd() {
        return configEntry.jdbc_password;
    }

    public static String getDBDriver() {
        return configEntry.jdbc_driver;
    }

    public static String getAppID() {
        return configEntry.wx_appId;
    }

    public static String getAppSecret() {
        return configEntry.wx_appSecret;
    }

    public static String getMchId() {
        return configEntry.mch_id;
    }

    public static String getKey() {
        return configEntry.key;
    }

    public static File getWebInfDir() {
        return new File(webappRoot, "WEB-INF");
    }

    public static MailConfigEntry getMailEntry() {
        return mailEntry;
    }

    public static <T> T loadConfig(String cfgFileName, Class<T> clazz) {
        return loadConfig(new File(getWebInfDir(), cfgFileName), clazz);
    }

    public static <T> T loadConfig(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null)
            throw new RuntimeException("InputStream is null.");
        try {
            return Yaml.loadType(inputStream, clazz);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can not find " + inputStream.toString());
        }
    }

    public static <T> T loadConfig(File file, Class<T> clazz) {
        if (file == null)
            throw new RuntimeException("Loading file is null.");
        try {
            return Yaml.loadType(file, clazz);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can not find " + file.getAbsolutePath());
        }
    }

    public static <T> T loadConfig(Class<T> clazz) {
        return loadConfig("config.yml", clazz);
    }
}