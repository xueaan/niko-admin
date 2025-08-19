package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysOss;
import org.niko.system.domain.SysOssToSysOssVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOssToSysOssVoMapper.class},
    imports = {}
)
public interface SysOssVoToSysOssMapper extends BaseMapper<SysOssVo, SysOss> {
}
