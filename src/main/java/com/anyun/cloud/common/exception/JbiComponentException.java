/*
 *
 *      JbiComponentException.java
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

package com.anyun.cloud.common.exception;

import java.io.Serializable;

/**
 * @author twitchgg <twitchgg@yahoo.com>
 * @version 1.0
 * @date 3/26/16
 */
public class JbiComponentException extends BaseCloudException implements Serializable {
    public JbiComponentException(int code,int subCode, Throwable cause) {
        super(code, subCode, cause);
    }

    public JbiComponentException(int code,int subCode) {
        super(code, subCode);
    }

    @Override
    public String getType() {
        return JbiComponentException.class.getSimpleName();
    }
}
