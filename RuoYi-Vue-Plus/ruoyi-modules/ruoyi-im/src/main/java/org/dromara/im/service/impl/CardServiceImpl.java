package org.dromara.im.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.im.domain.bo.CardBo;
import org.dromara.im.domain.vo.CardVo;
import org.dromara.im.domain.Card;
import org.dromara.im.mapper.CardMapper;
import org.dromara.im.service.ICardService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 社交卡片Service业务层处理
 *
 * @author luojiaju
 * @date 2024-02-12
 */
@RequiredArgsConstructor
@Service
public class CardServiceImpl implements ICardService {

    private final CardMapper baseMapper;

    /**
     * 查询社交卡片
     */
    @Override
    public CardVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询社交卡片列表
     */
    @Override
    public TableDataInfo<CardVo> queryPageList(CardBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Card> lqw = buildQueryWrapper(bo);
        Page<CardVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询社交卡片列表
     */
    @Override
    public List<CardVo> queryList(CardBo bo) {
        LambdaQueryWrapper<Card> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Card> buildQueryWrapper(CardBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Card> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCardName()), Card::getCardName, bo.getCardName());
        lqw.between(params.get("beginPrice") != null && params.get("endPrice") != null,
            Card::getPrice ,params.get("beginPrice"), params.get("endPrice"));
        lqw.eq(StringUtils.isNotBlank(bo.getRarity()), Card::getRarity, bo.getRarity());
        lqw.eq(bo.getCardStatus() != null, Card::getCardStatus, bo.getCardStatus());
        return lqw;
    }

    /**
     * 新增社交卡片
     */
    @Override
    public Boolean insertByBo(CardBo bo) {
        Card add = MapstructUtils.convert(bo, Card.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改社交卡片
     */
    @Override
    public Boolean updateByBo(CardBo bo) {
        Card update = MapstructUtils.convert(bo, Card.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Card entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除社交卡片
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
