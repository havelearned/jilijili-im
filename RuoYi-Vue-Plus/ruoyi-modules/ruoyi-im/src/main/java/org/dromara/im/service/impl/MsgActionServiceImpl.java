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
import org.dromara.im.domain.bo.MsgActionBo;
import org.dromara.im.domain.vo.MsgActionVo;
import org.dromara.im.domain.MsgAction;
import org.dromara.im.mapper.MsgActionMapper;
import org.dromara.im.service.IMsgActionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 聊天动作Service业务层处理
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@RequiredArgsConstructor
@Service
public class MsgActionServiceImpl implements IMsgActionService {

    private final MsgActionMapper baseMapper;

    /**
     * 查询聊天动作
     */
    @Override
    public MsgActionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询聊天动作列表
     */
    @Override
    public TableDataInfo<MsgActionVo> queryPageList(MsgActionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MsgAction> lqw = buildQueryWrapper(bo);
        Page<MsgActionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询聊天动作列表
     */
    @Override
    public List<MsgActionVo> queryList(MsgActionBo bo) {
        LambdaQueryWrapper<MsgAction> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MsgAction> buildQueryWrapper(MsgActionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MsgAction> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMsgId() != null, MsgAction::getMsgId, bo.getMsgId());
        lqw.between(params.get("beginEndorseCount") != null && params.get("endEndorseCount") != null,
            MsgAction::getEndorseCount ,params.get("beginEndorseCount"), params.get("endEndorseCount"));
        lqw.between(params.get("beginFullCount") != null && params.get("endFullCount") != null,
            MsgAction::getFullCount ,params.get("beginFullCount"), params.get("endFullCount"));
        lqw.between(params.get("beginLikeCount") != null && params.get("endLikeCount") != null,
            MsgAction::getLikeCount ,params.get("beginLikeCount"), params.get("endLikeCount"));
        return lqw;
    }

    /**
     * 新增聊天动作
     */
    @Override
    public Boolean insertByBo(MsgActionBo bo) {
        MsgAction add = MapstructUtils.convert(bo, MsgAction.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改聊天动作
     */
    @Override
    public Boolean updateByBo(MsgActionBo bo) {
        MsgAction update = MapstructUtils.convert(bo, MsgAction.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MsgAction entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除聊天动作
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
