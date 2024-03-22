package org.dromara.im.utils;

import cn.dev33.satoken.stp.StpUtil;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.im.domain.SimpleUser;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.mapper.SysOssMapper;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 08/02/2024
 */
public class SysUserUtils {


    public static Serializable getCurrentUserId() {
        String loginId = (String) StpUtil.getLoginId();

        return Optional.ofNullable(loginId)
            .map(id -> id.split(":")[1])
            .orElseThrow(() -> new ServiceException("暂未未登录"));
    }

    public static String getCurrentUserIdToString() {
        String loginId = (String) StpUtil.getLoginId();
        String userId = Optional.ofNullable(loginId)
            .map(id -> id.split(":")[1])
            .orElseThrow(() -> new ServiceException("暂未未登录"));
        return userId;
    }

    public static Long getCurrentUserIdToLong() {
        String loginId = (String) StpUtil.getLoginId();
        String userId = Optional.ofNullable(loginId)
            .map(id -> id.split(":")[1])
            .orElseThrow(() -> new ServiceException("暂未未登录"));
        return Long.valueOf(userId);
    }


    /**
     * 获取在线用户
     *
     * @param sysUserVoList 用户vo列表
     * @return 在线用户可用url
     */
    public static List<SimpleUser> getOnlineUsers(List<SysUserVo> sysUserVoList) {
        SysOssMapper ossMapper = SpringUtils.getBean(SysOssMapper.class);
        return sysUserVoList.parallelStream()
            .map(user -> CompletableFuture.supplyAsync(() -> {
                SimpleUser simpleUser = new SimpleUser();
                BeanUtils.copyProperties(user, simpleUser);
                Long avatarId = user.getAvatar();
                if (avatarId != null) {
                    SysOss sysOss = ossMapper.selectById(avatarId);
                    simpleUser.setAvatar(sysOss != null ? sysOss.getUrl() : null);
                }
                // 用户是否在线
                String token = StpUtil.getTokenValueByLoginId(user.getUserType() + ":" + user.getUserId());
                simpleUser.setOnline(StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) > 1);

                return simpleUser;
            }))
            .map(CompletableFuture::join)
            .toList();
    }

    public static boolean isOnline(SysUserVo user) {
        if (user == null) return false;
        String token = StpUtil.getTokenValueByLoginId(user.getUserType() + ":" + user.getUserId());
        return StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) > 1;
    }
}
