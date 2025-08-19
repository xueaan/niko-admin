package org.niko.common.log.event;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysOperLogBo;
import org.niko.system.domain.bo.SysOperLogBoToOperLogEventMapper;
import org.niko.system.domain.bo.SysOperLogBoToSysOperLogMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOperLogBoToSysOperLogMapper.class,SysOperLogBoToOperLogEventMapper.class},
    imports = {}
)
public interface OperLogEventToSysOperLogBoMapper extends BaseMapper<OperLogEvent, SysOperLogBo> {
}
