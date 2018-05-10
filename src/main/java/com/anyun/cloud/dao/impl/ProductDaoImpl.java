package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.dao.ProductDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ProductEntity;
import com.google.inject.Inject;
import com.iciql.SQLStatement;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractIciqlDao implements ProductDao {
    @Inject
    public ProductDaoImpl(Database database) {
        super(database);
    }

    @Override
    public ProductEntity selectDetailsById(int id) throws DatabaseException {
        ProductEntity p = new ProductEntity();
        return db.from(p).where(p.productId).is(id).select().get(0);
    }

    @Override
    public List<ProductEntity> selectList(int id, String name, String category) throws DatabaseException {
        List<ProductEntity> list;
        ProductEntity p = new ProductEntity();
//        System.out.println("sql:"+db.from(p).where(p.productId).like(31).and(p.productName).isNotNull().and(p.category).isNotNull().toSQL());
//        list =  db.from(p).where(p.productId).like(31).and(p.productName).isNotNull().and(p.category).isNotNull().select();

        String sql = "SELECT * FROM `video-cloud-base-platform`.product_info where id = ?  ";
        List<String> args = new ArrayList<>();
        args.add("35");


        list = db.executeQuery(ProductEntity.class, sql, args);

        return list;
    }

    @Override
    public void deleteById(int id) throws DatabaseException {
        ProductEntity p = new ProductEntity();
        db.from(p).where(p.productId).is(id).delete();
    }

    @Override
    public ProductEntity insert(ProductEntity p) throws DatabaseException {
        long key = db.insertAndGetKey(p);
        return db.from(p).where(p.productId).is((int) key).select().get(0);
    }

    @Override
    public List<ProductEntity> insert(List<ProductEntity> list) throws DatabaseException {
        ProductEntity p = new ProductEntity();
        List<Integer> longList = new ArrayList<>();
        List<Long> longs = db.insertAllAndGetKeys(list);
        longs.forEach(l -> {
            longList.add((int) l.longValue());
        });
        return db.from(p).where(p.productId).oneOf(longList).select();
    }

    @Override
    public ProductEntity update(ProductEntity p) {
        db.update(p);
        int id = p.getProductId();
        p = new ProductEntity();
        return db.from(p).where(p.productId).is(id).selectFirst();
    }

    @Override
    public List<ProductEntity> update(List<ProductEntity> list) {
        db.updateAll(list);
        List<Integer> l = new ArrayList<>();
        for (ProductEntity p : list) {
            l.add(p.getProductId());
        }
        ProductEntity P = new ProductEntity();
        return db.from(P).where(P.productId).oneOf(l).select();
    }

    @Override
    public PageDto<ProductEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<ProductEntity> pageDto = new PageDto<>();
        List<ProductEntity> data = null;
        ProductEntity p = new ProductEntity();
        int total = (int) db.from(p).selectCount();

        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(p).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }
}
