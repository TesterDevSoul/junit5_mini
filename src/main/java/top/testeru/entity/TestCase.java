/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.entity;

public class TestCase {
    private Integer id;


    private String caseTitle;

    /**
     * 备注
     */
    private String remark;

    /**
     * 测试用例内容
     */
    private String caseData;


    /**
     * 测试用例路径
     */
    private String path;
    /**
     * 测试用例类型
     */
    private Integer type;


    @Override
    public String toString() {
        return "TestCase{" +
                "id=" + id +
                ", caseTitle='" + caseTitle + '\'' +
                ", remark='" + remark + '\'' +
                ", caseData='" + caseData + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCaseData() {
        return caseData;
    }

    public void setCaseData(String caseData) {
        this.caseData = caseData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
