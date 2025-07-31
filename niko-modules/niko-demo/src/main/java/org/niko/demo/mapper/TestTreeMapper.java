package org.niko.demo.mapper;

import org.niko.common.mybatis.annotation.DataColumn;
import org.niko.common.mybatis.annotation.DataPermission;
import org.niko.common.mybatis.core.mapper.BaseMapperPlus;
import org.niko.demo.domain.TestTree;
import org.niko.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTree, TestTreeVo> {

}
