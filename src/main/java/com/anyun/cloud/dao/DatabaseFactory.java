package com.anyun.cloud.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.PreparedStatement;

/**
 * Created by jt on 17-12-12.
 */
public class DatabaseFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseFactory.class);
    private static DatabaseFactory factory;
    private SqlSessionFactory sqlSessionFactory;
    private boolean error = true;

    private DatabaseFactory() {
        try {
            Resources.setDefaultClassLoader(DatabaseFactory.class.getClassLoader());
            String resource = "config/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            LOGGER.error("Database initial error :{}", e.getMessage());
            error = true;
        }
    }

    public static DatabaseFactory getFactory() {
        synchronized (DatabaseFactory.class) {
            if (factory == null) {
                factory = new DatabaseFactory();
            }
        }
        return factory;
    }

    public boolean test() throws Exception {
        try {
            SqlSession session = sqlSessionFactory.openSession();
            String sql = "select 1 = 1 as test";
            PreparedStatement result = session.getConnection().prepareStatement(sql);
            if (result == null) {
                LOGGER.error("Database testing error.");
                throw new Exception("Database testing error.");
            }
            LOGGER.info("Database query test successed.");
            error = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Database testing error:{}", e.getMessage());
            error = true;
            return false;
        }
    }

    public SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public boolean isError() {
        return error;
    }

}
