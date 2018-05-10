package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.Date;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 16:40
 */
public class HostBaseCreateParam extends AbstractEntity{
    public String hostIp;
    public Date createDate;
    public String  CenterName;
    public String category;




    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
