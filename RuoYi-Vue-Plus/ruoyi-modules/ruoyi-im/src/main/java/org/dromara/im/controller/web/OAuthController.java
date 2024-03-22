package org.dromara.im.controller.web;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.im.domain.vo.github.GithubTokenInfo;
import org.dromara.im.properties.OAuthGiteeProperties;
import org.dromara.im.properties.OAuthGithubProperties;
import org.dromara.im.service.impl.IOAuthServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

import static org.dromara.im.constant.ImStringConstants.GITEE_TOKEN_URL;
import static org.dromara.im.constant.ImStringConstants.GITHUB_TOKEN_URL;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 17/01/2024
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthGithubProperties oAuthGithubProperties;
    private final OAuthGiteeProperties oAuthGiteeProperties;

    private final IOAuthServiceImpl ioAuthService;

    @GetMapping("/{source}/login")
    public RedirectView loginCallback(@PathVariable("source") String source,
                                      @RequestParam String code) {

        if (source.equals("github")) {
            // 1. 获取code,获取access_token
            String tokenUrl = String.format(GITHUB_TOKEN_URL, oAuthGithubProperties.getClientId(),
                oAuthGithubProperties.getSecrets(), code);
            HttpRequest request = HttpUtil.createRequest(Method.POST, tokenUrl);
            request.header("Accept", "application/json");
            log.info("请求地址:{}", tokenUrl);
            HttpResponse execute = request.execute();
            GithubTokenInfo githubTokenInfo = JsonUtils.parseObject(execute.body(), GithubTokenInfo.class);
            log.info("第三方登录信息:{}", githubTokenInfo);
            String token = ioAuthService.checkedGithubUser(githubTokenInfo);
            return new RedirectView(oAuthGithubProperties.getRedirectUri() + "?token=" + token);

        } else if (source.equals("gitee")) {
            String tokenUrl = String.format(GITEE_TOKEN_URL,
                code,
                oAuthGiteeProperties.getClientId(),
                oAuthGiteeProperties.getSecrets(),
                "http://localhost:8080/oauth/gitee/login");
            HttpRequest request = HttpUtil.createRequest(Method.POST, tokenUrl);
            request.header("Accept", "application/json");
            HttpResponse res = request.execute();
            GithubTokenInfo tokenInfo = JsonUtils.parseObject(res.body(), GithubTokenInfo.class);
            // 携带token获取用户信息
            String token = ioAuthService.checkedGiteeUser(tokenInfo);
            return new RedirectView(oAuthGiteeProperties.getRedirectUri() + "?token=" + token);
        } else {
            throw new ServiceException("暂不支持该第三方登录!!!");
        }

    }

    @GetMapping("/config/list")
    public R<Map<String, Object>> configList() {
        Map<String, Object> map = new HashMap<>();
        map.put("oauthGithub", oAuthGithubProperties);
        map.put("oauthGitee", oAuthGiteeProperties);
        return R.ok(map);
    }


}
