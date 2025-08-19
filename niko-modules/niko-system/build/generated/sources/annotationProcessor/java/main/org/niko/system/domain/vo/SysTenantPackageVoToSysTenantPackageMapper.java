package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysTenantPackage;
import org.niko.system.domain.SysTenantPackageToSysTenantPackageVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysTenantPackageToSysTenantPackageVoMapper.class},
    imports = {}
)
public interface SysTenantPackageVoToSysTenantPackageMapper extends BaseMapper<SysTenantPackageVo, SysTenantPackage> {
}
