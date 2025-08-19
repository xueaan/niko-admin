package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysSocialBoToSysSocialMapper;
import org.niko.system.domain.vo.SysSocialVo;
import org.niko.system.domain.vo.SysSocialVoToSysSocialMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysSocialBoToSysSocialMapper.class,SysSocialVoToSysSocialMapper.class},
    imports = {}
)
public interface SysSocialToSysSocialVoMapper extends BaseMapper<SysSocial, SysSocialVo> {
}
