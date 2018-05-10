package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 15:02
 */
public class OS extends AbstractEntity{
    public String  Distributor;
    public String Description;
    public String Release;
    public String Codename;
    public String  Arch;
    public String   KernelName;
    public String   KernelVersion;
    public String Uptime;

    public String getDistributor() {
        return Distributor;
    }

    public void setDistributor(String distributor) {
        Distributor = distributor;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRelease() {
        return Release;
    }

    public void setRelease(String release) {
        Release = release;
    }

    public String getCodename() {
        return Codename;
    }

    public void setCodename(String codename) {
        Codename = codename;
    }

    public String getArch() {
        return Arch;
    }

    public void setArch(String arch) {
        Arch = arch;
    }

    public String getKernelName() {
        return KernelName;
    }

    public void setKernelName(String kernelName) {
        KernelName = kernelName;
    }

    public String getKernelVersion() {
        return KernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        KernelVersion = kernelVersion;
    }

    public String getUptime() {
        return Uptime;
    }

    public void setUptime(String uptime) {
        Uptime = uptime;
    }
}
