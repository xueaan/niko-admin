package org.niko.system.domain;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.bo.SysNoticeBoToSysNoticeMapper;
import org.niko.system.domain.vo.SysNoticeVo;
import org.niko.system.domain.vo.SysNoticeVoToSysNoticeMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysNoticeVoToSysNoticeMapper.class,SysNoticeBoToSysNoticeMapper.class},
    imports = {}
)
public interface SysNoticeToSysNoticeVoMapper extends BaseMapper<SysNotice, SysNoticeVo> {
}
