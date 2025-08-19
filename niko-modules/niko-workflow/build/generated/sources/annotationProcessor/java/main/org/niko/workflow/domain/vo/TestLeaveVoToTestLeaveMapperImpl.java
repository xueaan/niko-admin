package org.niko.workflow.domain.vo;

import javax.annotation.processing.Generated;
import org.niko.workflow.domain.TestLeave;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T21:58:41+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 17.0.15 (BellSoft)"
)
@Component
public class TestLeaveVoToTestLeaveMapperImpl implements TestLeaveVoToTestLeaveMapper {

    @Override
    public TestLeave convert(TestLeaveVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        TestLeave testLeave = new TestLeave();

        testLeave.setId( arg0.getId() );
        testLeave.setLeaveType( arg0.getLeaveType() );
        testLeave.setStartDate( arg0.getStartDate() );
        testLeave.setEndDate( arg0.getEndDate() );
        testLeave.setLeaveDays( arg0.getLeaveDays() );
        testLeave.setRemark( arg0.getRemark() );
        testLeave.setStatus( arg0.getStatus() );

        return testLeave;
    }

    @Override
    public TestLeave convert(TestLeaveVo arg0, TestLeave arg1) {
        if ( arg0 == null ) {
            return arg1;
        }

        arg1.setId( arg0.getId() );
        arg1.setLeaveType( arg0.getLeaveType() );
        arg1.setStartDate( arg0.getStartDate() );
        arg1.setEndDate( arg0.getEndDate() );
        arg1.setLeaveDays( arg0.getLeaveDays() );
        arg1.setRemark( arg0.getRemark() );
        arg1.setStatus( arg0.getStatus() );

        return arg1;
    }
}
