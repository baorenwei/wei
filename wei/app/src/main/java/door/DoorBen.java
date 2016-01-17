package door;

/**
 * Created by Administrator on 2016/1/10.
 */
public class DoorBen {

    private String userName;
    private String sex;
    private String email;
    private String data;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    DoorBen(String userName, String sex, String email, String date) {
        this.userName = userName;
        this.sex = sex;
        this.email = email;
        this.data = date;
    }

}
