package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.dao.IpUseDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.IpUseEntity;
import com.google.inject.Inject;

import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/1 19:19
 */
public class IpUseDaoImpl extends AbstractIciqlDao implements IpUseDao {
    @Inject
    public IpUseDaoImpl(Database database) {
        super(database);
    }

    @Override
    public IpUseEntity selectDetailsById(int id) throws DatabaseException {
        IpUseEntity p = new IpUseEntity();
        int i = (int) db.from(p).where(p.id).is(id).selectCount();
        if (i > 0)
            return db.from(p).where(p.id).is(id).selectFirst();
        else
            return null;
    }

    @Override
    public IpUseEntity insert(IpUseEntity p) throws DatabaseException {
        long key = db.insertAndGetKey(p);
        return db.from(p).where(p.id).is((int) key).select().get(0);
    }

    @Override
    public IpUseEntity selectIpUseEntityByBlockIdAndIp(int blockId, String ip) {
        IpUseEntity p = new IpUseEntity();
        return db.from(p).where(p.blockId).is(blockId).and(p.ip).is(ip).selectFirst();

    }



    /**
     * 分页显示已使用Ip信息
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param blockId
     * @return
     */
    @Override
    public PageDto<IpUseEntity> selectPageList(int index, int limit, String sortBy, String sortDirection, int blockId) {
        PageDto<IpUseEntity> pageDto = new PageDto<>();
    List<IpUseEntity> data = null;
    IpUseEntity u =new IpUseEntity();
    int total = (int) db.from(u).where(u.blockId).is(blockId).selectCount();
        if (total > 0) { //有数据
        if (sortDirection.equals("desc")) { //倒序
            data = db.from(u).where(u.blockId).is(blockId).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
        } else {//正序
            data = db.from(u).where(u.blockId).is(blockId).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
        }
    }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
}
    @Override
    public void deleteById(int id) throws DatabaseException {
        IpUseEntity p = new IpUseEntity();
        db.from(p).where(p.id).is(id).delete();
    }

    @Override
    public long selectIpUsedNumByBlockId(int id) throws DatabaseException {
        IpUseEntity p=new IpUseEntity();
        return db.from(p).where(p.blockId).is(id).selectCount();
    }

}
