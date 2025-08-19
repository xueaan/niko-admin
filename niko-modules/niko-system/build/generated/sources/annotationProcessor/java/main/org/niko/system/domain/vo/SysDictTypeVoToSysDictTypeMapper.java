package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysDictType;
import org.niko.system.domain.SysDictTypeToSysDictTypeVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysDictTypeToSysDictTypeVoMapper.class},
    imports = {}
)
public interface SysDictTypeVoToSysDictTypeMapper extends BaseMapper<SysDictTypeVo, SysDictType> {
}
