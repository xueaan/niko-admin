package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysOssConfig;
import org.niko.system.domain.SysOssConfigToSysOssConfigVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOssConfigToSysOssConfigVoMapper.class},
    imports = {}
)
public interface SysOssConfigVoToSysOssConfigMapper extends BaseMapper<SysOssConfigVo, SysOssConfig> {
}
