package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysOssBoToSysOssMapper;
import org.niko.system.domain.vo.SysOssVo;
import org.niko.system.domain.vo.SysOssVoToSysOssMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysOssVoToSysOssMapper.class,SysOssBoToSysOssMapper.class},
    imports = {}
)
public interface SysOssToSysOssVoMapper extends BaseMapper<SysOss, SysOssVo> {
}
