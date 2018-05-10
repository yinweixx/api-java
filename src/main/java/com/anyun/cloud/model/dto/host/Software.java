package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 14:57
 */
public class Software  extends AbstractEntity{
    public Agent agent;
    public OS    os;
    public Docker docker;
    public LXD    lxd;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public Docker getDocker() {
        return docker;
    }

    public void setDocker(Docker docker) {
        this.docker = docker;
    }

    public LXD getLxd() {
        return lxd;
    }

    public void setLxd(LXD lxd) {
        this.lxd = lxd;
    }
}
