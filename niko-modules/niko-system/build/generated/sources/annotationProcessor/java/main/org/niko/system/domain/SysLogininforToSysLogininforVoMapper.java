package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysLogininforBoToSysLogininforMapper;
import org.niko.system.domain.vo.SysLogininforVo;
import org.niko.system.domain.vo.SysLogininforVoToSysLogininforMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysLogininforBoToSysLogininforMapper.class,SysLogininforVoToSysLogininforMapper.class},
    imports = {}
)
public interface SysLogininforToSysLogininforVoMapper extends BaseMapper<SysLogininfor, SysLogininforVo> {
}
