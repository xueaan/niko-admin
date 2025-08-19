package org.niko.workflow.domain.vo;

import javax.annotation.processing.Generated;
import org.niko.workflow.domain.FlowCategory;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:40+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class FlowCategoryVoToFlowCategoryMapperImpl implements FlowCategoryVoToFlowCategoryMapper {

    @Override
    public FlowCategory convert(FlowCategoryVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        FlowCategory flowCategory = new FlowCategory();

        flowCategory.setCreateTime( arg0.getCreateTime() );
        flowCategory.setCategoryId( arg0.getCategoryId() );
        flowCategory.setParentId( arg0.getParentId() );
        flowCategory.setAncestors( arg0.getAncestors() );
        flowCategory.setCategoryName( arg0.getCategoryName() );
        flowCategory.setOrderNum( arg0.getOrderNum() );

        return flowCategory;
    }

    @Override
    public FlowCategory convert(FlowCategoryVo arg0, FlowCategory arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setCreateTime( arg0.getCreateTime() );
        arg1.setCategoryId( arg0.getCategoryId() );
        arg1.setParentId( arg0.getParentId() );
        arg1.setAncestors( arg0.getAncestors() );
        arg1.setCategoryName( arg0.getCategoryName() );
        arg1.setOrderNum( arg0.getOrderNum() );

        return arg1;
    }
}
