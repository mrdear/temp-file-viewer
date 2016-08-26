package cn.mrdear.service;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.mrdear.entity.Category;
import cn.mrdear.util.ListFeature;
import cn.mrdear.util.PropertiesUtil;

/**
 * @author Niu Li
 * @date 2016/8/24
 */
@Service("fileService")
public class FileService {

    private static boolean isFirstFile = true;
    private static final String PATHPRE = "md.path";
    private static final Integer PATH_MAX = 20;
    /**
     * 递归查找文件夹及其子目录,构造树形菜单
     * @param path
     * @return
     */
    public Category iteratorFile(String path){

        File file = FileUtils.getFile(path);
        Category category = new Category();
        category.setDir(true);
        category.setState(new Category.StateBean(false,true));
        category.setText(file.getName());
        category.setSelectable(false);
        category.setPath(file.getPath());

        DFS(file.listFiles(),category);
        return category;
    }

    /**
     * 得到所配置的所有路径列表
     * @return
     */
    public ArrayList<String> findFilesPath(){
        ArrayList<String> pathList = new ArrayList<>();

        for (int i = 1; i <= PATH_MAX; i++) {
            String path = PropertiesUtil.getProperty(PATHPRE+i+"");
            if (path == null){
                break;
            }else {
                pathList.add(path);
            }
        }
        return pathList;
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

//    public static void main(String[] args) {
//        FileService fileService = new FileService();
//        System.out.println(fileService.findFilesPath());
//    }

    /**
     * 递归查找函数
     * @param files
     * @param curCategory
     */
    private void DFS(File[] files,Category curCategory){
        if (files == null){
            return ;
        }
        for (File file : files) {
            //统一字段
            Category categoryTemp = new Category();
            categoryTemp.setText(file.getName());
            categoryTemp.setPath(file.getPath());

            if (file.isDirectory()){
                if (isFirstFile){
                    categoryTemp.setState(new Category.StateBean(false,true));
                }
                categoryTemp.setSelectable(false);
                categoryTemp.setDir(true);
                DFS(file.listFiles(),categoryTemp);
            }else {
                if (file.getName().endsWith("md")){
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
