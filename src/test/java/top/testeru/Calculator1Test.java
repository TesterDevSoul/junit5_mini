package top.testeru;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 1. 创建计算器对象 -- new有参构造，传入参数值为：计算器
 * 2. log日志打印：开始进行加法计算
 * 3. 业务逻辑调用 获取结果值  int result        [2+2   4]   [4+3+8  15]   [100+0  ]  [-100+8  ]
 * 4. log日志打印计算结果：  计算结果为{}
 * 5. 断言 -- assertEquals()
 */
//@Test
//@BeforeAll
@DisplayName("计算器测试用例1111")
public class Calculator1Test extends BaseTest{


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
    public void sum(int num1, int num2, int re) {
        //3. 业务逻辑调用 获取结果值  int result
        result = calculator.sum(num1, num2);
        //5. 断言 -- assertEquals()
        //expected   4
        //actual     result
        //message   失败原因的解释说明
//        assertEquals(4,result,"2+2的计算结果失败");
        assertEquals(re,result,"2+2的计算结果失败");
    }
    public void subTest(int num1, int num2, int re) {

    }
    public void strTestStr(String str1, String str2,String re) {


    }
     public static Stream<Arguments> sum(){
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
    public void sum1() {
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
    public void sumError() {

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
    //-100+8
}

//运行顺序：
//@BeforeAll
//      @BeforeEach   @Test   @AfterEach
//      @BeforeEach   @Test   @AfterEach
//      @BeforeEach   @Test   @AfterEach
//@AfterAll