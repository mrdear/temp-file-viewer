package cn.mrdear.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.mrdear.conf.Setting;
import cn.mrdear.util.CookieUtil;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 返回片段
 *
 * @author Niu Li
 * @since 2016/12/22
 */
@Controller
@RequestMapping("/fragment")
public class FragmentController {

  private static final String FILE_PATH_COOKIE_NAME = "filepath";
  @Resource
  private Setting setting;

  /**
   * 返回对比页面
   */
  @RequestMapping(value = "/compare", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String compare() {
    return "fragment/compare :: compare";
  }

  /**
   * 返回上传页面
   */
  @RequestMapping(value = "/upload", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String upload(Model model, HttpServletResponse response) {
    List<String> mdpath = setting.getMdpath();
    //访问时默认写入第一个路径到cookies
    if (!mdpath.isEmpty()) {
      CookieUtil.addCookie(response, FILE_PATH_COOKIE_NAME, mdpath.get(0));
    }
    model.addAttribute("mdpath", mdpath);
    return "fragment/upload :: upload";
  }

}
