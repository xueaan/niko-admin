package org.niko.workflow.domain.bo;

import io.github.linpeilie.AutoMapperConfig__625;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.workflow.domain.TestLeave;

@Mapper(
    config = AutoMapperConfig__625.class,
    uses = {},
    imports = {}
)
public interface TestLeaveBoToTestLeaveMapper extends BaseMapper<TestLeaveBo, TestLeave> {
}
