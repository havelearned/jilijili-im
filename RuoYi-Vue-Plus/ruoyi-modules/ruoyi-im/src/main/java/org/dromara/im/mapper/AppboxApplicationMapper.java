package org.dromara.im.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.im.domain.AppboxApplication;
import org.dromara.im.domain.vo.AppboxApplicationVo;
import org.dromara.system.domain.vo.SysUserVo;

import java.io.Serializable;
import java.util.List;

/**
 * 创建应用or频道Mapper接口
 *
 * @author luojiaju
 * @date 2024-01-04
 */
public interface AppboxApplicationMapper extends BaseMapperPlus<AppboxApplication, AppboxApplicationVo> {

    List<Long> selectVoByAppId(@Param("appId") Long appId);

    /**
     * 通过用户id查询应用信息
     *
     * @param id
     * @return
     */
    List<AppboxApplicationVo> queryAppByUserId(@Param("id") Serializable id);

    List<AppboxApplicationVo> getTreeInfo(@Param("id") Long id);




    Page<SysUserVo> getUsersUnderTheApplicationV2(@Param(Constants.WRAPPER) Wrapper<Object> query, Page<SysUserVo> page);
}
