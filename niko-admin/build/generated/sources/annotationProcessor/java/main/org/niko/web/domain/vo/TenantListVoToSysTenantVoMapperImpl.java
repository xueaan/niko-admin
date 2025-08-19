package org.niko.web.domain.vo;

import javax.annotation.processing.Generated;
import org.niko.system.domain.vo.SysTenantVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:47+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class TenantListVoToSysTenantVoMapperImpl implements TenantListVoToSysTenantVoMapper {

    @Override
    public SysTenantVo convert(TenantListVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysTenantVo sysTenantVo = new SysTenantVo();

        sysTenantVo.setTenantId( arg0.getTenantId() );
        sysTenantVo.setCompanyName( arg0.getCompanyName() );
        sysTenantVo.setDomain( arg0.getDomain() );

        return sysTenantVo;
    }

    @Override
    public SysTenantVo convert(TenantListVo arg0, SysTenantVo arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setTenantId( arg0.getTenantId() );
        arg1.setCompanyName( arg0.getCompanyName() );
        arg1.setDomain( arg0.getDomain() );

        return arg1;
    }
}
