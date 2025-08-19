package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysMenuBoToSysMenuMapper;
import org.niko.system.domain.vo.SysMenuVo;
import org.niko.system.domain.vo.SysMenuVoToSysMenuMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysMenuVoToSysMenuMapper.class,SysMenuBoToSysMenuMapper.class},
    imports = {}
)
public interface SysMenuToSysMenuVoMapper extends BaseMapper<SysMenu, SysMenuVo> {
}
