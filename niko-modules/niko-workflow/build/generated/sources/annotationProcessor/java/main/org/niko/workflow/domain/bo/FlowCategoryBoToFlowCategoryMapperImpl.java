package org.niko.workflow.domain.bo;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.niko.workflow.domain.FlowCategory;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:41+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class FlowCategoryBoToFlowCategoryMapperImpl implements FlowCategoryBoToFlowCategoryMapper {

    @Override
    public FlowCategory convert(FlowCategoryBo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        FlowCategory flowCategory = new FlowCategory();

        flowCategory.setSearchValue( arg0.getSearchValue() );
        flowCategory.setCreateDept( arg0.getCreateDept() );
        flowCategory.setCreateBy( arg0.getCreateBy() );
        flowCategory.setCreateTime( arg0.getCreateTime() );
        flowCategory.setUpdateBy( arg0.getUpdateBy() );
        flowCategory.setUpdateTime( arg0.getUpdateTime() );
        Map<String, Object> map = arg0.getParams();
        if ( map != null ) {
            flowCategory.setParams( new LinkedHashMap<String, Object>( map ) );
        }
        flowCategory.setCategoryId( arg0.getCategoryId() );
        flowCategory.setParentId( arg0.getParentId() );
        flowCategory.setCategoryName( arg0.getCategoryName() );
        flowCategory.setOrderNum( arg0.getOrderNum() );

        return flowCategory;
    }

    @Override
    public FlowCategory convert(FlowCategoryBo arg0, FlowCategory arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setSearchValue( arg0.getSearchValue() );
        arg1.setCreateDept( arg0.getCreateDept() );
        arg1.setCreateBy( arg0.getCreateBy() );
        arg1.setCreateTime( arg0.getCreateTime() );
        arg1.setUpdateBy( arg0.getUpdateBy() );
        arg1.setUpdateTime( arg0.getUpdateTime() );
        if ( arg1.getParams() != null ) {
            Map<String, Object> map = arg0.getParams();
            if ( map != null ) {
                arg1.getParams().clear();
                arg1.getParams().putAll( map );
            }
            else {
                arg1.setParams( null );
            }
        }
        else {
            Map<String, Object> map = arg0.getParams();
            if ( map != null ) {
                arg1.setParams( new LinkedHashMap<String, Object>( map ) );
            }
        }
        arg1.setCategoryId( arg0.getCategoryId() );
        arg1.setParentId( arg0.getParentId() );
        arg1.setCategoryName( arg0.getCategoryName() );
        arg1.setOrderNum( arg0.getOrderNum() );

        return arg1;
    }
}
