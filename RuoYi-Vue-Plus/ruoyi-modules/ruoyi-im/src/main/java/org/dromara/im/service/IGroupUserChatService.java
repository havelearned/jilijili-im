package org.dromara.im.service;

import org.dromara.im.domain.GroupUserChat;
import org.dromara.im.domain.vo.GroupUserChatVo;
import org.dromara.im.domain.bo.GroupUserChatBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * groupUserChatService接口
 *
 * @author luojiaju
 * @date 2024-02-10
 */
public interface IGroupUserChatService {

    /**
     * 查询groupUserChat
     */
    GroupUserChatVo queryById(Long userId);

    /**
     * 查询groupUserChat列表
     */
    TableDataInfo<GroupUserChatVo> queryPageList(GroupUserChatBo bo, PageQuery pageQuery);

    /**
     * 查询groupUserChat列表
     */
    List<GroupUserChatVo> queryList(GroupUserChatBo bo);

    /**
     * 新增groupUserChat
     */
    Boolean insertByBo(GroupUserChatBo bo);

    /**
     * 修改groupUserChat
     */
    Boolean updateByBo(GroupUserChatBo bo);

    /**
     * 校验并批量删除groupUserChat信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
