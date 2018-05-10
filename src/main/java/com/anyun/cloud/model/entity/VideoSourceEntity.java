package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;


@Iciql.IQTable(name = "video_source_info")      //定义视频源表名
public class VideoSourceEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "camera_id",primaryKey = true,length = 10)
    private String cameraId;                    //摄像头ID（做唯一标识）

    @Iciql.IQColumn(name = "national_id",length = 200)
    private String nationalId;                  //国标id

    @Iciql.IQColumn(name = "short_number",length = 20)
    private String shortNumber;                 //短编号

    @Iciql.IQColumn(name = "intersection",length = 50)
    private String intersection;                //路口

    @Iciql.IQColumn(name = "orientation",length = 50)
    private String orientation;                 //朝向

    @Iciql.IQColumn(name = "description",length = 50)
    private String description;                        //描述

    @Iciql.IQColumn(name = "ip_address",length = 100)
    private String ipAddress;                   //ip地址

    @Iciql.IQColumn(name = "source_address",length = 100)
    private String sourceAddress;               //源地址

    @Iciql.IQColumn(name = "tcp",length = 20)
    private String tcp;                         //tcp

    @Iciql.IQColumn(name = "user_name",length = 50)
    private String userName;                    //用户名

    @Iciql.IQColumn(name = "password", length = 50)
    private String password;                    //密码

    @Iciql.IQColumn(name = "if_forward",length = 20)
    private String ifForword;                   //是否转发

    @Iciql.IQColumn(name = "forward_url",length = 500)
    private String forwardUrl;                  //转发url

    @Iciql.IQColumn(name = "if_link",length = 20)
    private String ifLink;                      //是否链接

    @Iciql.IQColumn(name = "if_include",length = 20)
    private String ifInclude;                   //是否收录

    @Iciql.IQColumn(name = "storage_url",length = 500)
    private String storageUrl;                  //存储url

    @Iciql.IQColumn(name = "data_center",length = 200)
    private String dataCenter;                  //数据中心

    @Iciql.IQColumn(name = "warranty_date",length = 200)
    private String warrantyDate;                //质保日期

    @Iciql.IQColumn(name = "product",length = 100)
    private String product;                     //生产厂商

    @Iciql.IQColumn(name = "vender",length = 100)
    private String vender;                      //维护商

    @Iciql.IQColumn(name = "bit_rate",length = 200)
    private String bitRate;                     //码率

    @Iciql.IQColumn(name = "resolving_power",length = 200)
    private String resolvingPower;              //分辨率--例:1980*1080

    @Iciql.IQColumn(name = "type",length = 200)
    private String type;                        //类型

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

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

    //重写　toString()
    @Override
    public String toString() {
       return "VideoSourceEntity{"+
               "cameraId=" + cameraId +
               "nationalId=" + nationalId +
               ", shortNumber='" + shortNumber + '\'' +
               ",intersection ='" + intersection + '\'' +
               ",orientation ='" + orientation + '\'' +
               ",description ='" + description + '\'' +
               ", ipAddress=" + ipAddress +
               ", sourceAddress=" + sourceAddress +
               ", tcp=" + tcp +
               ", userName=" + userName +
               ", password=" + password +
               ", ifForword=" + ifForword +
               ", forwardUrl=" + forwardUrl +
               ", ifLink=" + ifLink +
               ", ifInclude=" + ifInclude +
               ", storageUrl=" + storageUrl +
               ", dataCenter=" + dataCenter +
               ", warrantyDate=" + warrantyDate +
               ", product=" + product +
               ", vender=" + vender +
               ", bitRate=" + bitRate +
               ", resolvingPower=" + resolvingPower +
               ", type=" + type +
               '}';
    }

}
