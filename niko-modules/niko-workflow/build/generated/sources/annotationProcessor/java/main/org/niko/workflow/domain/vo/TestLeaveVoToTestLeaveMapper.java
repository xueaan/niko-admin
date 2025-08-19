package org.niko.workflow.domain.vo;

import io.github.linpeilie.AutoMapperConfig__625;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.workflow.domain.TestLeave;
import org.niko.workflow.domain.TestLeaveToTestLeaveVoMapper;

@Mapper(
    config = AutoMapperConfig__625.class,
    uses = {TestLeaveToTestLeaveVoMapper.class},
    imports = {}
)
public interface TestLeaveVoToTestLeaveMapper extends BaseMapper<TestLeaveVo, TestLeave> {
}
