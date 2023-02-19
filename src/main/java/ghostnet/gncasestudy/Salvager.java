package ghostnet.gncasestudy;

import jakarta.enterprise.context.*;
import java.io.Serializable;

@SessionScoped
public class Salvager implements Serializable {

    private int id;
    private String name;
    private String phone;
    private String userName;
    private String password;

    public Salvager() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCorrectLoginData(Salvager toLogin) {
        String otherUserName = toLogin.getUserName();
        String otherPassword = toLogin.getPassword();

        boolean isCorrectUserName = this.userName.equals(otherUserName);
        boolean isCorrectPassword = this.password.equals(otherPassword);

        return isCorrectUserName && isCorrectPassword;
    }
}
