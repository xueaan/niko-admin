package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysDept;
import org.niko.system.domain.SysDeptToSysDeptVoMapper;
import org.niko.system.domain.bo.SysDeptBoToSysDeptMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysDeptBoToSysDeptMapper.class,SysDeptToSysDeptVoMapper.class,SysDeptToSysDeptVoMapper.class},
    imports = {}
)
public interface SysDeptVoToSysDeptMapper extends BaseMapper<SysDeptVo, SysDept> {
}
