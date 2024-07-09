package top.zekee.acmerbackend.pojo;

import java.util.Objects;

public class AuthMap {
    static final Integer ADMIN = 0;
    static final Integer USER = 1;

    public static Boolean isAdmin(Integer auth) {
        return Objects.equals(auth, ADMIN);
    }
}
