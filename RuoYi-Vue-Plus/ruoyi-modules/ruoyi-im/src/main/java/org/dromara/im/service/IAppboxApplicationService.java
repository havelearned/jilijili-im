package org.dromara.im.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.im.domain.AppboxApplication;
import org.dromara.im.domain.vo.AppboxApplicationVo;
import org.dromara.im.domain.bo.AppboxApplicationBo;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.domain.vo.SysUserVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 创建应用or频道Service接口
 *
 * @author luojiaju
 * @date 2024-01-04
 */
public interface IAppboxApplicationService {

    /**
     * 查询创建应用or频道
     */
    AppboxApplicationVo queryById(Long id);


    /**
     * 查询创建应用or频道列表
     */
    List<AppboxApplicationVo> queryList(AppboxApplicationBo bo);

    /**
     * 新增创建应用or频道
     */
    Boolean insertByBo(AppboxApplicationBo bo);

    /**
     * 修改创建应用or频道
     */
    Boolean updateByBo(AppboxApplicationBo bo);

    /**
     * 校验并批量删除创建应用or频道信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * 查询本次请求用户的应用列表
     */
    List<AppboxApplicationVo> queryAppByUser();

    /**
     * 查询应用的树形结构
     */
    List<AppboxApplicationVo> getTreeInfo(Long id);

    Page<SysUserVo> getUsersUnderTheApplication(Serializable appId, SysUserBo sysUserBo, PageQuery pageQuery);

    /**
     * 为用户添加应用
     */
    Boolean addUserClass(Long appId);

    /**
     * 为用户退出应用
     */
    Boolean removeUserClass(Long appId);
}
