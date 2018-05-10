package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.iciql.Db;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/12/2017
 */
public abstract class AbstractIciqlDao {
    protected Db db;

    public AbstractIciqlDao(Database database) {

        this.db = database.getDb();
    }






}
