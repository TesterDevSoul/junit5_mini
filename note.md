## 报错

@BeforeAll注解需要static修饰
```

org.junit.platform.commons.JUnitException: 

@BeforeAll method 'public void com.ceshiren.CalculatorTest.beforeAll()'
must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).

```
- @AfterAll

```
java: 无法从静态上下文中引用非静态 变量 calculator
```
## 拆分用例类
- 1.`BaseTest` 类只有 `BeforeAll`,`BeforeEach`,`AfterEach`,`AfterAll`
    - 执行顺序：
        - `BeforeAll`
            - `BeforeEach` --> `Test 1` --> `AfterEach`
            - `BeforeEach` --> `Test 2` --> `AfterEach`
            - ...
        - `AfterAll`
- 2.加法、减法、字符串拼接为各自测试类
    - 加法：`SumTest`
    - 减法：`SubTest`
    - 字符拼接：`StrTest`

- 3.最终打印所有运算结果`result`：`TestInfo`{`BeforeEach`/`AfterEach`都可以获取到}
    - 算数运算的打印`int`
    - 字符的打印`str`

## 编写抛异常用例
- 4.使用`JUnit5`自带异常断言
    - 异常的`class`类型，不是异常的描述
    - `assertThrows`：抛出的异常类为指定异常与其父类都可。
    - `assertThrowsExactly`：只能是完全一样的异常类。


## 自定义显示名
- 5.`DisplayName`
    - 是**方法**「`ElementType.METHOD`」和**类**「`ElementType.TYPE`」上的注解；
    - 对应`value`为显示的名称
        - 可以是空格、特殊符号、表情符号  {录播课有介绍}
## 测试方法排序
- 6.使用`order`注解对测试方法排序
    - `MethodOrderer`选择排序规则
    - 2种配置方式
        - `JUnit5`配置文件：`junit-platform.properties`
        -  测试类上直接添加注解：`@TestMethodOrder`
        - 优先级：配置文件声明 **<** 类上声明排序规则
## 测试方法参数化
- 7.使用参数化进行多参数运算：`ParameterizedTest`+`*Source`「缺一不可」
    - `ParameterizedTest`
        - 不要在`@ParameterizedTest`注解的方法上添加`Test`注解「参数化注解就已经包含了test」
        - `ARGUMENTS_WITH_NAMES_PLACEHOLDER`
            - `key=value` 的map结构；`key`为方法参数名，`value`为参数值。eg:a=11,b=11,re=22
        - `ARGUMENTS_PLACEHOLDER`
            - `list`结构；直接为`value`的`list`集合。eg:11,11,22
    - `MethodSource`：提供参数源的注解
        - 参数方法与测试方法同名，则直接默认
        - 参数方法与测试方法名不同，则需要在括号内指定参数方法名

## suite套件管理测试集
- 8.使用`suite`套件运行测试集
    - `SelectClasses`
        - 选择多个测试类为测试集
    - `SuiteDisplayName`
        - 自定义对应测试集的显示名

## 标签注解
- Tags
- Tag
- 注意
    - 一个测试方法上共存：一个 Tags + Tag
    - 一个测试方法上可以有多个 Tag
    - 一个测试方法上只能有一个 Tags

## mvn
mvn clean 会删除对应的项目下target文件夹


## maven-allure
生成allure报告并下载版本到当前项目下：
```shell
mvn clean test allure:report
```

打开allure报告
```shell
mvn allure:serve
```


- 不同项目用不同allure版本不受影响