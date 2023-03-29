package top.testeru.entity;

import java.util.List;

public class Data {
    private List<AData> datas;

    @Override
    public String toString() {
        return "Data{" +
                "datas=" + datas +
                '}';
    }

    public List<AData> getDatas() {
        return datas;
    }

    public void setDatas(List<AData> datas) {
        this.datas = datas;
    }
}
