package org.dromara.im.service;

import org.dromara.im.domain.AppboxFixedAppUserConfig;
import org.dromara.im.domain.vo.AppboxFixedAppUserConfigVo;
import org.dromara.im.domain.bo.AppboxFixedAppUserConfigBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 应用的固定用户配置Service接口
 *
 * @author luojiaju
 * @date 2024-01-04
 */
public interface IAppboxFixedAppUserConfigService {

    /**
     * 查询应用的固定用户配置
     */
    AppboxFixedAppUserConfigVo queryById(Long id);

    /**
     * 查询应用的固定用户配置列表
     */
    TableDataInfo<AppboxFixedAppUserConfigVo> queryPageList(AppboxFixedAppUserConfigBo bo, PageQuery pageQuery);

    /**
     * 查询应用的固定用户配置列表
     */
    List<AppboxFixedAppUserConfigVo> queryList(AppboxFixedAppUserConfigBo bo);

    /**
     * 新增应用的固定用户配置
     */
    Boolean insertByBo(AppboxFixedAppUserConfigBo bo);

    /**
     * 修改应用的固定用户配置
     */
    Boolean updateByBo(AppboxFixedAppUserConfigBo bo);

    /**
     * 校验并批量删除应用的固定用户配置信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
