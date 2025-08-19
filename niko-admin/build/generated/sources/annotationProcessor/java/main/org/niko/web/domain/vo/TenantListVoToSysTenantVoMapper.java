package org.niko.web.domain.vo;

import io.github.linpeilie.AutoMapperConfig__627;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.vo.SysTenantVo;
import org.niko.system.domain.vo.SysTenantVoToTenantListVoMapper;

@Mapper(
    config = AutoMapperConfig__627.class,
    uses = {SysTenantVoToTenantListVoMapper.class},
    imports = {}
)
public interface TenantListVoToSysTenantVoMapper extends BaseMapper<TenantListVo, SysTenantVo> {
}
