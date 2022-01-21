package apps.commons.util.enums;

/**
 * @author YJH
 * @date 2019/12/6 22:37
 */
public enum Order {
    ASC("asc"), DESC("desc");

    private String des;

    Order(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
