package org.dromara.im.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.im.domain.GroupChat;
import org.dromara.im.domain.vo.GroupChatVo;

import java.util.List;
import java.util.Map;

/**
 * 群聊Mapper接口
 *
 * @author luojiaju
 * @date 2024-01-11
 */
public interface GroupChatMapper extends BaseMapperPlus<GroupChat, GroupChatVo> {

    /**
     * 插入<strong>用户</strong>群聊信息
     */
    Boolean insertAssociationUserJoiningGroupChat(@Param("groupId") Long id,
                                                  @Param("userIds") List<Long> userIds);

    /**
     * 通过群聊id和用户id 删除<strong>用户</strong>群聊信息
     */
    Boolean deleteAssociationByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);


    /**
     * 通过群聊id和用户id查询<strong>用户</strong>群聊信息列表
     *
     * @param params userId groupName
     * @return 群聊列表
     */
    List<GroupChatVo> selectAssociationByGroupIdAndUserId(Map<String, Object> params);


    /**
     * 通过群聊id查询群聊总人数
     *
     * @param groupId 群聊id
     * @return 群聊人数
     */
    Integer selectGroupCountByGroupId(@Param("groupId") Long groupId);

    /**
     * 查询群聊用户id
     * @param id 群聊id
     * @return 用户id
     */
    List<Long> selectGroupChatUsersByGroupId(@Param("id") Long id);
}
