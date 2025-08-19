package org.niko.system.domain;

import javax.annotation.processing.Generated;
import org.niko.system.domain.vo.SysOssConfigVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysOssConfigToSysOssConfigVoMapperImpl implements SysOssConfigToSysOssConfigVoMapper {

    @Override
    public SysOssConfigVo convert(SysOssConfig arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysOssConfigVo sysOssConfigVo = new SysOssConfigVo();

        sysOssConfigVo.setOssConfigId( arg0.getOssConfigId() );
        sysOssConfigVo.setConfigKey( arg0.getConfigKey() );
        sysOssConfigVo.setAccessKey( arg0.getAccessKey() );
        sysOssConfigVo.setSecretKey( arg0.getSecretKey() );
        sysOssConfigVo.setBucketName( arg0.getBucketName() );
        sysOssConfigVo.setPrefix( arg0.getPrefix() );
        sysOssConfigVo.setEndpoint( arg0.getEndpoint() );
        sysOssConfigVo.setDomain( arg0.getDomain() );
        sysOssConfigVo.setIsHttps( arg0.getIsHttps() );
        sysOssConfigVo.setRegion( arg0.getRegion() );
        sysOssConfigVo.setStatus( arg0.getStatus() );
        sysOssConfigVo.setExt1( arg0.getExt1() );
        sysOssConfigVo.setRemark( arg0.getRemark() );
        sysOssConfigVo.setAccessPolicy( arg0.getAccessPolicy() );

        return sysOssConfigVo;
    }

    @Override
    public SysOssConfigVo convert(SysOssConfig arg0, SysOssConfigVo arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setOssConfigId( arg0.getOssConfigId() );
        arg1.setConfigKey( arg0.getConfigKey() );
        arg1.setAccessKey( arg0.getAccessKey() );
        arg1.setSecretKey( arg0.getSecretKey() );
        arg1.setBucketName( arg0.getBucketName() );
        arg1.setPrefix( arg0.getPrefix() );
        arg1.setEndpoint( arg0.getEndpoint() );
        arg1.setDomain( arg0.getDomain() );
        arg1.setIsHttps( arg0.getIsHttps() );
        arg1.setRegion( arg0.getRegion() );
        arg1.setStatus( arg0.getStatus() );
        arg1.setExt1( arg0.getExt1() );
        arg1.setRemark( arg0.getRemark() );
        arg1.setAccessPolicy( arg0.getAccessPolicy() );

        return arg1;
    }
}
