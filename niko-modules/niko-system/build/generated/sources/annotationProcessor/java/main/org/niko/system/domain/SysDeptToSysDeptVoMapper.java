package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysDeptBoToSysDeptMapper;
import org.niko.system.domain.vo.SysDeptVo;
import org.niko.system.domain.vo.SysDeptVoToSysDeptMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysDeptBoToSysDeptMapper.class,SysDeptVoToSysDeptMapper.class,SysDeptBoToSysDeptMapper.class},
    imports = {}
)
public interface SysDeptToSysDeptVoMapper extends BaseMapper<SysDept, SysDeptVo> {
}
