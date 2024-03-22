package org.dromara.im.controller.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.bo.GroupChatBo;
import org.dromara.im.domain.vo.GroupChatVo;
import org.dromara.im.service.IGroupChatService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 群聊
 * 前端访问路由地址为:/im/groupChat
 *
 * @author luojiaju
 * @date 2024-01-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/groupChat")
public class GroupChatController extends BaseController {

    private final IGroupChatService groupChatService;


    /**
     * 通过用户id,查询群聊信息
     */
    @SaCheckLogin
    @GetMapping("/groupChatList")
    public TableDataInfo<GroupChatVo> getUserGroupChatList(GroupChatBo bo, PageQuery pageQuery) {
        return groupChatService.queryTheCurrentUsersGroupChatList(bo, pageQuery);
    }

    /**
     * 通过群聊id,查询用户列表
     */
    @SaCheckLogin
    @GetMapping("/userList")
    public TableDataInfo<SimpleUser> getGroupChatUsersList(GroupChatBo bo, PageQuery pageQuery) {
        return groupChatService.queryUserListThroughGroupChatId(bo, pageQuery);
    }


    /**
     * 查询群聊列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<GroupChatVo> list(GroupChatBo bo, PageQuery pageQuery) {
        return groupChatService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取群聊详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<Map<String, Object>> getInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long id) {
        return R.ok(groupChatService.queryById(id));
    }

    /**
     * 新增群聊
     */
    @SaCheckLogin
    @Log(title = "群聊", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<GroupChatBo> add(@Validated(AddGroup.class) @RequestBody GroupChatBo bo) {
        return R.ok(groupChatService.insertByBo(bo));
    }

    /**
     * 修改群聊
     */
    @SaCheckLogin
    @Log(title = "群聊", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody GroupChatBo bo) {
        return toAjax(groupChatService.updateByBo(bo));
    }

    /**
     * 删除群聊
     */
    @SaCheckLogin
    @Log(title = "群聊", businessType = BusinessType.DELETE)
    @DeleteMapping("/{groupId}/{userId}")
    public R<Void> remove(@NotNull(message = "群聊id不能为空")
                          @PathVariable Long groupId,
                          @NotNull(message = "群聊用户id不能为空")
                          @PathVariable Long userId) {
        return toAjax(groupChatService.deleteWithValidByGroupIdAndUserId(groupId, userId));
    }


    @SaCheckLogin
    @Log(title = "退出群聊", businessType = BusinessType.DELETE)
    @DeleteMapping("/exit/{groupId}/{userId}")
    public R<Void> quitUser(@NotNull(message = "群聊id不能为空")
                            @PathVariable Long groupId,
                            @NotNull(message = "群聊用户id不能为空")
                            @PathVariable Long userId) {
        return toAjax(groupChatService.removeByGroupIdAndUserId(groupId, userId));
    }
}
