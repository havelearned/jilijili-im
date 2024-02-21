package org.dromara.im.service;

import org.dromara.im.domain.MsgAction;
import org.dromara.im.domain.vo.MsgActionVo;
import org.dromara.im.domain.bo.MsgActionBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 聊天动作Service接口
 *
 * @author luojiaju
 * @date 2024-02-20
 */
public interface IMsgActionService {

    /**
     * 查询聊天动作
     */
    MsgActionVo queryById(Long id);

    /**
     * 查询聊天动作列表
     */
    TableDataInfo<MsgActionVo> queryPageList(MsgActionBo bo, PageQuery pageQuery);

    /**
     * 查询聊天动作列表
     */
    List<MsgActionVo> queryList(MsgActionBo bo);

    /**
     * 新增聊天动作
     */
    Boolean insertByBo(MsgActionBo bo);

    /**
     * 修改聊天动作
     */
    Boolean updateByBo(MsgActionBo bo);

    /**
     * 校验并批量删除聊天动作信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
