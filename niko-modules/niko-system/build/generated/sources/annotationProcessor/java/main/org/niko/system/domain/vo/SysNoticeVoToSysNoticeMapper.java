package org.niko.system.domain.vo;

import io.github.linpeilie.AutoMapperConfig__626;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.system.domain.SysNotice;
import org.niko.system.domain.SysNoticeToSysNoticeVoMapper;

@Mapper(
    config = AutoMapperConfig__626.class,
    uses = {SysNoticeToSysNoticeVoMapper.class},
    imports = {}
)
public interface SysNoticeVoToSysNoticeMapper extends BaseMapper<SysNoticeVo, SysNotice> {
}
