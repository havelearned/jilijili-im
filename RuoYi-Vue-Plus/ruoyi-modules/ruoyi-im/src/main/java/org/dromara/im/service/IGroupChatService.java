package org.dromara.im.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.bo.GroupChatBo;
import org.dromara.im.domain.vo.GroupChatVo;
import org.dromara.system.domain.vo.SysUserVo;

import java.util.List;
import java.util.Map;

/**
 * 群聊Service接口
 *
 * @author luojiaju
 * @date 2024-01-11
 */
public interface IGroupChatService {

    /**
     * 查询群聊
     */
    Map<String, Object> queryById(Long id);

    /**
     * 查询群聊列表
     */
    TableDataInfo<GroupChatVo> queryPageList(GroupChatBo bo, PageQuery pageQuery);

    /**
     * 查询群聊列表
     */
    List<GroupChatVo> queryList(GroupChatBo bo);

    /**
     * 新增群聊
     */
    GroupChatBo insertByBo(GroupChatBo bo);

    /**
     * 修改群聊
     */
    Boolean updateByBo(GroupChatBo bo);


    /**
     * 用户加入群聊
     */
    Boolean userJoiningGroupChat(GroupChatBo bo);

    /**
     * 校验并批量删除群聊信息
     */
    Boolean deleteWithValidByGroupIdAndUserId(Long groupId, Long userId);

    TableDataInfo<GroupChatVo> queryTheCurrentUsersGroupChatList(GroupChatBo bo, PageQuery pageQuery);

    TableDataInfo<SimpleUser> queryUserListThroughGroupChatId(GroupChatBo bo, PageQuery pageQuery);


    /**
     * 退出群聊
     *
     * @return
     */
    Boolean removeByGroupIdAndUserId(Long groupId, Long userId);
}
