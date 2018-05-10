package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DaoException;
import com.anyun.cloud.common.tools.db.AbstractEntity;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by jt on 17-12-12.
 */
public class BaseMyBatisDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMyBatisDao.class);
    protected DatabaseFactory databaseFactory;

    public BaseMyBatisDao() {
        databaseFactory = DatabaseFactory.getFactory();
    }

    public BaseMyBatisDao(DatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }

    public void setDatabaseFactory(DatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public <T> T selectOne(Class<T> type, String sql, Object params) throws DaoException {
        SqlSession session = databaseFactory.getSession();
        T dto;
        try {
            if (params == null) {
                dto = session.selectOne(sql);
            } else {
                dto = session.selectOne(sql, params);
            }
        } catch (Exception e) {
            throw new DaoException(0x00000021, e);
        } finally {
            session.close();
        }
        if (dto instanceof AbstractEntity) {
            LOGGER.debug("seletOne DTO '{}'", ((AbstractEntity) dto).asJson());
        } else {
            LOGGER.debug("seletOne DTO '{}'", dto);
        }

        return dto;
    }

    public <T> T selectById(Class<T> type, String sql, String id) {
        return selectOne(type, sql + ".selectById", id);
    }

    public <T> List<T> selectList(Class<T> type, String sql, Object params) {
        SqlSession session = databaseFactory.getSession();
        List<T> dtos = null;
        try {
            if (params == null) {
                dtos = session.selectList(sql);
            } else {
                dtos = session.selectList(sql, params);
            }
        } catch (Exception e) {
            throw new DaoException(0x00000021, e);
        } finally {
            session.close();
        }
        if (dtos != null && !dtos.isEmpty()) {
            for (T dto : dtos) {
                if (dto instanceof AbstractEntity) {
                    LOGGER.debug("seletList DTO '{}'", ((AbstractEntity) dto).asJson());
                } else {
                    LOGGER.debug("seletList DTO '{}'", dto);
                }
            }
        }
        return dtos;
    }

    public <T> List<T> selectPageList(Class<T> type, String sql, Object params, int pageNumber, int pageSize) {
        SqlSession session = databaseFactory.getSession();
        List<T> dtos = null;
        try {
            if (params == null) {
                dtos = session.selectList(sql);
            } else {
                dtos = session.selectList(sql, params, new RowBounds(pageNumber, pageSize));
            }
        } catch (Exception e) {
            throw new DaoException(0x00000021, e);
        } finally {
            session.close();
        }
        if (dtos != null && !dtos.isEmpty()) {
            for (T dto : dtos) {
                if (dto instanceof AbstractEntity) {
                    LOGGER.debug("seletList DTO '{}'", ((AbstractEntity) dto).asJson());
                } else {
                    LOGGER.debug("seletList DTO '{}'", dto);
                }
            }
        }
        return dtos;
    }

    public int insert(String sql, Object params) throws DaoException {
        try (SqlSession session = databaseFactory.getSession()) {
            int result = session.insert(sql, params);
            session.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException(0x00000021, e);
        }
    }

    public SqlSession getBatchSqlSession() throws DaoException {
        try {
            SqlSession session = databaseFactory.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            return session;
        } catch (Exception e) {
            throw new DaoException(0x00000021, e);
        }
    }

    public int update(String sql, Object params) throws DaoException {
        try (SqlSession session = databaseFactory.getSession()) {
            int result = session.update(sql, params);
            session.commit();
            return result;
        } catch (Exception e) {
            throw new DaoException(0x00000021, e);
        }
    }

    public int delete(String sql, Object params) throws DaoException {
        try (SqlSession session = databaseFactory.getSession()) {
            int result = session.update(sql, params);
            session.commit();
            return result;
        } catch (Exception e) {
            throw new DaoException(0x00000021, e);
        }
    }
}
