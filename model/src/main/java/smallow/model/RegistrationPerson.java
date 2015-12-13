package smallow.model;

import java.io.Serializable;

/**
 * Created by smallow on 15/12/1.
 */
public class RegistrationPerson implements Serializable {


    private String id;
    private String name;
    private String headIcon;//头像
    private String qq;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
