package org.niko.system.domain;

import javax.annotation.processing.Generated;
import org.niko.system.domain.vo.SysLogininforVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysLogininforToSysLogininforVoMapperImpl implements SysLogininforToSysLogininforVoMapper {

    @Override
    public SysLogininforVo convert(SysLogininfor arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysLogininforVo sysLogininforVo = new SysLogininforVo();

        sysLogininforVo.setInfoId( arg0.getInfoId() );
        sysLogininforVo.setTenantId( arg0.getTenantId() );
        sysLogininforVo.setUserName( arg0.getUserName() );
        sysLogininforVo.setClientKey( arg0.getClientKey() );
        sysLogininforVo.setDeviceType( arg0.getDeviceType() );
        sysLogininforVo.setStatus( arg0.getStatus() );
        sysLogininforVo.setIpaddr( arg0.getIpaddr() );
        sysLogininforVo.setLoginLocation( arg0.getLoginLocation() );
        sysLogininforVo.setBrowser( arg0.getBrowser() );
        sysLogininforVo.setOs( arg0.getOs() );
        sysLogininforVo.setMsg( arg0.getMsg() );
        sysLogininforVo.setLoginTime( arg0.getLoginTime() );

        return sysLogininforVo;
    }

    @Override
    public SysLogininforVo convert(SysLogininfor arg0, SysLogininforVo arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setInfoId( arg0.getInfoId() );
        arg1.setTenantId( arg0.getTenantId() );
        arg1.setUserName( arg0.getUserName() );
        arg1.setClientKey( arg0.getClientKey() );
        arg1.setDeviceType( arg0.getDeviceType() );
        arg1.setStatus( arg0.getStatus() );
        arg1.setIpaddr( arg0.getIpaddr() );
        arg1.setLoginLocation( arg0.getLoginLocation() );
        arg1.setBrowser( arg0.getBrowser() );
        arg1.setOs( arg0.getOs() );
        arg1.setMsg( arg0.getMsg() );
        arg1.setLoginTime( arg0.getLoginTime() );

        return arg1;
    }
}
