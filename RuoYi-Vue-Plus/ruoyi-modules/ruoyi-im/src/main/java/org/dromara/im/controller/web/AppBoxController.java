package org.dromara.im.controller.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.im.service.impl.AppboxApplicationServiceImpl;
import org.dromara.system.domain.bo.SysUserBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * <p>
 * description: 用户 添加或者修改应用
 * </p>
 *
 * @author luojiaju
 * @date 31/01/2024
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/im/appBox")
@RequiredArgsConstructor
public class AppBoxController extends BaseController {


    private final AppboxApplicationServiceImpl applicationService;


    /**
     * TODO 应用当前的公告信息
     * TODO 应用当前的用户列表
     * TODO 当前频道的聊天记录
     */




    /**
     * 用户添加应用
     */
    @SaCheckLogin
    @PostMapping("/{appId}")
    public R<Void> addUserClass(@PathVariable Long appId) {
        return toAjax(applicationService.addUserClass(appId));
    }


    /**
     * 用户退出应用
     */
    @SaCheckLogin
    @DeleteMapping("/{appId}")
    public R<Void> removeUserClass(@PathVariable Long appId) {
        return toAjax(applicationService.removeUserClass(appId));
    }


    /**
     * 通过应用id查询用户信息
     *
     * @param appId     应用程序的id
     * @param sysUserBo 用户信息
     * @param pageQuery 查询参数
     * @返回表格数据信息
     */
    @GetMapping("/appBoxUsers/{appId}")
    public TableDataInfo<?> obtainUsersUnderTheApplication(@PathVariable Serializable appId,
                                                           SysUserBo sysUserBo, PageQuery pageQuery) {
        return TableDataInfo.build(applicationService.getUsersUnderTheApplication(appId, sysUserBo, pageQuery));
    }
}
