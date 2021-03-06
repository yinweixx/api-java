package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 9:12
 */
public class DataCenterCreateParam extends AbstractEntity {

    private String centerName; //数据中心名称

    private String dnsName; //数据中心英文短名

    private String centerAddress; //数据中心物理地址
    private String centerCategory; //数据中心分类
    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterCategory() {
        return centerCategory;
    }

    public void setCenterCategory(String centerCategory) {
        this.centerCategory = centerCategory;
    }
}
