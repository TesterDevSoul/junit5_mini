/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.num;

import top.testeru.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubTest extends BaseTest {
    @Test
    @DisplayName("减法异常的测试用例")
    @Order(4)
    public void subNumError() {


        result = calculator.subtract(100, 8);
        System.out.println(result);//0
        assertEquals(0,result,"100 - 8计算错误");


    }

}
