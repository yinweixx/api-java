/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.anyun.cloud.common;

import org.hashids.Hashids;

import java.util.UUID;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 16-8-24
 */
public class RandomUtils {
    private RandomUtils() {

    }

    public static final String generateByUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static final String generateByhashId() {
        String salt = generateByUUID() + "-" + System.currentTimeMillis();
        Hashids hashids = new Hashids(salt);
        return hashids.encode(System.nanoTime());
    }

    public static final String generateByhashId(int size) {
        if (size == 0)
            size = 6;
        String salt = generateByUUID() + "-" + System.currentTimeMillis();
        Hashids hashids = new Hashids(salt, size);
        return hashids.encode(System.nanoTime());
    }
}
