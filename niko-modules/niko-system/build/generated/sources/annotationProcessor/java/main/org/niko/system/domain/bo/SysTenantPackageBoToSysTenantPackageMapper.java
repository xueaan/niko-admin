package org.niko.system.domain.bo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.niko.system.domain.SysTenantPackage;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {},
    imports = {}
)
public interface SysTenantPackageBoToSysTenantPackageMapper extends BaseMapper<SysTenantPackageBo, SysTenantPackage> {
  @Mapping(
      target = "menuIds",
      expression = "java(org.niko.common.core.utils.StringUtils.join(source.getMenuIds(), \",\"))"
  )
  SysTenantPackage convert(SysTenantPackageBo source);

  @Mapping(
      target = "menuIds",
      expression = "java(org.niko.common.core.utils.StringUtils.join(source.getMenuIds(), \",\"))"
  )
  SysTenantPackage convert(SysTenantPackageBo source, @MappingTarget SysTenantPackage target);
}
