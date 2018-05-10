package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.elasticsearch.Elasticsearch;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.ElasticSearchDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.ElasticSearchDto;
import com.anyun.cloud.model.dto.PageDto;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ElasticSearchDaoImpl extends AbstractIciqlDao implements ElasticSearchDao {
    final static Logger LOGGER= LoggerFactory.getLogger(ElasticSearchDaoImpl.class);

    @Inject
    public ElasticSearchDaoImpl(Database database) {
        super(database);
    }

    @Override
    public PageDto<ElasticSearchDto> QueryLogByCondition(int index, int limit, String sortBy, String sortDirection, String id, String host, String source, String startTimestamp, String endTimestamp, String message, Elasticsearch elasticsearch) {
        PageDto<ElasticSearchDto> pageDto = new PageDto<>();

        List<ElasticSearchDto> data = new ArrayList<>();
        try {
            Connection connection = elasticsearch.getConnection();
            String sql = "select *  from  jiqun where 1=1";
            if(id !=null && !id.equals("")){
                sql += " and _id = '%"+id+"%'" ;
            }
            if(host !=null && !host.equals("")){
                sql += " and host like '%"+host+"%'" ;
            }
            if(source !=null &&!source.equals("")){
                sql += " and source like '%"+source+"%'" ;
            }
            if(message !=null &&!message.equals("")){
                sql += " and message like '%"+message+"%'" ;
            }
            if((startTimestamp !=null &&!startTimestamp.equals("")) && (endTimestamp !=null &&!endTimestamp.equals("")) ){
//                startTimestamp = startTimestamp+"T00:00:00.000Z";
//                endTimestamp = endTimestamp+"T00:00:00.000Z";
                sql += " and @timestamp between '"+startTimestamp+"' and '"+endTimestamp+"'" ;
            }
            if (sortDirection.equals("desc") && !sortDirection.equals("")){
                if (sortBy.equals(""))
                    sortBy= "@timestamp";
                sql +=" order by "+sortBy+" desc limit  "+limit+" offset "+(index-1)*limit;
            }
            else {
                if (sortBy.equals(""))
                    sortBy= "@timestamp";
                sql +=" order by "+sortBy+" limit  "+limit+" offset "+(index-1)*limit;
            }
            LOGGER.debug("sql[{}]",sql);


            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ElasticSearchDto dto1= new ElasticSearchDto();
                String dateStr = resultSet.getString("@timestamp");
                SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);//输入的被转化的时间格式
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = dff.parse(dateStr);
                String str1 = df1.format(date1);

                System.out.println("\n");
                dto1.setId(resultSet.getString("_id"));
                if(host !=null && !host.equals("")){
                    dto1.setHost(resultSet.getString("host").replace(host,"<strong style='color:red'>"+host+"</strong>"));
                }
                else {
                    dto1.setHost(resultSet.getString("host"));

                }
                if(source !=null &&!source.equals("")){
                    dto1.setSource(resultSet.getString("source").replace(source,"<strong style='color:red'>"+source+"</strong>"));

                }
                else {
                    dto1.setSource(resultSet.getString("source"));
                }
                if(message !=null &&!message.equals("")){
                    dto1.setMessage(resultSet.getString("message").replace(message,"<strong style='color:red'>"+message+"</strong>"));
                }
                else {
                    dto1.setMessage(resultSet.getString("message"));
                }
                dto1.setTimestamp(str1);

                data.add(dto1);
                LOGGER.debug("dto:[{}]:",str1);
            }

            String sql1 = "select *  from  jiqun where 1=1";
            if(id !=null && !id.equals("")){
                sql1 += " and _id like '%"+id+"%'" ;
            }
            if(host !=null && !host.equals("")){
                sql1 += " and host like '%"+host+"%'" ;
            }
            if(source !=null &&!source.equals("")){
                sql1 += " and source like '%"+source+"%'" ;
            }
            if(message !=null &&!message.equals("")){
                sql1 += " and message like '%"+message+"%'" ;
            }
            if((startTimestamp !=null &&!startTimestamp.equals("")) && (endTimestamp !=null &&!endTimestamp.equals("")) ){
//                startTimestamp = startTimestamp+"T00:00:00.000Z";
//                endTimestamp = endTimestamp+"T00:00:00.000Z";
                sql1 += " and @timestamp between '"+startTimestamp+"' and '"+endTimestamp+"'" ;
            }
            LOGGER.debug("sql1[{}]",sql1);
            int total ;
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet resultSet1 = ps1.executeQuery();
            List<ElasticSearchDto>  l = new ArrayList<>();
            ElasticSearchDto dto= new ElasticSearchDto();
            while (resultSet1.next()) {
                System.out.println("\n");
                dto.setId(resultSet1.getString("_id"));
                dto.setHost(resultSet1.getString("host"));
                dto.setMessage(resultSet1.getString("message"));
                dto.setTimestamp(resultSet1.getString("@timestamp"));
                dto.setSource(resultSet1.getString("source"));
                l.add(dto);
            }
            if(l==null){
                total=0;
            }else{
                total  = l.size();
                LOGGER.debug("total[{}]:",total);
            }
            pageDto.setIndex(index);
            pageDto.setLimit(limit);
            pageDto.setTotal(total);
            pageDto.setData(data);
            LOGGER.debug("pagedto:[{}]：",pageDto.asJson());
            return pageDto;

        }
        catch (Exception e){
            Response response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return null;
        }
    }

    @Override
    public ElasticSearchDto selectServiceDetail(String id, Elasticsearch elasticsearch){
        try {
            Connection connection = elasticsearch.getConnection();
            String sql = "select *  from  jiqun where _id = '"+id+"' ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            LOGGER.debug("sql:[{}]",sql);

            while (resultSet.next()) {
                ElasticSearchDto dto = new ElasticSearchDto();
                String dateStr = resultSet.getString("@timestamp");
                SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);//输入的被转化的时间格式
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = dff.parse(dateStr);
                String str1 = df1.format(date1);

                System.out.println("\n");
                dto.setId(resultSet.getString("_id"));
                dto.setHost(resultSet.getString("host"));
                dto.setSource(resultSet.getString("source"));
                dto.setMessage(resultSet.getString("message"));
                dto.setTimestamp(str1);
                LOGGER.debug("dto:[{}]:",str1);
                return dto;
            }
        }
        catch (Exception e){
            Response response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return null;
        }

        return null;
    }
}
