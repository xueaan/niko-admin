package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__627;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.web.domain.vo.TenantListVo;
import org.niko.web.domain.vo.TenantListVoToSysTenantVoMapper;

@Mapper(
    config = AutoMapperConfig__627.class,
    uses = {TenantListVoToSysTenantVoMapper.class},
    imports = {}
)
public interface SysTenantVoToTenantListVoMapper extends BaseMapper<SysTenantVo, TenantListVo> {
}
