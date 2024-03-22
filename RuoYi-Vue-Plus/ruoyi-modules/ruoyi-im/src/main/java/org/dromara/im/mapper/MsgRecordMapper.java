package org.dromara.im.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.im.domain.MsgRecord;
import org.dromara.im.domain.bo.MsgRecordBo;
import org.dromara.im.domain.vo.MsgRecordVo;

import java.util.List;

/**
 * 聊天消息记录Mapper接口
 *
 * @author luojiaju
 * @date 2024-02-20
 */
public interface MsgRecordMapper extends BaseMapperPlus<MsgRecord, MsgRecordVo> {


    /**
     * 查询用户对好友 一对一的聊天记录
     */
    Page<MsgRecordVo> queryUsersOneOnOneChatHistoryWithFriends(@Param(Constants.WRAPPER) Wrapper<MsgRecord> ew,
                                                                     @Param("page") IPage<MsgRecordVo> page);


    /**
     * 查询用户群聊的聊天记录
     */
    Page<MsgRecordVo> queryChatRecordsOfUserGroupChats(@Param(Constants.WRAPPER) Wrapper<MsgRecord> ew,
                                                               @Param("page") IPage<MsgRecordVo> page);


    /**
     * 查询频道的聊天记录
     */
    Page<MsgRecordVo> queryChatHistoryOfTheChannel(@Param(Constants.WRAPPER) Wrapper<MsgRecord> ew,
                                                       @Param("page") IPage<MsgRecordVo> page);

    /**
     * 查询单个消息
     * @param id
     * @return
     */
    MsgRecordVo selectVoByIdUser(@Param("id") Long id);


//    Integer selectUnreadInteger(@Param(Constants.WRAPPER) Wrapper<MsgRecord> ew);
}
