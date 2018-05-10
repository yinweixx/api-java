package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.anyun.cloud.dao.impl.AbstractIciqlDao;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/19 19:56
 */
@Iciql.IQTable(name = "ip_block")
public class IpBlockEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    public int blockId;   //ip段Id
   // @Iciql.IQColumn(name = "center_id")  //关联ip池Id
   // public int centerId;
    @Iciql.IQColumn(name = "gateway")  //Ip段网关
    public String blockGateway;
    @Iciql.IQColumn(name = "netMask") //Ip段掩码
    public String blockNetMask;
    @Iciql.IQColumn(name = "startIp") //Ip段起始ip
    public String blockStartIp;
    @Iciql.IQColumn(name = "endIp") //Ip段终止ip
    public String blockEndIp;
    @Iciql.IQColumn(name = "block_category") //Ip段分类
    public String blockCategory;
    @Iciql.IQColumn(name = "pool_id")  //关联ip池Id
    public int poolId;
    public long usedCount;
    public  long unusedCount;

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

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

    public long getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(long usedCount) {
        this.usedCount = usedCount;
    }

    public long getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(long unusedCount) {
        this.unusedCount = unusedCount;
    }
}




