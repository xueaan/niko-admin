package org.niko.system.domain.bo;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.niko.system.domain.SysConfig;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysConfigBoToSysConfigMapperImpl implements SysConfigBoToSysConfigMapper {

    @Override
    public SysConfig convert(SysConfigBo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysConfig sysConfig = new SysConfig();

        sysConfig.setSearchValue( arg0.getSearchValue() );
        sysConfig.setCreateDept( arg0.getCreateDept() );
        sysConfig.setCreateBy( arg0.getCreateBy() );
        sysConfig.setCreateTime( arg0.getCreateTime() );
        sysConfig.setUpdateBy( arg0.getUpdateBy() );
        sysConfig.setUpdateTime( arg0.getUpdateTime() );
        Map<String, Object> map = arg0.getParams();
        if ( map != null ) {
            sysConfig.setParams( new LinkedHashMap<String, Object>( map ) );
        }
        sysConfig.setConfigId( arg0.getConfigId() );
        sysConfig.setConfigName( arg0.getConfigName() );
        sysConfig.setConfigKey( arg0.getConfigKey() );
        sysConfig.setConfigValue( arg0.getConfigValue() );
        sysConfig.setConfigType( arg0.getConfigType() );
        sysConfig.setRemark( arg0.getRemark() );

        return sysConfig;
    }

    @Override
    public SysConfig convert(SysConfigBo arg0, SysConfig arg1) {
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
        arg1.setConfigId( arg0.getConfigId() );
        arg1.setConfigName( arg0.getConfigName() );
        arg1.setConfigKey( arg0.getConfigKey() );
        arg1.setConfigValue( arg0.getConfigValue() );
        arg1.setConfigType( arg0.getConfigType() );
        arg1.setRemark( arg0.getRemark() );

        return arg1;
    }
}
