package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/19 20:22
 */
public class IpBlockCreateParam extends AbstractEntity{
    public String blockGateway;
    public String blockNetMask;
    public String blockStartIp;
    public String blockEndIp;
    public String blockCategory;
    public int poolId;

    public String getBlockGateway() {
        return blockGateway;
    }

    public void setBlockGateway(String blockGateway) {
        this.blockGateway = blockGateway;
    }

    public String getBlockNetMask() {
        return blockNetMask;
    }

    public void setBlockNetMask(String blockNetMask) {
        this.blockNetMask = blockNetMask;
    }

    public String getBlockStartIp() {
        return blockStartIp;
    }

    public void setBlockStartIp(String blockStartIp) {
        this.blockStartIp = blockStartIp;
    }

    public String getBlockEndIp() {
        return blockEndIp;
    }

    public void setBlockEndIp(String blockEndIp) {
        this.blockEndIp = blockEndIp;
    }

    public String getBlockCategory() {
        return blockCategory;
    }

    public void setBlockCategory(String blockCategory) {
        this.blockCategory = blockCategory;
    }

    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }
}
