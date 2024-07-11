package top.zekee.acmerbackend.pojo;

import io.swagger.v3.oas.models.security.SecurityScheme;

public class Code {
    public final static Integer SUCCESS = 20021;
    public static final Integer USERNAME_NOT_EXIST = 20040;
    public final static Integer USERNAME_EXIST = 20041;
    public final static Integer WRONG_PASSWORD = 20042;
    public final static Integer WRONG_PARAMETER = 20043;
    public final static Integer WRONG_HEADER = 20044;
    public final static Integer WRONG_METHOD = 20045;
    public static final Integer TOKEN_EXPIRED = 20046;
    public static final Integer INVALID_TOKEN = 20047;
    public static final Integer INSUFFICIENT_PERMISSIONS = 20048;
    public static final Integer FREQUENT_REQUEST = 20049;
    public final static Integer UNKNOWN_ERROR = 20050;
}