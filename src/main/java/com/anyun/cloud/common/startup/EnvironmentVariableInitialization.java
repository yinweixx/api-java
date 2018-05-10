package com.anyun.cloud.common.startup;

import com.anyun.cloud.common.Constant;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class EnvironmentVariableInitialization {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentVariableInitialization.class);

    public static void init() throws Exception {
        File managementNetworkConfigFile = new File(Constant.PATH_CFG_SYSTEM);         //读取文件
        if (!managementNetworkConfigFile.exists())
            throw new Exception("file [PATH_CFG_NET_INTERFACE_MGR] not exist");
        String content = FileUtil.cat(managementNetworkConfigFile, "utf-8");  //获取文件数据
        if (StringUtils.isEmpty(content))
            throw new Exception("file [PATH_CFG_NET_INTERFACE_MGR] not exist");
        Properties properties = new Properties();
        properties.load(new FileInputStream(managementNetworkConfigFile));
    }
}
