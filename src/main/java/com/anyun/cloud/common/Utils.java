package com.anyun.cloud.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by sxt on 8/28/17.
 */
public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
//    public static String getMacAddress(byte[] mac) {
//        if (mac == null)
//            return "";
//        StringBuilder sb = new StringBuilder(18);
//        for (byte b : mac) {
//            if (sb.length() > 0)
//                sb.append(':');
//            sb.append(String.format("%02x", b));
//        }
//        return sb.toString();
//    }
//
//    public static String buildSerialNumber() throws Exception{
//        /**
//         * /sys/class/net/ 该目录下为所有网卡名称
//         */
//        File file = new File("/sys/class/net/");   //获取该路径下的所有文件和目录
//        StringBuilder sb = new StringBuilder();
//        for (File iface : file.listFiles()) {               //过滤文件和目录
//            if(iface.getName().contains(":"))
//                continue;
//            if(iface.getName().equals("lo"))
//                continue;
//            String command = "ethtool -i " + iface.getName() + " | grep bus-info:";
//            /**
//             *   ethtool ethx       //查询ethx网口基本设置，其中 x 是对应网卡的编号，如eth0、eth1等等
//             ethtool –h        //显示ethtool的命令帮助(help)
//             ethtool –i ethX    //查询ethX网口的相关信息
//             ethtool –d ethX    //查询ethX网口注册性信息
//             ethtool –r ethX    //重置ethX网口到自适应模式
//             ethtool –S ethX    //查询ethX网口收发包统计
//             ethtool –s ethX [speed 10|100|1000] [duplex half|full]  [autoneg on|off]        //设置网口速率10/100/1000M、设置网口半/全双工、设置网口是否自协商
//             ethtool -E eth0 magic 0x10798086 offset 0x10 value 0x1A  修改网卡EEPROM内容（0x1079 网卡device id , 0x8086网卡verdor id  ）
//             ethtool -e eth0  : dump网卡EEPROM内容
//             */
//            BashCommand linuxCommand = new BashCommand(command);
//            String result = linuxCommand.exec().trim();
//            if(linuxCommand.getException() != null)
//                continue;
//            if(linuxCommand.getResult().getExitValue() != 0)
//                continue;
//            String[] netInfo = StringUtils.getSplitValues(result,":");
//            if(netInfo.length < 2)
//                continue;
//            if(StringUtils.isEmpty(netInfo[1]) || netInfo[1].trim().contains("N/A"))
//                continue;
//            command = "ip link show " + iface.getName() + " | grep link | awk -F ' ' '{print $2}'";  //获取该网卡的硬件地址，在多个网卡设备上比较有用
//            linuxCommand = new BashCommand(command);
//            result = linuxCommand.exec().trim();
//            if(linuxCommand.getException() != null)
//                continue;
//            if(linuxCommand.getResult().getExitValue() != 0)
//                continue;
//            if(result.equals("00:00:00:00:00:00"))
//                continue;
//            sb.append(result);
//            LOGGER.debug("Interface [{}] Mac [{}]",iface.getName(),result);
//        }
//        return EncryptUtils.getMD5ofStr(sb.toString());
//    }

    public static String toJson(Object obj) {
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.disableHtmlEscaping();
            gb.setPrettyPrinting();
            Gson gson = gb.create();
            return gson.toJson(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
