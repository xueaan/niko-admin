package org.niko.system.domain.bo;

import javax.annotation.processing.Generated;
import org.niko.system.domain.SysLogininfor;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysLogininforBoToSysLogininforMapperImpl implements SysLogininforBoToSysLogininforMapper {

    @Override
    public SysLogininfor convert(SysLogininforBo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysLogininfor sysLogininfor = new SysLogininfor();

        sysLogininfor.setInfoId( arg0.getInfoId() );
        sysLogininfor.setTenantId( arg0.getTenantId() );
        sysLogininfor.setUserName( arg0.getUserName() );
        sysLogininfor.setClientKey( arg0.getClientKey() );
        sysLogininfor.setDeviceType( arg0.getDeviceType() );
        sysLogininfor.setStatus( arg0.getStatus() );
        sysLogininfor.setIpaddr( arg0.getIpaddr() );
        sysLogininfor.setLoginLocation( arg0.getLoginLocation() );
        sysLogininfor.setBrowser( arg0.getBrowser() );
        sysLogininfor.setOs( arg0.getOs() );
        sysLogininfor.setMsg( arg0.getMsg() );
        sysLogininfor.setLoginTime( arg0.getLoginTime() );

        return sysLogininfor;
    }

    @Override
    public SysLogininfor convert(SysLogininforBo arg0, SysLogininfor arg1) {
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
