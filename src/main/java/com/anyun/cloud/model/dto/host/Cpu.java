package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 15:04
 */
public class Cpu extends AbstractEntity{
          public String  brandName;
          public long  physicalCores;
          public long  threadsPerCore;
          public long  logicalCores;
          public long  familyId;
          public long   modelId;
          public String[] features;
          public long   cachelineBytes;
          public long L1DataCache;
          public long L1InstructionCache;
          public long L2Cache;
          public long L3Cache;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public long getPhysicalCores() {
        return physicalCores;
    }

    public void setPhysicalCores(long physicalCores) {
        this.physicalCores = physicalCores;
    }

    public long getThreadsPerCore() {
        return threadsPerCore;
    }

    public void setThreadsPerCore(long threadsPerCore) {
        this.threadsPerCore = threadsPerCore;
    }

    public long getLogicalCores() {
        return logicalCores;
    }

    public void setLogicalCores(long logicalCores) {
        this.logicalCores = logicalCores;
    }

    public long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(long familyId) {
        this.familyId = familyId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String[] getFeatures() {
        return features;
    }

    public void setFeatures(String[] features) {
        this.features = features;
    }

    public long getCachelineBytes() {
        return cachelineBytes;
    }

    public void setCachelineBytes(long cachelineBytes) {
        this.cachelineBytes = cachelineBytes;
    }

    public long getL1DataCache() {
        return L1DataCache;
    }

    public void setL1DataCache(long l1DataCache) {
        L1DataCache = l1DataCache;
    }

    public long getL1InstructionCache() {
        return L1InstructionCache;
    }

    public void setL1InstructionCache(long l1InstructionCache) {
        L1InstructionCache = l1InstructionCache;
    }

    public long getL2Cache() {
        return L2Cache;
    }

    public void setL2Cache(long l2Cache) {
        L2Cache = l2Cache;
    }

    public long getL3Cache() {
        return L3Cache;
    }

    public void setL3Cache(long l3Cache) {
        L3Cache = l3Cache;
    }
}
