package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysTenantPackageBoToSysTenantPackageMapper;
import org.niko.system.domain.vo.SysTenantPackageVo;
import org.niko.system.domain.vo.SysTenantPackageVoToSysTenantPackageMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysTenantPackageVoToSysTenantPackageMapper.class,SysTenantPackageBoToSysTenantPackageMapper.class},
    imports = {}
)
public interface SysTenantPackageToSysTenantPackageVoMapper extends BaseMapper<SysTenantPackage, SysTenantPackageVo> {
}
