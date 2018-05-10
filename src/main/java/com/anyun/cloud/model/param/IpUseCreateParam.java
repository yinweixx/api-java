package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.Date;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/2 9:40
 */
public class IpUseCreateParam extends AbstractEntity{

    public int blockId;
    public String ip;
    public Date createDate;


    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
