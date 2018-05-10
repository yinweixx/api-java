/*
 *
 *      StringUtils.java
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
package com.anyun.cloud.common.string;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 *
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 判断字符串是否为空
     *
     * @param str	需要判断的字符串
     * @return booleanValue 返回是否为空
     */
    public final static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        str = str.trim();
        return str.length() == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str	需要判断的字符串
     * @return	booleanValue 返回是否为空
     */
    public final static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 获取去两边空格之后的字符串长度
     *
     * @param str
     * @return
     */
    public final static long getLength(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        return str.trim().length();
    }

    /**
     * 获取分割的字符串
     *
     * @param str	需要分割的字符串
     * @param segment	分割字符串
     * @return	segments	分割好的字符串数组
     */
    public final static String[] getSplitValues(String str, String segment) {
        if (str == null || str.trim().length() == 0) {
            return new String[]{};
        }
        if (segment == null || segment.length() == 0) {
            return new String[]{};
        }
        if(!str.contains(segment))
            return new String[]{str};
        StringTokenizer stringTokenizer = new StringTokenizer(str, segment);
        List<String> segs = new LinkedList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String _token = stringTokenizer.nextToken();
            segs.add(_token);
        }
        String[] returns = new String[segs.size()];
        for (int i = 0; i < segs.size(); i++) {
            returns[i] = segs.get(i);
        }
        return returns;
    }

    /**
     * 将时间格式化为字符串，时间格式化默认为(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @param expression
     * @return
     */
    public static final String formatDate(Date date, String expression) {
        if (isEmpty(expression)) {
            expression = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(expression);
        return sdf.format(date);
    }

    /**
     * 将时间字符串格式化为Date，时间格式化默认为(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @param expression
     * @return
     */
    public static final Date parseDate(String date, String expression) {
        try {
            if (isEmpty(expression)) {
                expression = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(expression);
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取URL字符串
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getURLString(String url) throws UnsupportedEncodingException {
        return new String(url.getBytes("ISO8859_1"), "GB2312");
    }

    /**
     * 判断字符串是否都为空
     *
     * @param str
     * @return
     */
    public final static boolean isEmptyAll(String... str) {
        if (str.length == 0) {
            return true;
        }
        for (String _str : str) {
            if (isEmpty(_str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否只包含英文和数字
     *
     * @param str
     * @return
     */
    public static boolean onlyEngAndNumeric(String str) {
        return str.matches("[a-zA-Z0-9|\\.]*");
    }

    public static String uuidGen() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean regex(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    public static List<String> readStringLines(String result) {
        List<String> values = new LinkedList<>();
        if (StringUtils.isEmpty(result)) {
            return values;
        }
        BufferedReader reader = new BufferedReader(new StringReader(result));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                values.add(line.trim());
            }
            return values;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
