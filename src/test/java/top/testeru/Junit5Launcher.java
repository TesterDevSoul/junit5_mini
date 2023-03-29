/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru;

import top.testeru.entity.TestCase;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static top.testeru.BaseTest.logger;
import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class Junit5Launcher {
    public static void main(String[] args) throws IOException {
        //当前项目地址
        File file = new File(System.getProperty("user.dir"));
        List<TestCase> testCaseList = new ArrayList<>();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        //通过对包、类、方法维度的选择器，制定要执行的脚本
                        selectPackage("top.testeru.num")

                ).filters(
                        //通过对类名的通配符匹配，制定或者排除相关用例
                        includeClassNamePatterns(".*Test")
                ).build();

        try (LauncherSession session = LauncherFactory.openSession()) {
            TestPlan testPlan = session.getLauncher().discover(request);
            TestIdentifier jUnitJupiter = Optional.ofNullable(testPlan)
                    .get()
                    .getRoots()
                    .stream()
                    .filter(testIdentifier -> testIdentifier.getUniqueId().contains("junit-jupiter"))
                    .collect(Collectors.toList()).get(0);

            HashSet<TestIdentifier> testIdentifiers =
                    Optional.ofNullable(testPlan)
                            .map(tp -> tp.getDescendants(jUnitJupiter))
                            .orElseGet(Collections::emptySet)
                            .stream()
                            .collect(Collectors.toCollection(HashSet::new));
            testIdentifiers.forEach(testIdentifier -> {
                System.out.println(testIdentifier.getParentId().get());

                TestCase testCase = new TestCase();
                if(testIdentifier.getParentId().get().contains("class:")){//说明是一个测试方法
                    MethodSource methodSource = (MethodSource)testIdentifier.getSource().get();
                    String className = methodSource.getClassName();//包名.类名
                    String methodName = methodSource.getMethodName();//测试方法名
                    testCase.setCaseTitle(className + "#" + methodName);//测试用例标题
                    testCase.setPath(file.getPath());//测试用例路径
                    testCase.setType(1);//1代表的就是自动获取测试用例

                    testCaseList.add(testCase);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("listCase:{}",testCaseList);
        //testcaseList写入yaml文件
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        //实体类直接转为string
        String testCaseListStr = objectMapper.writeValueAsString(testCaseList);
        objectMapper.writeValue(new File("testcase.json"),testCaseList);

//        获取token
        TypeReference<HashMap<String,String>> typeReference = new TypeReference<HashMap<String,String>>(){};
        HashMap<String, String> login = objectMapper.readValue(new File("src/test/resources/user.json"), typeReference);

        Response response = RestAssured.given()
                .log().all()
                .contentType("application/json; charset=utf-8")
                .body(login)
                .when()
                .post("http://127.0.0.1:8085/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        String token = response.path("data.accessToken").toString();
        logger.info("登录的token:{}", token);
        //传入数据
        RestAssured.given()
                .log().all()
                .header("Authorization","Bearer "+token)
                .contentType("application/json; charset=utf-8")
                .body(testCaseListStr)
                .when()
                .post("http://127.0.0.1:8085/case")
                .then()
                .log().all();

    }
}