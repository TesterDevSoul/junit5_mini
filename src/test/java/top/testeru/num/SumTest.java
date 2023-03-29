/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.num;

import top.testeru.BaseTest;
import top.testeru.entity.AData;
import top.testeru.entity.Data;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SumTest extends BaseTest {

    //Test注解 是方法上的
    //一个测试类中可以有多个测试方法，即多个@Test注解
    //Test注解修饰的方法返回值类型是void
    //Test注解里面编写的内容是测试用例执行的具体内容及断言结果
//    @Test
    //{argumentsWithNames}
    @ParameterizedTest(name = "{0} + {1} = {2}")//这个代表是参数化  但是不代表提供参数源  Test\ParameterizedTest 注解二选一
    @MethodSource//代表的是参数的源
    @DisplayName("加法参数化的测试用例")
    @Order(9)
    public void sumNum(int num1, int num2, int re) {
        //3. 业务逻辑调用 获取结果值  int result
        result = calculator.sum(num1, num2);
        //5. 断言 -- assertEquals()
        //expected   4
        //actual     result
        //message   失败原因的解释说明
//        assertEquals(4,result,"2+2的计算结果失败");
        assertEquals(re,result,"2+2的计算结果失败");
    }

    public static Stream<Arguments> sumNum(){
        return Stream.of(
                Arguments.arguments(2,2,4),
                Arguments.arguments(4,5,9),
                Arguments.arguments(8,3,11)
        );
    }


    //2,2
    //4,5
    //8,3
    //4,3,8
    //2,6,8,9



    @Test
    @DisplayName("加法正向的测试用例")
    @Order(37)
    public void sumNum1() {
        //3. 业务逻辑调用 获取结果值  int result
        result = calculator.sum(4, 3, 8);

        //5. 断言 -- assertEquals()
        //expected  11
        //actual     result
        //message   失败原因的解释说明
        assertEquals(15,result,"加法的计算结果失败");
//        assertEquals(5,result,"2+2的计算结果失败");
    }


    //    100+0  ]
    @Test
    @DisplayName("加法异常的测试用例")
    @Order(74)
    public void sumNumError() {

        //3. 业务逻辑调用 获取结果值  int result
//        int result = calculator.sum(100, 0);
        /**
         * 异常断言：assertThrows
         * expectedType  抛出的异常类型
         * executable    异常业务流
         //         */
//        Exception nFException = assertThrows(
//                IllegalArgumentException.class,
//                () -> calculator.sum(100, 0));
        Exception nFException = assertThrowsExactly(NumberFormatException.class,
                () -> calculator.sum(100, 0));
        assertTrue(nFException.getMessage().contains("integer is 100"));


    }

    /**
     * yaml文件解析补充
     */
    //参数化的数据源在其他类中 引用格式为：包名.类名#数据源方法名

    @ParameterizedTest
    @MethodSource("top.testeru.source.SumYamlSource#sumNum")
    @DisplayName("加法正向/异常的测试用例")
    @Order(7)
    public void sumNumAllTest(AData aData) {
        List<Executable> sumAssertAll  = new ArrayList<>();
        System.out.println("输入参数："+aData);
//        Scanner scanner = new Scanner(System.in);

        if(aData.getMessage().contains("有效")){
            result = calculator.sum(aData.getA(),aData.getB());

            //5. 断言 -- assertEquals()
            //expected  11
            //actual     result
            //message   失败原因的解释说明
            sumAssertAll.add(() -> assertEquals(aData.getResult(),result,"加法的计算结果失败"));

        } else if (aData.getMessage().contains("无效")) {
            Exception nFException = assertThrowsExactly(IllegalArgumentException.class,
                    () -> calculator.sum(aData.getA(),aData.getB()));
            sumAssertAll.add(() -> assertTrue(nFException.getMessage().contains("请输入范围内的整数")));
            sumAssertAll.add(() -> assertEquals(aData.getResult(),result,"加法的无效计算结果失败"));


        }

        assertAll(sumAssertAll);

    }





    //yaml文件解析测试
    @Test
    public void yamlTest(){
        /**
         *
         * Factory
         * yaml YAMLFactory
         * json JsonFactory
         *
         */
        //工厂是 食品加工厂还是对应五金加工厂
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Data data;
        //食品加工厂中 加工牛奶的还是加工茶叶的 还是加工瓜子的
        TypeReference<Data> typeReference = new TypeReference<>(){};

        try {
            // readValue  相当于加工出来最终的结果 是茶叶还是纯奶 还是酸奶
            data = mapper.readValue(new File("src/test/resources/yaml/add.yml"), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
        List<AData> datas = data.getDatas();
//        System.out.println();


    }




}
