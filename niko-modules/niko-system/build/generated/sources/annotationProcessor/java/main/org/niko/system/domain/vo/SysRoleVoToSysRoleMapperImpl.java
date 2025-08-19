package org.niko.system.domain.vo;

import javax.annotation.processing.Generated;
import org.niko.system.domain.SysRole;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysRoleVoToSysRoleMapperImpl implements SysRoleVoToSysRoleMapper {

    @Override
    public SysRole convert(SysRoleVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysRole sysRole = new SysRole();

        sysRole.setCreateTime( arg0.getCreateTime() );
        sysRole.setRoleId( arg0.getRoleId() );
        sysRole.setRoleName( arg0.getRoleName() );
        sysRole.setRoleKey( arg0.getRoleKey() );
        sysRole.setRoleSort( arg0.getRoleSort() );
        sysRole.setDataScope( arg0.getDataScope() );
        sysRole.setMenuCheckStrictly( arg0.getMenuCheckStrictly() );
        sysRole.setDeptCheckStrictly( arg0.getDeptCheckStrictly() );
        sysRole.setStatus( arg0.getStatus() );
        sysRole.setRemark( arg0.getRemark() );

        return sysRole;
    }

    @Override
    public SysRole convert(SysRoleVo arg0, SysRole arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setCreateTime( arg0.getCreateTime() );
        arg1.setRoleId( arg0.getRoleId() );
        arg1.setRoleName( arg0.getRoleName() );
        arg1.setRoleKey( arg0.getRoleKey() );
        arg1.setRoleSort( arg0.getRoleSort() );
        arg1.setDataScope( arg0.getDataScope() );
        arg1.setMenuCheckStrictly( arg0.getMenuCheckStrictly() );
        arg1.setDeptCheckStrictly( arg0.getDeptCheckStrictly() );
        arg1.setStatus( arg0.getStatus() );
        arg1.setRemark( arg0.getRemark() );

        return arg1;
    }
}
