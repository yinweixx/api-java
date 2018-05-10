package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 15:04
 */
public class Memory extends AbstractEntity{
    public long Virtual;
    public long Swap;

    public long getVirtual() {
        return Virtual;
    }

    public void setVirtual(long virtual) {
        Virtual = virtual;
    }

    public long getSwap() {
        return Swap;
    }

    public void setSwap(long swap) {
        Swap = swap;
    }
}
