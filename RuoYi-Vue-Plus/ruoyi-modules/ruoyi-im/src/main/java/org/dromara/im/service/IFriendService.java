package org.dromara.im.service;

import org.dromara.im.domain.Friend;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.vo.FriendVo;
import org.dromara.im.domain.bo.FriendBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 好友Service接口
 *
 * @author luojiaju
 * @date 2024-01-12
 */
public interface IFriendService {

    /**
     * 查询好友
     */
    FriendVo queryById(Long id);

    /**
     * 通过用户id 查询好友列表
     */
    TableDataInfo<SimpleUser> queryPageList(FriendBo bo, PageQuery pageQuery);

    /**
     * 查询好友列表
     */
    List<FriendVo> queryList(FriendBo bo);

    /**
     * 新增好友
     */
    Boolean insertByBo(FriendBo bo);

    /**
     * 修改好友
     */
    Boolean updateByBo(FriendBo bo);

    /**
     * 校验并批量删除好友信息
     */
    Boolean deleteWithValidByIds(Serializable friendId, Boolean isValid);

    Boolean acceptFriend(FriendBo friend);

    TableDataInfo<SimpleUser> searchPageList(FriendBo bo, PageQuery pageQuery);
}
