package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysOssConfigBoToSysOssConfigMapper;
import org.niko.system.domain.vo.SysOssConfigVo;
import org.niko.system.domain.vo.SysOssConfigVoToSysOssConfigMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOssConfigBoToSysOssConfigMapper.class,SysOssConfigVoToSysOssConfigMapper.class},
    imports = {}
)
public interface SysOssConfigToSysOssConfigVoMapper extends BaseMapper<SysOssConfig, SysOssConfigVo> {
}
