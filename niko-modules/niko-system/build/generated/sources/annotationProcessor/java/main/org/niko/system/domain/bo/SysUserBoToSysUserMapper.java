package org.niko.system.domain.bo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysUser;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {},
    imports = {}
)
public interface SysUserBoToSysUserMapper extends BaseMapper<SysUserBo, SysUser> {
}
