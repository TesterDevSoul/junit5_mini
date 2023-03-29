/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.str;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import top.testeru.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrTest extends BaseTest {
    @Test
    @DisplayName("字符串拼接的测试用例")
    @Order(9)
    public void str1() {

        strResult = calculator.concatStr("Hello", "JUnit5");
        System.out.println(strResult);//Hello JUnit5
        assertEquals("Hello JUnit5",strResult,"字符串拼接错误");


    }
}
