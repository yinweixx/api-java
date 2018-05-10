package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.dao.VideoSourceDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.VideoSourceEntity;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class VideoSourceDaoImpl extends AbstractIciqlDao implements VideoSourceDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(VideoSourceDaoImpl.class);

    @Inject
    public VideoSourceDaoImpl(Database database) {
        super(database);
    }


    @Override
    public void insertVideoSource(VideoSourceEntity v) {
        db.insert(v);
    }

    @Override
    public PageDto<VideoSourceEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) throws DatabaseException {
        LOGGER.debug("param:[{}]：", index +","+limit+","+sortBy+","+sortDirection);
        //定义变量
        PageDto<VideoSourceEntity> pageDto = new PageDto<VideoSourceEntity>();
        List<VideoSourceEntity> data = null;
        VideoSourceEntity v = new VideoSourceEntity();

        int total = (int) db.from(v).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(v).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(v).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }

        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        LOGGER.debug("pagedto:[{}]：", pageDto.asJson());

        return pageDto;
    }

    @Override
    public VideoSourceEntity selectVideoById(String cameraId) throws DatabaseException {
        VideoSourceEntity v = new VideoSourceEntity();
        return db.from(v).where(v.getCameraId()).is(cameraId).selectFirst();
    }

    @Override
    public VideoSourceEntity selectVideoByNationalId(String nationalId) throws DatabaseException {
        VideoSourceEntity v = new VideoSourceEntity();
        return db.from(v).where(v.getNationalId()).is(nationalId).selectFirst();
    }

    @Override
    public List<VideoSourceEntity> selectVideoAll() throws DatabaseException {
        VideoSourceEntity v = new VideoSourceEntity();
        return db.from(v).select();
    }

    @Override
    public void deleteAll(List<String> ids) throws DatabaseException {
        LOGGER.debug("Dao.ids:[{}]", JsonUtil.toJson(ids));
        VideoSourceEntity v = new VideoSourceEntity();
        //db.deleteAll(ids);
        for (String id : ids){
            db.from(v).where(v.getNationalId()).is(id).delete();
        }
    }

    @Override
    public PageDto<VideoSourceEntity> selectVideoByCondition(int index, int limit, String sortBy, String sortDirection, String intersection, String orientation, String desc, String product, String vender, String dataCenter) throws DatabaseException {
        PageDto<VideoSourceEntity> pageDto = new PageDto<>();

        //显示条件下查询数据总量
        String sql = "select * from video_source_info where 1=1";
        if (intersection != null && !intersection.equals("")) {
            sql += " and intersection like '%" + intersection + "%'";
        }
        if (orientation != null && !orientation.equals("")) {
            sql += " and orientation like '%" + orientation + "%'";
        }
        if (desc != null && !desc.equals("")) {
            sql += " and description like  '%" + desc + "%'";
        }
        if (product != null && !product.equals("")) {
            sql += " and product like  '%" + product +"%'";
        }
        if (vender != null && !vender.equals("")) {
            sql += " and vender like  '%" + vender +"%'";
        }
        if (dataCenter != null && !dataCenter.equals("")){
            sql += " and data_center like '%"+ dataCenter+"%'";
        }
        LOGGER.debug("sql[{}]", sql);
        List<VideoSourceEntity> l = db.executeQuery(VideoSourceEntity.class,sql);
        int total;
        if (l == null) {
            total = 0;
        } else {
            total = l.size();
        }
        LOGGER.debug("total:[{}]：", total);

        //根据条件查询数据分页
        List<VideoSourceEntity> data;

        String sql1 = "select * from video_source_info where 1=1";
        if (intersection != null && !intersection.equals("")) {
            sql1 += " and intersection like '%" + intersection + "%'";
        }
        if (orientation != null && !orientation.equals("")) {
            sql1 += " and orientation like '%" + orientation + "%'";
        }
        if (desc != null && !desc.equals("")) {
            sql1 += " and description like '%" + desc + "%'";
        }
        if (product != null && !product.equals("")) {
            sql1 += " and product like  '%" + product +"%'";
        }
        if (vender != null && !vender.equals("")) {
            sql1 += " and vender like  '%" + vender +"%'";
        }
        if (dataCenter != null && !dataCenter.equals("")){
            sql1 += " and data_center like '%"+ dataCenter+"%'";
        }
        if (sortDirection.equals("desc") && !sortDirection.equals("")) {
            if (sortBy.equals(""))
                sortBy = "national_id";
            sql1 += " order by " + sortBy + " desc limit  " + limit + " offset " + (index - 1) * limit;
        } else {
            if (sortBy.equals(""))
                sortBy = "national_id";
            sql1 += " order by " + sortBy + " limit  " + limit + " offset " + (index - 1) * limit;
        }
        LOGGER.debug("sql1[{}]", sql1);
        ResultSet rs = db.executeQuery(sql1);
        data = db.buildObjects(VideoSourceEntity.class,rs);
        LOGGER.debug("data:[{}]：", JsonUtil.toJson(data));

        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    @Override
    public VideoSourceEntity updateVideoSource(VideoSourceEntity v) throws DatabaseException {
        VideoSourceEntity video = new VideoSourceEntity();
        db.from(video).set(v.getNationalId()).to(v.getNationalId())
                .set(video.getIntersection()).to(v.getIntersection())
                .set(video.getOrientation()).to(v.getOrientation())
                .set(video.getDescription()).to(v.getDescription())
                .set(video.getIpAddress()).to(v.getIpAddress())
                .set(video.getSourceAddress()).to(v.getSourceAddress())
                .set(video.getTcp()).to(v.getTcp())
                .set(video.getUserName()).to(v.getUserName())
                .set(video.getPassword()).to(v.getPassword())
                .set(video.getIfForword()).to(v.getIfForword())
                .set(video.getForwardUrl()).to(v.getForwardUrl())
                .set(video.getIfLink()).to(v.getIfLink())
                .set(video.getIfInclude()).to(v.getIfInclude())
                .set(video.getStorageUrl()).to(v.getStorageUrl())
                .set(video.getDataCenter()).to(v.getDataCenter())
                .set(video.getWarrantyDate()).to(v.getWarrantyDate())
                .set(video.getProduct()).to(v.getProduct())
                .set(video.getVender()).to(v.getVender())
                .set(video.getBitRate()).to(v.getBitRate())
                .set(video.getResolvingPower()).to(v.getResolvingPower())
                .set(video.getType()).to(v.getType())
                .where(video.getCameraId()).is(v.getCameraId()).update();
        String  cameraId = v.getCameraId();
        return db.from(video).where(video.getCameraId()).is(cameraId).selectFirst();
    }

    @Override
    public VideoSourceEntity insertVideoSourceByCondition(VideoSourceEntity v) throws DatabaseException {
        VideoSourceEntity vs = new VideoSourceEntity();
        db.insert(v);
        return db.from(vs).where(vs.getCameraId()).is(v.getCameraId()).selectFirst();
    }


}
