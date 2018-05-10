package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 19:14
 */
@Iciql.IQTable(name = "host_hardware")
public class HostHardwareEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id" ,primaryKey = true,autoIncrement = true)
    public int hostId; //宿主机Id
    @Iciql.IQColumn(name="host_ip")
    public String hostIp; //宿主机ip
    @Iciql.IQColumn(name="brandName")
    public String cpuBrandName; //cpu 品牌名称
    @Iciql.IQColumn(name="physicalCores")
    public String cpuPhysicalCores; //cpu 物理核数
    @Iciql.IQColumn(name="threadsPerCore")
    public String cpuThreadsPerCore; //cpu进程核数
    @Iciql.IQColumn(name="logicalCores")
    public String cpuLogicalCores;//cpu逻辑核数
    @Iciql.IQColumn(name="familyId")
    public String cpuFamilyId;//cpu产品系列号
    @Iciql.IQColumn(name="modelId")
    public String cpuModelId;// cpu 型号
     @Iciql.IQColumn(name="features")
    public String  features; //cpu特征
    @Iciql.IQColumn(name="cachelineBytes")
    public String cpuCachelineBytes; //cpu缓存字节
    @Iciql.IQColumn(name="L1DataCache")
    public String cpuL1DataCache;//cpu一级缓存
    @Iciql.IQColumn(name="L1InstructionCache")
    public String cpuL1InstructionCache;
    @Iciql.IQColumn(name="L2Cache")
    public String cpuL2Cache; //cpu二级缓存
    @Iciql.IQColumn(name="L3Cache")
    public String cpuL3Cache; //cpu 三级缓存
    @Iciql.IQColumn(name="Virtual")
    public String memoryVirtual; //cpu虚拟内存
    @Iciql.IQColumn(name="Swap")
    public String memorySwap;//cpu交换内存

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getCpuBrandName() {
        return cpuBrandName;
    }

    public void setCpuBrandName(String cpuBrandName) {
        this.cpuBrandName = cpuBrandName;
    }

    public String getCpuPhysicalCores() {
        return cpuPhysicalCores;
    }

    public void setCpuPhysicalCores(String cpuPhysicalCores) {
        this.cpuPhysicalCores = cpuPhysicalCores;
    }

    public String getCpuThreadsPerCore() {
        return cpuThreadsPerCore;
    }

    public void setCpuThreadsPerCore(String cpuThreadsPerCore) {
        this.cpuThreadsPerCore = cpuThreadsPerCore;
    }

    public String getCpuLogicalCores() {
        return cpuLogicalCores;
    }

    public void setCpuLogicalCores(String cpuLogicalCores) {
        this.cpuLogicalCores = cpuLogicalCores;
    }

    public String getCpuFamilyId() {
        return cpuFamilyId;
    }

    public void setCpuFamilyId(String cpuFamilyId) {
        this.cpuFamilyId = cpuFamilyId;
    }

    public String getCpuModelId() {
        return cpuModelId;
    }

    public void setCpuModelId(String cpuModelId) {
        this.cpuModelId = cpuModelId;
    }

    public String getCpuCachelineBytes() {
        return cpuCachelineBytes;
    }

    public void setCpuCachelineBytes(String cpuCachelineBytes) {
        this.cpuCachelineBytes = cpuCachelineBytes;
    }

    public String getCpuL1DataCache() {
        return cpuL1DataCache;
    }

    public void setCpuL1DataCache(String cpuL1DataCache) {
        this.cpuL1DataCache = cpuL1DataCache;
    }

    public String getCpuL1InstructionCache() {
        return cpuL1InstructionCache;
    }

    public void setCpuL1InstructionCache(String cpuL1InstructionCache) {
        this.cpuL1InstructionCache = cpuL1InstructionCache;
    }

    public String getCpuL2Cache() {
        return cpuL2Cache;
    }

    public void setCpuL2Cache(String cpuL2Cache) {
        this.cpuL2Cache = cpuL2Cache;
    }

    public String getCpuL3Cache() {
        return cpuL3Cache;
    }

    public void setCpuL3Cache(String cpuL3Cache) {
        this.cpuL3Cache = cpuL3Cache;
    }

    public String getMemoryVirtual() {
        return memoryVirtual;
    }

    public void setMemoryVirtual(String memoryVirtual) {
        this.memoryVirtual = memoryVirtual;
    }

    public String getMemorySwap() {
        return memorySwap;
    }

    public void setMemorySwap(String memorySwap) {
        this.memorySwap = memorySwap;
    }
}

