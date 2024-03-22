package org.dromara.im.mapper.convert;

import io.github.linpeilie.AutoMapperConfig;
import io.github.linpeilie.BaseMapper;
import org.dromara.im.domain.SimpleUser;
import org.dromara.system.domain.SysRole;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysRoleVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 15/01/2024
 */
@Mapper(
    config = AutoMapperConfig.class,
    uses = {},
    imports = {}
)
public interface SimpleUserMapper extends BaseMapper<SysUser, SimpleUser> {
    List<SimpleUser> convert(List<SysUser> source, @MappingTarget List<SimpleUser> target);

    SimpleUser convert(SysUser source, @MappingTarget SimpleUser target);

    SimpleUser convert(SysUserVo source, @MappingTarget SimpleUser target);


    SysUserVo convert(SimpleUser source, @MappingTarget SysUserVo target);
}
