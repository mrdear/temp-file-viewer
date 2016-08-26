package cn.mrdear.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;

import cn.mrdear.entity.Article;
import cn.mrdear.entity.Category;
import cn.mrdear.service.FileService;

/**
 * @author Niu Li
 * @date 2016/8/9
 */
@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Resource(name = "fileService")
    private FileService fileService;

    /**
     * 前往主页
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String showIndex() throws IOException {
        return "index";
    }
    /**
     * 前往主页
     * @return
     */
    @RequestMapping(value = "/article",method = RequestMethod.POST)
    public @ResponseBody Article showArticle(String path, Model model) throws IOException {
        File file = new File(path);
        String result = "#欢迎使用Markdown View Tools";
        if (path.endsWith("md")){
            result = FileUtils.readFileToString(file,"UTF-8");
        }
        Article article = new Article();
        article.setContent(result);
        return article;
    }

    @RequestMapping(value = "/categorys",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody JSONArray showCategory(){
        ArrayList<String> pathList = fileService.findFilesPath();//存放路径集合
        ArrayList<Category> categories = new ArrayList<>();//存放目录集合
        //遍历,java8的方法
//        pathList.forEach(path->{
//            Category category = fileService.iteratorFile(path);
//            categories.add(category);
//        });
        for (String path : pathList) {
            Category category = fileService.iteratorFile(path);
            categories.add(category);
        }
        //定制序列化
        String result = fileService.toTreeJsonStr(categories);
        return JSON.parseArray(result);
    }

}
