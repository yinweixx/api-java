/*
 *
 *      JsonUtil.java
 *      Copyright (C) <2015-?>  <twitchgg@yahoo.com>
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.anyun.cloud.common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    public static <T> T fromJson(Class<T> type,String json) {
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.disableHtmlEscaping();
            Gson gson = gb.create();
            return gson.fromJson(json,type);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.disableHtmlEscaping();
            Gson gson = gb.create();
            return gson.toJson(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String toJson(Object obj,Class clazz) {
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.disableHtmlEscaping();
            Gson gson = gb.create();
            return gson.toJson(obj,clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
