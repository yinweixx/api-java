package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class VideoSourceCreateParam extends AbstractEntity {
    private String nationalId;                  //国标id

    private String shortNumber;                 //短编号

    private String intersection;                //路口

    private String orientation;                 //朝向

    private String description;                        //描述

    private String ipAddress;                   //ip地址

    private String sourceAddress;               //源地址

    private String tcp;                         //tcp

    private String userName;                    //用户名

    private String password;                    //密码

    private String ifForword;                   //是否转发

    private String forwardUrl;                  //转发url

    private String ifLink;                      //是否链接

    private String ifInclude;                   //是否收录

    private String storageUrl;                  //存储url

    private String dataCenter;                  //数据中心

    private String warrantyDate;                //质保日期

    private String product;                     //生产厂商

    private String vender;                      //维护商

    private String bitRate;                     //码率

    private String resolvingPower;              //分辨率--例:1980*1080

    private String type;                        //类型


    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }

    public String getIntersection() {
        return intersection;
    }

    public void setIntersection(String intersection) {
        this.intersection = intersection;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getTcp() {
        return tcp;
    }

    public void setTcp(String tcp) {
        this.tcp = tcp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIfForword() {
        return ifForword;
    }

    public void setIfForword(String ifForword) {
        this.ifForword = ifForword;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getIfLink() {
        return ifLink;
    }

    public void setIfLink(String ifLink) {
        this.ifLink = ifLink;
    }

    public String getIfInclude() {
        return ifInclude;
    }

    public void setIfInclude(String ifInclude) {
        this.ifInclude = ifInclude;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getResolvingPower() {
        return resolvingPower;
    }

    public void setResolvingPower(String resolvingPower) {
        this.resolvingPower = resolvingPower;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
