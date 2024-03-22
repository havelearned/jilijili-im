package org.dromara.im.service;

import org.dromara.im.domain.AppUser;
import org.dromara.im.domain.vo.AppUserVo;
import org.dromara.im.domain.bo.AppUserBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户应用关联Service接口
 *
 * @author Lion Li
 * @date 2024-02-18
 */
public interface IAppUserService {

    /**
     * 查询用户应用关联
     */
    AppUserVo queryById(Long userId);

    /**
     * 查询用户应用关联列表
     */
    TableDataInfo<AppUserVo> queryPageList(AppUserBo bo, PageQuery pageQuery);

    /**
     * 查询用户应用关联列表
     */
    List<AppUserVo> queryList(AppUserBo bo);

    /**
     * 新增用户应用关联
     */
    Boolean insertByBo(AppUserBo bo);

    /**
     * 修改用户应用关联
     */
    Boolean updateByBo(AppUserBo bo);

    /**
     * 校验并批量删除用户应用关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
