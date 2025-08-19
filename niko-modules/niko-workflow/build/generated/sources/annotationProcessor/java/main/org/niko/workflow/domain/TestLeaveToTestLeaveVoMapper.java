package org.niko.workflow.domain;

import io.github.linpeilie.AutoMapperConfig__625;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.workflow.domain.bo.TestLeaveBoToTestLeaveMapper;
import org.niko.workflow.domain.vo.TestLeaveVo;
import org.niko.workflow.domain.vo.TestLeaveVoToTestLeaveMapper;

@Mapper(
    config = AutoMapperConfig__625.class,
    uses = {TestLeaveVoToTestLeaveMapper.class,TestLeaveBoToTestLeaveMapper.class},
    imports = {}
)
public interface TestLeaveToTestLeaveVoMapper extends BaseMapper<TestLeave, TestLeaveVo> {
}
