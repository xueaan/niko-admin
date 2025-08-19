package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysConfigBoToSysConfigMapper;
import org.niko.system.domain.vo.SysConfigVo;
import org.niko.system.domain.vo.SysConfigVoToSysConfigMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysConfigVoToSysConfigMapper.class,SysConfigBoToSysConfigMapper.class},
    imports = {}
)
public interface SysConfigToSysConfigVoMapper extends BaseMapper<SysConfig, SysConfigVo> {
}
