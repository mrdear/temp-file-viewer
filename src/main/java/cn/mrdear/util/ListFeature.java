package cn.mrdear.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

import cn.mrdear.entity.Category;

/**
 * 定制fastjson转换规则,空集合不进行转换
 * @author Niu Li
 * @date 2016/8/25
 */
public class ListFeature implements PropertyPreFilter {

    @Override
    public boolean apply(JSONSerializer serializer, Object object, String name) {

        if (name.equals("children")){
            Category category = (Category) object;
            return category.getIsDir();
        }
        return true;
    }
}
