package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysTenantBoToSysTenantMapper;
import org.niko.system.domain.vo.SysTenantVo;
import org.niko.system.domain.vo.SysTenantVoToSysTenantMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysTenantVoToSysTenantMapper.class,SysTenantBoToSysTenantMapper.class},
    imports = {}
)
public interface SysTenantToSysTenantVoMapper extends BaseMapper<SysTenant, SysTenantVo> {
}
