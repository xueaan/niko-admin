package org.niko.system.domain.bo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.common.log.event.OperLogEventToSysOperLogBoMapper;
import org.niko.system.domain.SysOperLog;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOperLogBoToOperLogEventMapper.class,OperLogEventToSysOperLogBoMapper.class},
    imports = {}
)
public interface SysOperLogBoToSysOperLogMapper extends BaseMapper<SysOperLogBo, SysOperLog> {
}
