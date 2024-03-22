package org.dromara.im.service;

import org.dromara.im.domain.vo.ImNoticeVo;
import org.dromara.im.domain.bo.ImNoticeBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户通知Service接口
 *
 * @author luojiaju
 * @date 2024-01-05
 */
public interface IImNoticeService {

    /**
     * 查询用户通知
     */
    ImNoticeVo queryById(Long id);

    /**
     * 查询用户通知列表
     */
    TableDataInfo<ImNoticeVo> queryPageList(ImNoticeBo bo, PageQuery pageQuery);

    /**
     * 查询用户通知列表
     */
    List<ImNoticeVo> queryList(ImNoticeBo bo);

    /**
     * 新增用户通知
     */
    Boolean insertByBo(ImNoticeBo bo);

    /**
     * 修改用户通知
     */
    Boolean updateByBo(ImNoticeBo bo);

    /**
     * 校验并批量删除用户通知信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
