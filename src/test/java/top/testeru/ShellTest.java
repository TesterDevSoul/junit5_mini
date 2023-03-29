/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru;

import top.testeru.entity.ResultList;
import top.testeru.entity.ShellResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @Test  静态测试用例 在编译的时候就生成测试用例
 * @TestFactory 动态测试工厂 编译时没有测试用例，边执行边生成测试用例
 *
 * 动态测试没有BeforeEach AfterEach
 * beforeAll AfterAll是一样
 *
 */
public class ShellTest {
    ResultList resultList;

    @Test
    public void shellYamlTest(){
        ResultList shellYaml = getShellYaml();
        System.out.println(resultList);
    }

    private ResultList getShellYaml() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<ResultList> typeReference = new TypeReference<ResultList>() {
        };
        try {
            resultList = mapper.readValue(new File("src/test/resources/yaml/shell_test_result.yaml"), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    //动态测试工厂：返回值不是Collection，Stram,Iterator；是其他类型，则会报错：JUnitException
    @TestFactory
    Collection<DynamicTest> shellDynamic(){
        Collection<DynamicTest> shellDyList= new ArrayList<>();
        ResultList shellYaml = getShellYaml();//shell-yaml文件的解析
        List<ShellResult> resultList1 = shellYaml.getResultList();
        resultList1.forEach(
                shellResult -> {
                    //直接导入类，类.方法去引用 import org.junit.jupiter.api.DynamicTest;
//                    DynamicTest.dynamicTest()
                    //直接导入静态包  import static org.junit.jupiter.api.DynamicTest.dynamicTest;
                    DynamicTest dynamicTest1 =
                            dynamicTest(
                                shellResult.getCaseName(),
                                //测试的业务逻辑
                                () -> {
                                    System.out.println("result：" + shellResult.getResult());
                                    assertTrue(shellResult.getResult());
                                }
                            );
                    shellDyList.add(dynamicTest1);
                }
        );

        return shellDyList;
    }

}
