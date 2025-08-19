package org.niko.system.domain;

import javax.annotation.processing.Generated;
import org.niko.system.domain.vo.SysOperLogVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysOperLogToSysOperLogVoMapperImpl implements SysOperLogToSysOperLogVoMapper {

    @Override
    public SysOperLogVo convert(SysOperLog arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysOperLogVo sysOperLogVo = new SysOperLogVo();

        sysOperLogVo.setOperId( arg0.getOperId() );
        sysOperLogVo.setTenantId( arg0.getTenantId() );
        sysOperLogVo.setTitle( arg0.getTitle() );
        sysOperLogVo.setBusinessType( arg0.getBusinessType() );
        sysOperLogVo.setMethod( arg0.getMethod() );
        sysOperLogVo.setRequestMethod( arg0.getRequestMethod() );
        sysOperLogVo.setOperatorType( arg0.getOperatorType() );
        sysOperLogVo.setOperName( arg0.getOperName() );
        sysOperLogVo.setDeptName( arg0.getDeptName() );
        sysOperLogVo.setOperUrl( arg0.getOperUrl() );
        sysOperLogVo.setOperIp( arg0.getOperIp() );
        sysOperLogVo.setOperLocation( arg0.getOperLocation() );
        sysOperLogVo.setOperParam( arg0.getOperParam() );
        sysOperLogVo.setJsonResult( arg0.getJsonResult() );
        sysOperLogVo.setStatus( arg0.getStatus() );
        sysOperLogVo.setErrorMsg( arg0.getErrorMsg() );
        sysOperLogVo.setOperTime( arg0.getOperTime() );
        sysOperLogVo.setCostTime( arg0.getCostTime() );

        return sysOperLogVo;
    }

    @Override
    public SysOperLogVo convert(SysOperLog arg0, SysOperLogVo arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setOperId( arg0.getOperId() );
        arg1.setTenantId( arg0.getTenantId() );
        arg1.setTitle( arg0.getTitle() );
        arg1.setBusinessType( arg0.getBusinessType() );
        arg1.setMethod( arg0.getMethod() );
        arg1.setRequestMethod( arg0.getRequestMethod() );
        arg1.setOperatorType( arg0.getOperatorType() );
        arg1.setOperName( arg0.getOperName() );
        arg1.setDeptName( arg0.getDeptName() );
        arg1.setOperUrl( arg0.getOperUrl() );
        arg1.setOperIp( arg0.getOperIp() );
        arg1.setOperLocation( arg0.getOperLocation() );
        arg1.setOperParam( arg0.getOperParam() );
        arg1.setJsonResult( arg0.getJsonResult() );
        arg1.setStatus( arg0.getStatus() );
        arg1.setErrorMsg( arg0.getErrorMsg() );
        arg1.setOperTime( arg0.getOperTime() );
        arg1.setCostTime( arg0.getCostTime() );

        return arg1;
    }
}
