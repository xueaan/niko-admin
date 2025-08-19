package org.niko.workflow.domain.vo;

import io.github.linpeilie.AutoMapperConfig__625;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.workflow.domain.FlowCategory;
import org.niko.workflow.domain.FlowCategoryToFlowCategoryVoMapper;

@Mapper(
    config = AutoMapperConfig__625.class,
    uses = {FlowCategoryToFlowCategoryVoMapper.class},
    imports = {}
)
public interface FlowCategoryVoToFlowCategoryMapper extends BaseMapper<FlowCategoryVo, FlowCategory> {
}
