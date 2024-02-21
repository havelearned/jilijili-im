package org.dromara.im.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.im.domain.vo.AppboxApplicationVo;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.domain.vo.SysUserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

@SpringBootTest
public class AppboxApplicationServiceImplTest {

    @Autowired
    AppboxApplicationServiceImpl applicationService;

    @Test
    void queryApplicationUsers() {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(0);
        pageQuery.setPageSize(10);
        SysUserBo sysUserBo = new SysUserBo();
        Page<SysUserVo> page = applicationService.getUsersUnderTheApplication("1750558818170286082", sysUserBo, pageQuery);
        System.out.println(JsonUtils.toJsonString(page));
    }



    @Test
    void RedisTest() {

        Collection<String> onlineToken = RedisUtils.keys("*" + "online_token" + "*");
        for (String online : onlineToken) {
            String value = online.split(":")[1];
            long tokenActiveTimeoutByToken = StpUtil.stpLogic.getTokenActiveTimeoutByToken(value);
            System.out.println(tokenActiveTimeoutByToken);
            System.out.println(value);
        }

        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId("sys_user:1092");
        System.out.println(tokenValueByLoginId);
    }
}
