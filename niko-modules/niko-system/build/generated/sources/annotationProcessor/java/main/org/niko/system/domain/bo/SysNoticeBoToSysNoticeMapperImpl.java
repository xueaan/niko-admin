package org.niko.system.domain.bo;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.niko.system.domain.SysNotice;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysNoticeBoToSysNoticeMapperImpl implements SysNoticeBoToSysNoticeMapper {

    @Override
    public SysNotice convert(SysNoticeBo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysNotice sysNotice = new SysNotice();

        sysNotice.setSearchValue( arg0.getSearchValue() );
        sysNotice.setCreateDept( arg0.getCreateDept() );
        sysNotice.setCreateBy( arg0.getCreateBy() );
        sysNotice.setCreateTime( arg0.getCreateTime() );
        sysNotice.setUpdateBy( arg0.getUpdateBy() );
        sysNotice.setUpdateTime( arg0.getUpdateTime() );
        Map<String, Object> map = arg0.getParams();
        if ( map != null ) {
            sysNotice.setParams( new LinkedHashMap<String, Object>( map ) );
        }
        sysNotice.setNoticeId( arg0.getNoticeId() );
        sysNotice.setNoticeTitle( arg0.getNoticeTitle() );
        sysNotice.setNoticeType( arg0.getNoticeType() );
        sysNotice.setNoticeContent( arg0.getNoticeContent() );
        sysNotice.setStatus( arg0.getStatus() );
        sysNotice.setRemark( arg0.getRemark() );

        return sysNotice;
    }

    @Override
    public SysNotice convert(SysNoticeBo arg0, SysNotice arg1) {
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
        arg1.setNoticeId( arg0.getNoticeId() );
        arg1.setNoticeTitle( arg0.getNoticeTitle() );
        arg1.setNoticeType( arg0.getNoticeType() );
        arg1.setNoticeContent( arg0.getNoticeContent() );
        arg1.setStatus( arg0.getStatus() );
        arg1.setRemark( arg0.getRemark() );

        return arg1;
    }
}
