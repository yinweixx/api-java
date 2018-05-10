package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @date 2018/1/15 11:33
 */
@Iciql.IQTable(name = "data_center")
public class DataCenterEntity extends AbstractEntity{
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    public int centerId;
    @Iciql.IQColumn(name = "center_name", length = 200, trim = true)
    public String centerName; //数据中心名称
    @Iciql.IQColumn(name = "dns_name", length = 50, trim = true)
    public String dnsName; //数据中心英文短名
    @Iciql.IQColumn(name = "center_address")
    public String centerAddress; //数据中心物理地址
    @Iciql.IQColumn(name = "center_category")
    public String centerCategory; //数据中心分类
    public long hostsNum; //数据中心下宿主机数目
    public long ipNum; //数据中心已使用Ip数目
    public long ipTotal; //数据中心下IP总数
    public long testHostsNum; //测试环境下宿主机数目
    public long testIpTotal; //测试环境下Ip总数
    public long testHostsNum1;//测试环境下正常运行的宿主机数目
    public long testIpUseTotal;//测试环境IP池下已使用的IP数目
    public long productHostsNum; //生产环境下宿主机数目
    public long productHostsNum1; //生产环境下正常运行宿主机数目
    public long productIpTotal;  //生产环境下Ip总数
    public long productIpUseTotal; //生产环境已使用IP数目
    public String testPct;                //测试环境已使用IP所占百分比
    public String productPct;              //生产环境已使用IP所占百分比
    public String centerPct;               //数据中心IP使用占比
    public DataCenterEntity() {
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

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

    public long getHostsNum() {
        return hostsNum;
    }

    public void setHostsNum(long hostsNum) {
        this.hostsNum = hostsNum;
    }

    public long getIpNum() {
        return ipNum;
    }

    public void setIpNum(long ipNum) {
        this.ipNum = ipNum;
    }

    public long getIpTotal() {
        return ipTotal;
    }

    public void setIpTotal(long ipTotal) {
        this.ipTotal = ipTotal;
    }

    public long getTestHostsNum() {
        return testHostsNum;
    }

    public void setTestHostsNum(long testHostsNum) {
        this.testHostsNum = testHostsNum;
    }

    public long getTestIpTotal() {
        return testIpTotal;
    }

    public void setTestIpTotal(long testIpTotal) {
        this.testIpTotal = testIpTotal;
    }

    public long getTestHostsNum1() {
        return testHostsNum1;
    }

    public void setTestHostsNum1(long testHostsNum1) {
        this.testHostsNum1 = testHostsNum1;
    }

    public long getTestIpUseTotal() {
        return testIpUseTotal;
    }

    public void setTestIpUseTotal(long testIpUseTotal) {
        this.testIpUseTotal = testIpUseTotal;
    }

    public long getProductHostsNum() {
        return productHostsNum;
    }

    public void setProductHostsNum(long productHostsNum) {
        this.productHostsNum = productHostsNum;
    }

    public long getProductHostsNum1() {
        return productHostsNum1;
    }

    public void setProductHostsNum1(long productHostsNum1) {
        this.productHostsNum1 = productHostsNum1;
    }

    public long getProductIpTotal() {
        return productIpTotal;
    }

    public void setProductIpTotal(long productIpTotal) {
        this.productIpTotal = productIpTotal;
    }

    public long getProductIpUseTotal() {
        return productIpUseTotal;
    }

    public void setProductIpUseTotal(long productIpUseTotal) {
        this.productIpUseTotal = productIpUseTotal;
    }

    public String getCenterCategory() {
        return centerCategory;
    }

    public void setCenterCategory(String centerCategory) {
        this.centerCategory = centerCategory;
    }

    public String getTestPct() {
        return testPct;
    }

    public void setTestPct(String testPct) {
        this.testPct = testPct;
    }

    public String getProductPct() {
        return productPct;
    }

    public void setProductPct(String productPct) {
        this.productPct = productPct;
    }

    public String getCenterPct() {
        return centerPct;
    }

    public void setCenterPct(String centerPct) {
        this.centerPct = centerPct;
    }
}
