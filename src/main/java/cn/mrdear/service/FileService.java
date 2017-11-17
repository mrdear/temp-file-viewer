package cn.mrdear.service;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.mrdear.entity.Category;
import cn.mrdear.util.ListFeature;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 查找出列表文件
 *
 * @author Niu Li
 * @since 2016/8/24
 */
@Service
public class FileService {

  private static int index = 0;

  private static Logger logger = LoggerFactory.getLogger(FileService.class);

  /**
   * 递归查找文件夹及其子目录,构造树形菜单
   *
   * @param path 遍历的路径
   * @return 返回当前目录实体
   */
  public Category iteratorFile(String path) {
    File file = FileUtils.getFile(path);
    Category category = new Category();
    category.setIsDir(true);
    category.setSpread(true);
    category.setName(file.getName());
    category.setPath(file.getPath());
    category.setId(index++);
    try {
      DFS(file.listFiles(), category);
    } catch (UnsupportedEncodingException e) {
      // do nothing
    }
    return category;
  }

  /**
   * 集合转换为树形菜单需要的json串
   *
   * @param lists 要转换的目录
   * @return json串
   */
  public String toTreeJsonStr(List<Category> lists) {
    ListFeature feature = new ListFeature();
    return JSON.toJSONString(lists, feature);
  }

  /**
   * 递归查找函数
   *
   * @param files       文件子集
   * @param curCategory 当前目录
   */
  private void DFS(File[] files, Category curCategory) throws UnsupportedEncodingException {
    if (files == null) {
      return;
    }
    for (File file : files) {
      //统一字段
      Category categoryTemp = new Category();
      categoryTemp.setName(new String(file.getName().getBytes("UTF-8")));
      categoryTemp.setPath(new String(file.getPath().getBytes("UTF-8")));
      categoryTemp.setId(index++);
      if (file.isDirectory()) {
        logger.debug("find dir :" + file.getName());
        categoryTemp.setIsDir(true);
        DFS(file.listFiles(), categoryTemp);
      } else {
        if (file.getName().endsWith("md")) {
          logger.debug("find md file:" + new String(file.getName().getBytes("UTF-8")));
        } else {
          continue;
        }
      }
      curCategory.getChildren().add(categoryTemp);
    }
  }
}
