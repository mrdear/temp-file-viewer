package cn.mrdear.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.apache.commons.fileupload.util.Streams;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.annotation.Resource;

import cn.mrdear.conf.Setting;
import cn.mrdear.entity.Article;
import cn.mrdear.entity.Category;
import cn.mrdear.service.FileService;

/**
 * 主控制器
 * @author Niu Li
 * @date 2016/8/9
 */
@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private FileService fileService;
    @Resource
    private Setting setting;

    /**
     * 前往主页
     * @return 主页
     */
    @RequestMapping(value = "/",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showIndex() throws IOException {
        logger.info("访问主页");
        return "index";
    }

    /**
     * 获取单篇文章
     * @param path 该文章路径
     * @return 该文章实体
     * @throws IOException 抛出异常
     */
    @RequestMapping(value = "/article",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody Article showArticle(String path, Model model) throws IOException {
        logger.debug("访问article页面");
        File file = new File(path);
        String result = setting.getWelecome();
        if (path.endsWith("md")){
            result = FileUtils.readFileToString(file,"UTF-8");
        }
        Article article = new Article();
        article.setContent(result);
        return article;
    }

    /**
     * 获取文件目录
     * @return 获取的文件目录集合
     */
    @RequestMapping(value = "/categorys",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody JSONArray showCategory(){
        logger.info("访问categorys");
        ArrayList<Category> categories = new ArrayList<>();//存放目录集合
        //遍历,java8的方法
//        setting.getMDPaths().stream().limit(setting.getPATH_MAX()).forEach(path->{
//            Category category = fileService.iteratorFile(path);
//            categories.add(category);
//        });
        int path_max = Integer.valueOf(setting.getMaxCount());
        for (int i = 0; i < setting.getMdpath().size(); i++) {
            if (i > path_max){
                break;
            }
            Category category = fileService.iteratorFile(setting.getMdpath().get(i));
            categories.add(category);
        }
        //定制序列化
        String result = fileService.toTreeJsonStr(categories);
        return JSON.parseArray(result);
    }

    /**
     * 下载文件请求
     * @param path 下载文件的路径
     * @return 该文件的字节流
     * @throws IOException 抛出异常
     */
    @RequestMapping(value = "/downloadFile",method = RequestMethod.GET,produces = "application/octet-stream;charset=UTF-8")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(required = true) String path) throws IOException {
        if (path.endsWith("md")){
            File file = new File(path);
            //解决文件名乱码
            String filename = URLEncoder.encode(file.getName(),"UTF-8");
            logger.debug("下载文件名:"+filename);
            logger.debug("下载路径:"+path);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        }
        return null;
    }

    /**
     * 文件上传
     * @param file 上传的文件,form表单提交
     * @param path 文件上传路径
     * @return 首页
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String uploadFile(MultipartFile file,String path){
        try {
            if (!file.isEmpty()){
                String filename = new String(file.getOriginalFilename().getBytes(),"UTF-8");
                logger.debug("上传文件名称:"+filename);
                logger.debug("上传路径:"+path);
                //使用StreamsAPI方式拷贝文件
                Streams.copy(file.getInputStream(),new FileOutputStream(path+File.separator+filename),true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("上传文件出错",e);
        }
        return "redirect:/";
    }

}
