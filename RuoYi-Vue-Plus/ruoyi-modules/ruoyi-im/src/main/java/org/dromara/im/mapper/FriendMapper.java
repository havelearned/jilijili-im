package org.dromara.im.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.im.domain.Friend;
import org.dromara.im.domain.bo.FriendBo;
import org.dromara.im.domain.vo.FriendVo;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysUserVo;

/**
 * 好友Mapper接口
 *
 * @author luojiaju
 * @date 2024-01-12
 */
public interface FriendMapper extends BaseMapperPlus<Friend, FriendVo> {

    /**
     *  查询好友分页列表
     */
    Page<FriendVo> queryPageListByUserId(Page<FriendVo> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Friend> lqw);

    /**
     * 查询好友信息
     */
    IPage<SysUserVo> selectUserFriendById(@Param(Constants.WRAPPER) Wrapper<Friend> lqw, Page<Object> page);

    Page<SysUserVo> selectPageUserList(Page<SysUser> page,@Param(Constants.WRAPPER) QueryWrapper<SysUser> lam);
}
