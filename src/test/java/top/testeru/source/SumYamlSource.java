/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.source;

import top.testeru.entity.AData;
import top.testeru.entity.Data;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class SumYamlSource {
    //引用的数据源一定要是public修饰
    public static Stream<AData> sumNum(){
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
        return data.getDatas().stream();
    }

}
