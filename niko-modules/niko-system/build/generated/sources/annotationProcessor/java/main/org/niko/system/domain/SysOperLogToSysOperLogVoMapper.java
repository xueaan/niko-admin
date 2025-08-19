package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysOperLogBoToSysOperLogMapper;
import org.niko.system.domain.vo.SysOperLogVo;
import org.niko.system.domain.vo.SysOperLogVoToSysOperLogMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOperLogVoToSysOperLogMapper.class,SysOperLogBoToSysOperLogMapper.class},
    imports = {}
)
public interface SysOperLogToSysOperLogVoMapper extends BaseMapper<SysOperLog, SysOperLogVo> {
}
