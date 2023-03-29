/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.entity;

import java.util.ArrayList;
import java.util.List;

public class ResultList {
    private List<ShellResult> resultList ;

    public ResultList(List<ShellResult> resultList) {
        this.resultList = resultList;
    }

    public ResultList() {
    }

    @Override
    public String toString() {
        return "Shell{" +
                "resultList=" + resultList +
                '}';
    }

    public List<ShellResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<ShellResult> resultList) {
        this.resultList = resultList;
    }
}
