package org.dromara.im.controller.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.bo.FriendBo;
import org.dromara.im.domain.vo.FriendVo;
import org.dromara.im.service.IFriendService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 好友
 * 前端访问路由地址为:/im/friend
 *
 * @author luojiaju
 * @date 2024-01-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/friend")
public class FriendController extends BaseController {

    private final IFriendService friendService;

    /**
     * 通过用户id 查询好友列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<SimpleUser> list(FriendBo bo, PageQuery pageQuery) {
        return friendService.queryPageList(bo, pageQuery);
    }

    @SaCheckLogin
    @GetMapping("/search")
    public TableDataInfo<SimpleUser> search(FriendBo bo, PageQuery pageQuery) {
        return friendService.searchPageList(bo, pageQuery);
    }

    /**
     * 导出好友列表
     */
    @SaCheckLogin
    @Log(title = "好友", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FriendBo bo, HttpServletResponse response) {
        List<FriendVo> list = friendService.queryList(bo);
        ExcelUtil.exportExcel(list, "好友", FriendVo.class, response);
    }

    /**
     * 获取好友详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<FriendVo> getInfo(@NotNull(message = "主键不能为空")
                               @PathVariable Long id) {
        return R.ok(friendService.queryById(id));
    }

    /**
     * 新增好友
     */
    @SaCheckLogin
    @Log(title = "好友", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FriendBo bo) {
        return toAjax(friendService.insertByBo(bo));
    }

    /**
     * 修改好友
     */
    @SaCheckLogin
    @Log(title = "好友", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FriendBo bo) {
        return toAjax(friendService.updateByBo(bo));
    }

    /**
     * 删除好友
     *
     * @param friendId 好友id
     */
    @SaCheckLogin
    @Log(title = "好友", businessType = BusinessType.DELETE)
    @DeleteMapping("/{friendId}")
    public R<Void> remove(@NotNull(message = "好友id错误!")
                          @PathVariable Long friendId) {
        return toAjax(friendService.deleteWithValidByIds(friendId, true));
    }


    /**
     * 同意添加好友
     *
     * @param friend
     * @return
     */
    @SaCheckLogin
    @PutMapping("/accept")
    public R<Void> acceptFriend(@Validated(EditGroup.class) @RequestBody FriendBo friend) {
        return toAjax(friendService.acceptFriend(friend));
    }


}
