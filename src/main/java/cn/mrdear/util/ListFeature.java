package cn.mrdear.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

import cn.mrdear.entity.Category;

/**
 * 定制fastjson转换规则,空集合不进行转换
 * @author Niu Li
 * @since 2016/8/25
 */
public class ListFeature implements PropertyPreFilter {

    private static final String STATIC_FILED = "children";

    @Override
    public boolean apply(JSONSerializer serializer, Object object, String name) {
        if (STATIC_FILED.equals(name)){
            Category category = (Category) object;
            return category.getIsDir();
        }
        return true;
    }
}
