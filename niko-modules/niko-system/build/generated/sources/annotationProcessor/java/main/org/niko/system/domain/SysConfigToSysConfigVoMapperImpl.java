package org.niko.system.domain;

import javax.annotation.processing.Generated;
import org.niko.system.domain.vo.SysConfigVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysConfigToSysConfigVoMapperImpl implements SysConfigToSysConfigVoMapper {

    @Override
    public SysConfigVo convert(SysConfig arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysConfigVo sysConfigVo = new SysConfigVo();

        sysConfigVo.setConfigId( arg0.getConfigId() );
        sysConfigVo.setConfigName( arg0.getConfigName() );
        sysConfigVo.setConfigKey( arg0.getConfigKey() );
        sysConfigVo.setConfigValue( arg0.getConfigValue() );
        sysConfigVo.setConfigType( arg0.getConfigType() );
        sysConfigVo.setRemark( arg0.getRemark() );
        sysConfigVo.setCreateTime( arg0.getCreateTime() );

        return sysConfigVo;
    }

    @Override
    public SysConfigVo convert(SysConfig arg0, SysConfigVo arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setConfigId( arg0.getConfigId() );
        arg1.setConfigName( arg0.getConfigName() );
        arg1.setConfigKey( arg0.getConfigKey() );
        arg1.setConfigValue( arg0.getConfigValue() );
        arg1.setConfigType( arg0.getConfigType() );
        arg1.setRemark( arg0.getRemark() );
        arg1.setCreateTime( arg0.getCreateTime() );

        return arg1;
    }
}
