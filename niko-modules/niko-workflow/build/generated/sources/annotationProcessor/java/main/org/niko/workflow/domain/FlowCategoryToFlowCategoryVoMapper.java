package org.niko.workflow.domain;

import io.github.linpeilie.AutoMapperConfig__625;
import io.github.linpeilie.BaseMapper;
import org.mapstruct.Mapper;
import org.niko.workflow.domain.bo.FlowCategoryBoToFlowCategoryMapper;
import org.niko.workflow.domain.vo.FlowCategoryVo;
import org.niko.workflow.domain.vo.FlowCategoryVoToFlowCategoryMapper;

@Mapper(
    config = AutoMapperConfig__625.class,
    uses = {FlowCategoryVoToFlowCategoryMapper.class,FlowCategoryBoToFlowCategoryMapper.class},
    imports = {}
)
public interface FlowCategoryToFlowCategoryVoMapper extends BaseMapper<FlowCategory, FlowCategoryVo> {
}
