package com.anyun.cloud.common.context;

import com.anyun.cloud.common.FileUtil;
import com.anyun.cloud.common.tools.db.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 系统环境变量
 */
public class SystemEnvironment extends AbstractEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemEnvironment.class);
    public static final String DB_VALIDATION_SQL = "VALIDATION_SQL";
    public static final String DB_INIT_SIZE = "DB_INIT_SIZE";
    public static final String DB_MAX_SIZE = "DB_MAX_SIZE";
    public static final String DB_VALIDATION_TIMEOUT = "DB_VALIDATION_TIMEOUT";
    public static final String DB_JDBC_URI = "DB_JDBC_URI";
    public static final String DB_JDBC_USR = "DB_JDBC_USR";
    public static final String DB_JDBC_PWD = "DB_JDBC_PWD";
    public static final String DB_JDBC_DRIVER = "DB_JDBC_DRIVER";
    public static final String ETCD_URL = "ETCD_URL";
    public static final String NATS_URL = "NATS_URL";
    public static final String ELASTICSEARCH_URL = "ELASTICSEARCH_URL";
    public static final String ELASTICSEARCH_USER_NAME = "ELASTICSEARCH_USER_NAME";
    public static final String ELASTICSEARCH_PASSWORD = "ELASTICSEARCH_PASSWORD";


    /**
     * 定义map 存储系统环境变量
     */
    private Map<String, String> sysenv = new HashMap<>();

    public SystemEnvironment() {
        this.sysenv.putAll(System.getenv());
    }

    /**
     * 放入系统环境配置
     */
    public SystemEnvironment(Map<String, String> sysenv) {
        this.sysenv = sysenv;
    }

    /**
     * 获取系统环境配置
     */
    public Map<String, String> getSysenv() {
        return sysenv;
    }

    /**
     * 获取成员变量
     */
    public String getEnv(String name) {
        return sysenv.get(name);
    }


    /**
     * 从配置文件 加载 系统配置信息
     */
    public static SystemEnvironment getSystemEnvironment() throws Exception {
        String userHome = System.getProperty("user.home");
        LOGGER.debug("userHome:[{}]", userHome);
        String defaultSystemPropertiesPath = userHome + "/system.properties";
        LOGGER.debug("defaultSystemPropertiesPath:[{}]", defaultSystemPropertiesPath);
        File defaultSystemPropertiesFile = new File(defaultSystemPropertiesPath);
        String content = FileUtil.cat(defaultSystemPropertiesFile, "UTF-8");
        if (null == content || "".equals(content.trim())) {
            throw new Exception("配置文件为空!");
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(defaultSystemPropertiesFile));
        } catch (IOException e) {
            LOGGER.debug(e.getMessage() + "加载配置文件到 Properties 失败！");
            throw new Exception(e.getMessage() + "加载配置文件到 Properties 失败！");
        }

        Map<String, String> sysenv = new HashMap<>();
        properties.forEach((k, v) -> {
            sysenv.put(k.toString(), v.toString());
            LOGGER.debug("READ  PROPERITS ===> key:[{}]" + "\t" + "value:[{}]", k, v);
        });
        SystemEnvironment environment = new SystemEnvironment(sysenv);
        return environment;
    }

    /**
     * 添加 api类 到jetty
     *
     * @param packageName  包名
     * @param childPackage 是否扫描子包
     * @return getProviderNames
     */
    public static String getProviderNames(String packageName, boolean childPackage) {
        //api包路径
        List<String> packageNames = FileUtil.getClassName(packageName, childPackage);
        LOGGER.debug("List<String> packageNames:[{}]", packageNames);
        if (packageNames != null && packageNames.size() > 0) {
            LOGGER.debug("packageNames size:[{}]", packageNames.size());
        } else {
            LOGGER.debug("packageNames size:", 0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int number = 1;
        for (String name : packageNames) {
            stringBuilder.append(name).append("\n");
            LOGGER.debug("number:[{}]------ClassName:[{}]", number++, name);
        }
        return stringBuilder.toString();
    }
}
