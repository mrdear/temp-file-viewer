package cn.mrdear.service;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.mrdear.entity.Category;
import cn.mrdear.util.ListFeature;

/**
 * 文件上传工具类
 * @author Niu Li
 * @date 2016/8/24
 */
@Service
public class FileService {

    private static boolean isFirstFile = true;

    private static Logger logger = LoggerFactory.getLogger(FileService.class);
    /**
     * 递归查找文件夹及其子目录,构造树形菜单
     * @param path 遍历的路径
     * @return 返回当前目录实体
     */
    public Category iteratorFile(String path){
        logger.debug("配置路径为:"+path);
        File file = FileUtils.getFile(path);
        Category category = new Category();
        category.setDir(true);
        category.setState(new Category.StateBean(false,true));
        category.setText(file.getName());
        category.setSelectable(false);
        category.setPath(file.getPath());
        logger.debug("初始化目录:"+file.getName());

        try {
            DFS(file.listFiles(),category);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("递归目录出错,编码转换出问题",e);
        }
        return category;
    }

    /**
     * 集合转换为树形菜单需要的json串
     * @param lists
     * @return
     */
    public String toTreeJsonStr(List<Category> lists){
        ListFeature feature = new ListFeature();
        return JSON.toJSONString(lists,feature);
    }

    /**
     * 递归查找函数
     * @param files 文件子集
     * @param curCategory 当前目录
     */
    private void DFS(File[] files,Category curCategory) throws UnsupportedEncodingException {
        if (files == null){
            return ;
        }
        for (File file : files) {
            //统一字段
            Category categoryTemp = new Category();
            categoryTemp.setText(new String(file.getName().getBytes("UTF-8")));
            categoryTemp.setPath(new String(file.getPath().getBytes("UTF-8")));

            if (file.isDirectory()){
                logger.debug("找到文件夹:"+file.getName());
                if (isFirstFile){
                    categoryTemp.setState(new Category.StateBean(false,true));
                }
                categoryTemp.setSelectable(false);
                categoryTemp.setDir(true);
                DFS(file.listFiles(),categoryTemp);
            }else {
                if (file.getName().endsWith("md")){
                    logger.debug("找到文件:"+new String(file.getName().getBytes("UTF-8")));
                    categoryTemp.setIcon("glyphicon glyphicon-book");
                    categoryTemp.setSelectable(true);
                    if (isFirstFile){
                        categoryTemp.setState(new Category.StateBean(true,true));
                        isFirstFile = false;
                    }
                }else {
                    continue;
                }
            }
            curCategory.getNodes().add(categoryTemp);
        }
    }
}
