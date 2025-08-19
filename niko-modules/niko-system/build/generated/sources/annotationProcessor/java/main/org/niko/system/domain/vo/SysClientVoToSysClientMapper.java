package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysClient;
import org.niko.system.domain.SysClientToSysClientVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysClientToSysClientVoMapper.class},
    imports = {}
)
public interface SysClientVoToSysClientMapper extends BaseMapper<SysClientVo, SysClient> {
}
