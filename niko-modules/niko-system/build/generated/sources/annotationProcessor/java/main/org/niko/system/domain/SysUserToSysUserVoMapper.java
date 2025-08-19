package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysUserBoToSysUserMapper;
import org.niko.system.domain.vo.SysRoleVoToSysRoleMapper;
import org.niko.system.domain.vo.SysUserVo;
import org.niko.system.domain.vo.SysUserVoToSysUserMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysRoleVoToSysRoleMapper.class,SysRoleToSysRoleVoMapper.class,SysUserBoToSysUserMapper.class,SysUserVoToSysUserMapper.class},
    imports = {}
)
public interface SysUserToSysUserVoMapper extends BaseMapper<SysUser, SysUserVo> {
}
