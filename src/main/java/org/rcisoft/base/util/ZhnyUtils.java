package org.rcisoft.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author GaoLiwei
 * @date 2019/3/20
 */
public class ZhnyUtils {


    /**
     * 自动更新主键 id为实体类中主键的命名 objects中为map,key是属性，value是要更新的值
     *
     * @author GaoLiWei
     * @date 15:15 2019/3/20
     **/
    public static List<?> replaceId(List<?> list, String id, Map<String, Object> map) {
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    Class<?> aClass = list.get(0).getClass();
                    Field[] declaredFields = aClass.getDeclaredFields();
                    if (declaredFields.length > 0) {
                        Type type = null;
                        for (int l = 0; l<declaredFields.length; l++){
                            if (id.equals(declaredFields[l].getName())){
                                type = declaredFields[l].getType();
                                break;
                            }
                        }
                        Method method = null;
                        String idMethodName = "set" + id.substring(0, 1).toUpperCase() + id.substring(1);
                        method = aClass.getMethod(idMethodName,(Class<?>) type);
                        method.invoke(list.get(i), UuidUtil.create32());
                        //处理其他
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            for (int l = 0; l<declaredFields.length; l++){
                                if (entry.getKey().equals(declaredFields[l].getName())){
                                    type = declaredFields[l].getType();
                                    break;
                                }
                            }
                            String otherMethodName = "set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
                            method = aClass.getMethod(otherMethodName, (Class<?>) type);
                            method.invoke(list.get(i), entry.getValue());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }


}
