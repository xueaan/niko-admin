package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysRoleBoToSysRoleMapper;
import org.niko.system.domain.vo.SysRoleVo;
import org.niko.system.domain.vo.SysRoleVoToSysRoleMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysRoleVoToSysRoleMapper.class,SysRoleBoToSysRoleMapper.class},
    imports = {}
)
public interface SysRoleToSysRoleVoMapper extends BaseMapper<SysRole, SysRoleVo> {
}
