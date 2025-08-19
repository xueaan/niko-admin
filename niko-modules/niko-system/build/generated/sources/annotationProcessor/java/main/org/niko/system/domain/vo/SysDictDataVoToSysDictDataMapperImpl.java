package org.niko.system.domain.vo;

import javax.annotation.processing.Generated;
import org.niko.system.domain.SysDictData;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:42+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class SysDictDataVoToSysDictDataMapperImpl implements SysDictDataVoToSysDictDataMapper {

    @Override
    public SysDictData convert(SysDictDataVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysDictData sysDictData = new SysDictData();

        sysDictData.setCreateTime( arg0.getCreateTime() );
        sysDictData.setDictCode( arg0.getDictCode() );
        sysDictData.setDictSort( arg0.getDictSort() );
        sysDictData.setDictLabel( arg0.getDictLabel() );
        sysDictData.setDictValue( arg0.getDictValue() );
        sysDictData.setDictType( arg0.getDictType() );
        sysDictData.setCssClass( arg0.getCssClass() );
        sysDictData.setListClass( arg0.getListClass() );
        sysDictData.setIsDefault( arg0.getIsDefault() );
        sysDictData.setRemark( arg0.getRemark() );

        return sysDictData;
    }

    @Override
    public SysDictData convert(SysDictDataVo arg0, SysDictData arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setCreateTime( arg0.getCreateTime() );
        arg1.setDictCode( arg0.getDictCode() );
        arg1.setDictSort( arg0.getDictSort() );
        arg1.setDictLabel( arg0.getDictLabel() );
        arg1.setDictValue( arg0.getDictValue() );
        arg1.setDictType( arg0.getDictType() );
        arg1.setCssClass( arg0.getCssClass() );
        arg1.setListClass( arg0.getListClass() );
        arg1.setIsDefault( arg0.getIsDefault() );
        arg1.setRemark( arg0.getRemark() );

        return arg1;
    }
}
