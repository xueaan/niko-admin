package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysDictDataBoToSysDictDataMapper;
import org.niko.system.domain.vo.SysDictDataVo;
import org.niko.system.domain.vo.SysDictDataVoToSysDictDataMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysDictDataVoToSysDictDataMapper.class,SysDictDataBoToSysDictDataMapper.class},
    imports = {}
)
public interface SysDictDataToSysDictDataVoMapper extends BaseMapper<SysDictData, SysDictDataVo> {
}
