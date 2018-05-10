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

package com.anyun.cloud.common.bean;

import java.util.*;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 10/8/16
 */
public class BeanTypeUtils {

    public static Set<Class<?>> getAllExtendedOrImplementedTypesRecursively(Class<?> clazz) {
        List<Class<?>> res = new ArrayList<>();
        do {
            res.add(clazz);
            // First, add all the interfaces implemented by this class
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                res.addAll(Arrays.asList(interfaces));

                for (Class<?> interfaze : interfaces) {
                    res.addAll(getAllExtendedOrImplementedTypesRecursively(interfaze));
                }
            }
            // Add the super class
            Class<?> superClass = clazz.getSuperclass();
            // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
            if (superClass == null) {
                break;
            }
            // Now inspect the superclass
            clazz = superClass;
        } while (!"java.lang.Object".equals(clazz.getCanonicalName()));
        return new HashSet<>(res);
    }

    public static boolean existExtendedOrImplementedTypesRecursively(Class<?> clazz, Class<?> type) {
        for (Class<?> _type : getAllExtendedOrImplementedTypesRecursively(clazz)) {
            if (_type.equals(type))
                return true;
        }
        return false;
    }
}
