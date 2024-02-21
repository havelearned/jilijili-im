package org.dromara.im.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.im.domain.GroupChat;
import org.dromara.im.domain.GroupUserChat;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.vo.GroupChatVo;
import org.dromara.im.domain.vo.GroupUserChatVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.springframework.messaging.simp.user.SimpUser;

/**
 * groupUserChatMapper接口
 *
 * @author luojiaju
 * @date 2024-02-10
 */
public interface GroupUserChatMapper extends BaseMapperPlus<GroupUserChat, GroupUserChatVo> {


    /**
     * 获取当前用户的群聊列表
     *
     */
    @Select("""
        select  ig.*
        from im_group_user_chat gu
                 inner join im_group_chat ig on ig.id = gu.group_id
        ${ew.getCustomSqlSegment}
        """)
    Page<GroupChatVo> selectUserGroup(@Param(Constants.WRAPPER) Wrapper<GroupChat> wrapper,
                                                          Page<GroupChat> page);



    /**
     * 查询当前群聊的用户列表
     */
    @Select("""
             select su.*
             from im_group_user_chat gu
                      join sys_user su on gu.user_id = su.user_id
            ${ew.getCustomSqlSegment}
             """)
    Page<SysUserVo> selectUserList(@Param(Constants.WRAPPER) Wrapper<GroupChat> wrapper,
                                    Page<GroupChat> page);


}
