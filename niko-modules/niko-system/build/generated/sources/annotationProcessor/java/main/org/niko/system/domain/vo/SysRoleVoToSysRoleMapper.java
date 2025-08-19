package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysRole;
import org.niko.system.domain.SysRoleToSysRoleVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysRoleToSysRoleVoMapper.class},
    imports = {}
)
public interface SysRoleVoToSysRoleMapper extends BaseMapper<SysRoleVo, SysRole> {
}
