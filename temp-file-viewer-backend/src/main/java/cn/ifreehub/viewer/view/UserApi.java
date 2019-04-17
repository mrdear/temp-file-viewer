package cn.ifreehub.viewer.view;

import cn.ifreehub.viewer.constant.CurrentUserHolder;
import cn.ifreehub.viewer.model.Token;
import cn.ifreehub.viewer.model.User;
import cn.ifreehub.viewer.repo.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.constant.AppConfig;
import cn.ifreehub.viewer.constant.JwtTokenType;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.util.JwtTokenUtils;
import cn.ifreehub.viewer.view.vo.UserProfileVO;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
@RestController
@RequestMapping("api/v1/")
public class UserApi {
    @Autowired
    private UserRepo userRepo;

    /**
     * 简易登录接口
     *
     * @param username 用户名
     * @param passwd   用户密码sha256结果
     */
    @PostMapping("login/")
    public ApiWrapper login(String username, String passwd, HttpServletResponse response, HttpServletRequest request) {
        String adminUserName = EnvironmentContext.getStringValue(AppConfig.ROOT_USERNAME);
        if (Objects.equals(username, adminUserName)) {
            String realPwd = EnvironmentContext.getStringValue(AppConfig.ROOT_PASSWORD);
            if (!Objects.equals(DigestUtils.sha256Hex(realPwd), passwd)) {
                return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
            }
            username = adminUserName;
        } else {
            User user = userRepo.findUserByUserName(username);
            if (user != null) { //如果存在就校验
                if (!Objects.equals(user.getPassword(), passwd)) {
                    return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
                }
            } else {//如果不存在就注册,暂时妥协
                user = new User();
                user.setUserName(username);
                user.setPassword(passwd);
                userRepo.save(user);
            }
        }
        CurrentUserHolder.setUserName(username);
        EnvironmentContext.createUserConfig();

        // 登录成功,下发token
        JwtTokenUtils.create(username, JwtTokenType.DEFAULT, response);
        return ApiWrapper.success();
    }

    /**
     * 得到当前用户信息
     */
    @GetMapping("profile/")
    public ApiWrapper profile(HttpServletRequest request) {
        Token token = JwtTokenUtils.getTokenFromRequest(request);
        String avatar = EnvironmentContext.getStringValue(AppConfig.ROOT_AVATAR);
        String userName = null;
        if (token != null) {
            userName = token.getUserName();
        }
        return ApiWrapper.success(new UserProfileVO(userName, avatar));
    }

}
