package org.niko.system.domain.bo;

import javax.annotation.processing.Generated;
import org.niko.system.domain.SysOperLog;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysOperLogBoToSysOperLogMapperImpl implements SysOperLogBoToSysOperLogMapper {

    @Override
    public SysOperLog convert(SysOperLogBo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysOperLog sysOperLog = new SysOperLog();

        sysOperLog.setOperId( arg0.getOperId() );
        sysOperLog.setTenantId( arg0.getTenantId() );
        sysOperLog.setTitle( arg0.getTitle() );
        sysOperLog.setBusinessType( arg0.getBusinessType() );
        sysOperLog.setMethod( arg0.getMethod() );
        sysOperLog.setRequestMethod( arg0.getRequestMethod() );
        sysOperLog.setOperatorType( arg0.getOperatorType() );
        sysOperLog.setOperName( arg0.getOperName() );
        sysOperLog.setDeptName( arg0.getDeptName() );
        sysOperLog.setOperUrl( arg0.getOperUrl() );
        sysOperLog.setOperIp( arg0.getOperIp() );
        sysOperLog.setOperLocation( arg0.getOperLocation() );
        sysOperLog.setOperParam( arg0.getOperParam() );
        sysOperLog.setJsonResult( arg0.getJsonResult() );
        sysOperLog.setStatus( arg0.getStatus() );
        sysOperLog.setErrorMsg( arg0.getErrorMsg() );
        sysOperLog.setOperTime( arg0.getOperTime() );
        sysOperLog.setCostTime( arg0.getCostTime() );

        return sysOperLog;
    }

    @Override
    public SysOperLog convert(SysOperLogBo arg0, SysOperLog arg1) {
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
