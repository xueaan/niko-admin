package org.niko.system.domain.vo;

import java.util.List;
import javax.annotation.processing.Generated;
import org.niko.system.domain.SysMenu;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:41+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysMenuVoToSysMenuMapperImpl implements SysMenuVoToSysMenuMapper {

    @Override
    public SysMenu convert(SysMenuVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysMenu sysMenu = new SysMenu();

        sysMenu.setCreateDept( arg0.getCreateDept() );
        sysMenu.setCreateTime( arg0.getCreateTime() );
        sysMenu.setMenuId( arg0.getMenuId() );
        sysMenu.setParentId( arg0.getParentId() );
        sysMenu.setMenuName( arg0.getMenuName() );
        sysMenu.setOrderNum( arg0.getOrderNum() );
        sysMenu.setPath( arg0.getPath() );
        sysMenu.setComponent( arg0.getComponent() );
        sysMenu.setQueryParam( arg0.getQueryParam() );
        sysMenu.setIsFrame( arg0.getIsFrame() );
        sysMenu.setIsCache( arg0.getIsCache() );
        sysMenu.setMenuType( arg0.getMenuType() );
        sysMenu.setVisible( arg0.getVisible() );
        sysMenu.setStatus( arg0.getStatus() );
        sysMenu.setPerms( arg0.getPerms() );
        sysMenu.setIcon( arg0.getIcon() );
        sysMenu.setRemark( arg0.getRemark() );
        sysMenu.setChildren( convert( arg0.getChildren() ) );

        return sysMenu;
    }

    @Override
    public SysMenu convert(SysMenuVo arg0, SysMenu arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setCreateDept( arg0.getCreateDept() );
        arg1.setCreateTime( arg0.getCreateTime() );
        arg1.setMenuId( arg0.getMenuId() );
        arg1.setParentId( arg0.getParentId() );
        arg1.setMenuName( arg0.getMenuName() );
        arg1.setOrderNum( arg0.getOrderNum() );
        arg1.setPath( arg0.getPath() );
        arg1.setComponent( arg0.getComponent() );
        arg1.setQueryParam( arg0.getQueryParam() );
        arg1.setIsFrame( arg0.getIsFrame() );
        arg1.setIsCache( arg0.getIsCache() );
        arg1.setMenuType( arg0.getMenuType() );
        arg1.setVisible( arg0.getVisible() );
        arg1.setStatus( arg0.getStatus() );
        arg1.setPerms( arg0.getPerms() );
        arg1.setIcon( arg0.getIcon() );
        arg1.setRemark( arg0.getRemark() );
        if ( arg1.getChildren() != null ) {
            List<SysMenu> list = convert( arg0.getChildren() );
            if ( list != null ) {
                arg1.getChildren().clear();
                arg1.getChildren().addAll( list );
            }
            else {
                arg1.setChildren( null );
            }
        }
        else {
            List<SysMenu> list = convert( arg0.getChildren() );
            if ( list != null ) {
                arg1.setChildren( list );
            }
        }

        return arg1;
    }
}
