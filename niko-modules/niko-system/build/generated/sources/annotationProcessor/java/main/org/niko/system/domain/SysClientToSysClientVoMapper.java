package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysClientBoToSysClientMapper;
import org.niko.system.domain.vo.SysClientVo;
import org.niko.system.domain.vo.SysClientVoToSysClientMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysClientBoToSysClientMapper.class,SysClientVoToSysClientMapper.class},
    imports = {}
)
public interface SysClientToSysClientVoMapper extends BaseMapper<SysClient, SysClientVo> {
}
