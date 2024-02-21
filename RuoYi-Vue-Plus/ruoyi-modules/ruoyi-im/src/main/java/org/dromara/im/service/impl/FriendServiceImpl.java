package org.dromara.im.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.im.domain.Friend;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.bo.FriendBo;
import org.dromara.im.domain.vo.FriendVo;
import org.dromara.im.mapper.FriendMapper;
import org.dromara.im.service.IFriendService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.dromara.im.utils.SysUserUtils.getCurrentUserId;
import static org.dromara.im.utils.SysUserUtils.getOnlineUsers;

/**
 * 好友Service业务层处理
 *
 * @author luojiaju
 * @date 2024-01-12
 */
@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements IFriendService {

    private final FriendMapper baseMapper;

    private final SysUserMapper sysUserMapper;

    private final SysOssMapper sysOssMapper;

    /**
     * 查询好友
     */
    @Override
    public FriendVo queryById(Long id) {
        SysUserVo userVo = sysUserMapper.selectUserById(id);
        if (userVo == null) {
            throw new ServiceException("好友不存在");
        }
        SimpleUser simpleUser = new SimpleUser();
        BeanUtils.copyProperties(userVo, simpleUser);
        FriendVo friendVo = baseMapper.selectVoById(id);
        friendVo.setFriendUser(simpleUser);
        return friendVo;
    }

    /**
     * 通过用户id查询好友列表
     */
    @Override
    public TableDataInfo<SimpleUser> queryPageList(FriendBo bo, PageQuery pageQuery) {
        // 查询用户好友
        QueryWrapper<Friend> query = Wrappers.query();
        query.eq("imf.user_id", getCurrentUserId())
            .eq(bo.getRelationStatus() != null, "imf.relation_status", bo.getRelationStatus())
            .orderByDesc("imf.create_time");

        if (StringUtils.isNotEmpty(bo.getUserName())) {
            query.like(true, "su.user_name", bo.getUserName())
                .eq(bo.getFriendId() != null, "imf.friend_id", bo.getFriendId());
        }

        IPage<SysUserVo> result = baseMapper.selectUserFriendById(query, pageQuery.build());
        List<SysUserVo> userVoList = result.getRecords();

        List<SimpleUser> simpleUserList = getOnlineUsers(userVoList);
        IPage<SimpleUser> userIPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        userIPage.setRecords(simpleUserList);
        return TableDataInfo.build(userIPage);
    }

    /**
     * 查询好友列表
     */
    @Override
    public List<FriendVo> queryList(FriendBo bo) {
        LambdaQueryWrapper<Friend> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Friend> buildQueryWrapper(FriendBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Friend> lqw = Wrappers.lambdaQuery();
        lqw.eq(Friend::getUserId, bo.getUserId());
        lqw.eq(bo.getFriendId() != null, Friend::getFriendId, bo.getFriendId());
        lqw.eq(bo.getRelationStatus() != null, Friend::getRelationStatus, bo.getRelationStatus());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
            Friend::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
        return lqw;
    }

    /**
     * 新增好友
     */
    @Override
    public Boolean insertByBo(FriendBo bo) {
        Friend add = MapstructUtils.convert(bo, Friend.class);
        // 校验是否添加自己
        validEntityBeforeMy(add);

        // 校验是否被屏蔽|黑名单
        validEntityBeforeSave(add);

        // 校验是否在好友列表
        verifyIfItIsOnTheFriendList(add);

        // 校验是否在申请列表
        verifyIfItIsOnTheApplicationList(add);

        // 保持到数据库
        add.setFriendId(bo.getUserId());
        add.setUserId(bo.getFriendId());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
            // TODO 给好友发送通知, xxx 发出还有申请 通过|拒绝
            // 1. websocket 发送通知
            // 2. 通知表保存消息
        }
        return flag;
    }

    // 校验是否在申请列表
    private void verifyIfItIsOnTheApplicationList(Friend add) {
        LambdaQueryWrapper<Friend> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Friend::getUserId, add.getFriendId())
            .eq(Friend::getFriendId, getCurrentUserId());
        Long l = this.baseMapper.selectCount(lambdaQuery);
        if (l > 0) {
            throw new UserException("已发送好友申请!!!");
        }
    }

    // 校验是否在好友列表
    private void verifyIfItIsOnTheFriendList(Friend add) {
        Serializable userId = getCurrentUserId();
        LambdaQueryWrapper<Friend> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Friend::getUserId, userId)
            .eq(Friend::getFriendId, add.getFriendId());
        Long l = this.baseMapper.selectCount(lambdaQuery);
        if (l > 0) {
            throw new UserException("已在您的好友列表中");
        }
    }


    /**
     * 保存前的数据校验 是否被屏蔽
     */
    private void validEntityBeforeSave(Friend entity) {
        LambdaQueryWrapper<Friend> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Friend::getUserId, entity.getFriendId())
            .eq(Friend::getFriendId, getCurrentUserId())
            .eq(Friend::getRelationStatus, 3);
        Long l = this.baseMapper.selectCount(lambdaQuery);
        if (l > 1) {
            throw new ServiceException("对方已经屏蔽您!!!");
        }

        // 检查是否在当前用户的屏蔽列表中
        checkIfItIsInTheCurrentUsersBlockingList(entity);

    }

    private void checkIfItIsInTheCurrentUsersBlockingList(Friend entity) {
        LambdaQueryWrapper<Friend> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Friend::getUserId, getCurrentUserId())
            .eq(Friend::getFriendId, entity.getFriendId())
            .eq(Friend::getRelationStatus, 3);
        Long l = this.baseMapper.selectCount(lambdaQuery);
        if (l > 0) {
            throw new ServiceException("对方在您的黑名单中,请解除后再操作!!!");
        }

    }

    /**
     * 修改好友
     */
    @Override
    public Boolean updateByBo(FriendBo bo) {
        Friend update = MapstructUtils.convert(bo, Friend.class);
        validEntityBeforeSave(update);
        LambdaUpdateWrapper<Friend> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(true, Friend::getUserId, update.getUserId())
            .eq(true, Friend::getFriendId, update.getFriendId())
            .set(true, Friend::getRelationStatus, update.getRelationStatus());
        return baseMapper.update(update, updateWrapper) > 0;
    }


    // 验证是否添加了自己
    private void validEntityBeforeMy(Friend entity) {
        // 不能添加自己为好友
        if (entity.getFriendId().equals(entity.getUserId())) {
            throw new UserException("不能添加自己!!!");
        }
    }

    /**
     * 批量删除好友
     */
    @Override
    public Boolean deleteWithValidByIds(Serializable friendId, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        LambdaQueryWrapper<Friend> lam = Wrappers.lambdaQuery();
        lam.eq(Friend::getUserId, getCurrentUserId())
            .eq(Friend::getFriendId, friendId);
        int delete = baseMapper.delete(lam);
        lam.clear();
        lam.eq(Friend::getUserId, friendId)
            .eq(Friend::getFriendId, getCurrentUserId());
        int deleted = baseMapper.delete(lam);
        return delete == deleted;
    }

    @Override
    public TableDataInfo<SimpleUser> searchPageList(FriendBo bo, PageQuery pageQuery) {
        QueryWrapper<SysUser> lam = Wrappers.query();
        lam.eq(bo.getUserId() != null, "su.user_id,", bo.getUserId())
            .like(StringUtils.isNotEmpty(bo.getUserName()), "su.user_name", bo.getUserName())
            .orderByDesc("su.create_time");
        Page<SysUser> page = pageQuery.build();
        Page<SysUserVo> userVoPage = baseMapper.selectPageUserList(page, lam);
        IPage<SimpleUser> resultPage = userVoPage.convert(user -> {
            SysOssVo sysOssVo = sysOssMapper.selectVoById(user.getAvatar());
            SimpleUser simpleUser = new SimpleUser();
            simpleUser.setUserName(user.getUserName());
            simpleUser.setUserId(user.getUserId());
            simpleUser.setNickName(user.getNickName());
            simpleUser.setOnline(user.getOnline());
            simpleUser.setUserType(user.getUserType());
            simpleUser.setSex(user.getSex());

            if (sysOssVo != null) {
                simpleUser.setAvatar(sysOssVo.getUrl());
            }
            return simpleUser;
        });
        return TableDataInfo.build(resultPage);
    }

    @Override
    public Boolean acceptFriend(FriendBo bo) {
        Boolean b = this.updateByBo(bo);

        if (b) {
            // 互相好友
            Friend add = MapstructUtils.convert(bo, Friend.class);
            add.setFriendId(bo.getUserId());
            add.setUserId(bo.getFriendId());
            this.baseMapper.insert(add);

            // 构建消息
            //TODO 用户是否在线,在线发送通知
            Long friendId = bo.getFriendId();
            SysUserVo userVo = sysUserMapper.selectUserById(friendId);
            String friendToken = StpUtil.getTokenValueByLoginId(userVo.getUserType() + ":" + userVo.getUserId());
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(friendToken) > 1) {
                // 在线发送通知
            } else {
                // 不在线发送离线通知
            }
        }
        return b;
    }
}
