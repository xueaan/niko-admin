package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysDictTypeBoToSysDictTypeMapper;
import org.niko.system.domain.vo.SysDictTypeVo;
import org.niko.system.domain.vo.SysDictTypeVoToSysDictTypeMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysDictTypeVoToSysDictTypeMapper.class,SysDictTypeBoToSysDictTypeMapper.class},
    imports = {}
)
public interface SysDictTypeToSysDictTypeVoMapper extends BaseMapper<SysDictType, SysDictTypeVo> {
}
