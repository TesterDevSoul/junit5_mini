/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BaseTest {
    public static final Logger logger = getLogger(lookup().lookupClass());
    //1. 创建计算器对象 -- new有参构造，传入参数值为：计算器
    public static Calculator calculator;
    public int result ;
    public String strResult;
    public String strCase;
    //AfterAll注解 static修饰 void返回值   与在代码中的前后顺序无关
    //在测试类里面运行一次，并且是在所有的方法之后运行一次
    //作用：apk卸载 app退出，测试用例结束，web端关闭浏览器操作。。。
    @AfterAll
    public static void afterAll(){
        calculator.close();
    }
    //BeforeAll注解  方法上 static修饰的
    //在测试类里面运行一次，并且是在所有的方法之前运行一次
    //作用：对象的声明 测试数据准备，log日志删除，apk安装，启动的某些参数的配置 AppiumDriver webdriver ChromeDriver
    @BeforeAll
    public static void beforeAll() {
        calculator = new Calculator("计算器");
    }


    //void返回值
    //BeforeEach注解 在每一个@Test注解修饰的方法之前运行一次；所以，当前测试类有多少个Test注解，BeforeEach注解修饰的方法就运行多少次
    //作用：测试用例中，测试方法需要初始化的内容及属性「app/web端进入固定页面，回退到固定页面；重启app；删除某些产生的测试数据」
    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        String displayName = testInfo.getDisplayName();
        System.out.println("testInfo获取："+displayName);
//        TestInfo
        //method            strCase
        // sum 支付           加法   数据库连接、注册会员
        // sub 营销           减法   新用户输入数据库
        // str 主业务线     字符串拼接  apk卸载


        Optional<Method> testMethod = testInfo.getTestMethod();
        //获取 Method 类里面的 getName() 方法的返回值  Optional<String>
        Optional<String> s = testMethod.map(Method::getName);
        System.out.println(s+"------");//sumNum
//        Optional<String> sum = s.filter(str -> str.contains("sum"));//Optional<sumNum>  Optional<null>
//        strCase = sum.isPresent() ? "加法" : "其他";
        if (s.filter(str -> str.contains("sum")).isPresent()) {
            strCase = "加法";

        }else if (s.filter(str -> str.contains("sub")).isPresent()){
            strCase = "减法";

        } else if (s.filter(str -> str.contains("str")).isPresent()) {
            strCase = "字符串拼接";

        }else {
            strCase = "计算器的统称";
        }
        //2. log日志打印：开始进行加法计算
//        logger.info("开始进行加法计算");
        logger.info("开始进行 {} 计算",strCase);

    }

    //@BeforeEach+@AfterEach可以同时修饰一个方法
    //@BeforeAll+@AfterAll可以同时修饰一个方法
    //@BeforeAll+@AfterEach 不可以❌
    //@BeforeEach+@AfterAll 不可以❌
    @BeforeEach
    @AfterEach
    public void write(){
        System.out.println("before and after");
    }


    //void返回值
    //AfterEach注解 在每一个@Test注解修饰的方法之后运行一次；
    // 所以，当前测试类有多少个Test注解，AfterEach注解修饰的方法就运行多少次
    //无论@Test注解修饰的测试方法是否断言成功，@AfterEach方法的内容都去运行
    //作用：测试用例中，测试方法需要初始化的内容及属性「app/web端进入固定页面，回退到固定页面；重启app；删除某些产生的测试数据」
    @AfterEach
    public void afterEach(TestInfo testInfo){
        Optional<String> optional = testInfo
                .getTestMethod()
                .map(Method::getName)//获取方法名
                .filter(str -> str.contains("Num"));
        //str  Optional<null>


//        testInfo.getDisplayName().contains("").

        String reS = optional.isPresent() ? String.valueOf(result) : strResult ;



        //4. log日志打印计算结果：  计算结果为{}
//        logger.info("计算结果为{}", result);
        logger.info("计算结果为{}", reS);

    }
}
