package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysPostBoToSysPostMapper;
import org.niko.system.domain.vo.SysPostVo;
import org.niko.system.domain.vo.SysPostVoToSysPostMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysPostBoToSysPostMapper.class,SysPostVoToSysPostMapper.class},
    imports = {}
)
public interface SysPostToSysPostVoMapper extends BaseMapper<SysPost, SysPostVo> {
}
