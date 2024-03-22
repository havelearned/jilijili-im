package org.dromara.im.service;

import org.dromara.im.domain.Card;
import org.dromara.im.domain.vo.CardVo;
import org.dromara.im.domain.bo.CardBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 社交卡片Service接口
 *
 * @author luojiaju
 * @date 2024-02-12
 */
public interface ICardService {

    /**
     * 查询社交卡片
     */
    CardVo queryById(Long id);

    /**
     * 查询社交卡片列表
     */
    TableDataInfo<CardVo> queryPageList(CardBo bo, PageQuery pageQuery);

    /**
     * 查询社交卡片列表
     */
    List<CardVo> queryList(CardBo bo);

    /**
     * 新增社交卡片
     */
    Boolean insertByBo(CardBo bo);

    /**
     * 修改社交卡片
     */
    Boolean updateByBo(CardBo bo);

    /**
     * 校验并批量删除社交卡片信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
