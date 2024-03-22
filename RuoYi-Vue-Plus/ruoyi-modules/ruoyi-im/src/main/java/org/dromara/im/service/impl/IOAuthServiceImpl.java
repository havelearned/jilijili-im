package org.dromara.im.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.im.domain.vo.github.GithubTokenInfo;
import org.dromara.im.domain.vo.github.GithubUserInfo;
import org.dromara.im.service.IOAuthService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.mapper.SysUserMapper;
import org.dromara.system.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.dromara.im.constant.ImStringConstants.GITEE_GET_USER;
import static org.dromara.im.constant.ImStringConstants.GITHUB_GET_USER;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 18/01/2024
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IOAuthServiceImpl implements IOAuthService {

    private final SysUserMapper sysUserMapper;
    private final ISysRoleService iSysRoleService;


    public String checkedGithubUser(GithubTokenInfo githubTokenInfo) {
        // 获取github用户信息
        HttpRequest request = HttpUtil.createRequest(Method.GET, GITHUB_GET_USER);
        request.header("Authorization", "Bearer " + githubTokenInfo.getAccess_token());
        HttpResponse execute = request.execute();

        GithubUserInfo githubUserInfo = JsonUtils.parseObject(execute.body(), GithubUserInfo.class);
        if (githubUserInfo != null) {
            return saveUserInfo(githubUserInfo,"gitee");
        } else {
            return "登录失败!!!";
        }

    }

    public String checkedGiteeUser(GithubTokenInfo tokenInfo) {
        HttpRequest request = HttpUtil.createRequest(Method.GET, GITEE_GET_USER + "?access_token=" + tokenInfo.getAccess_token());
        HttpResponse res = request.execute();

        String body = res.body();
        log.info("token=>{},body=>{}",tokenInfo.getAccess_token(),body);
        GithubUserInfo userInfo = JsonUtils.parseObject(body, GithubUserInfo.class);
        if (userInfo != null && userInfo.getId() > 0) {
            return saveUserInfo(userInfo,"gitee");
        } else {
            return "登录失败!!!";
        }
    }
    public String saveUserInfo(GithubUserInfo githubUserInfo,String userType) {
        try {
            SysUserVo userVo = sysUserMapper.selectVoById(githubUserInfo.getId());
            // 判断用户是否已经注册
            if (userVo == null) {
                // 注册并登录,返回token
                SysUser sysUser = new SysUser();
                sysUser.setUserId(githubUserInfo.getId());
                sysUser.setUserName(githubUserInfo.getLogin());
                sysUser.setNickName(githubUserInfo.getName());
                sysUser.setTenantId("000000");
                sysUser.setDeptId(105L);
                sysUser.setUserType(userType);
                sysUser.setPassword(BCrypt.hashpw("123456"));
                sysUser.setEmail(githubUserInfo.getEmail());
                sysUser.setRemark(githubUserInfo.getAvatar_url());
                int insert = sysUserMapper.insert(sysUser);
                if (insert > 0) {
                    // 默认普通角色
                    iSysRoleService.insertAuthUsers(2L, new Long[]{sysUser.getUserId()});
                    return JsonUtils.toJsonString(userVo);
                }
                return "null";
            } else {
                // 直接登录,返回token
                return JsonUtils.toJsonString(userVo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
