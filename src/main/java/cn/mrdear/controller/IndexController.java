package cn.mrdear.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 获取目录
     * @return
     */
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

    /**
     * 下载文件
     * @param path
     * @return
     */
    @RequestMapping(value = "/downloadFile",method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@RequestParam(required = true) String path) throws IOException {
        if (path.endsWith("md")){
            File file = new File(path);
            String filename = path.substring(path.lastIndexOf("\\")+1);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        }
        return null;
    }

}
