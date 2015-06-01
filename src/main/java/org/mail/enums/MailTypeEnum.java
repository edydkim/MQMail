package org.mail.enums;

public enum MailTypeEnum implements org.apache.thrift.TEnum {
    DAILY(1, "daily", "daily"),
    WEEKLY(2, "weekly", "weekly");

    private int code;

    private String display1;

    private String display2;

    private MailTypeEnum(int code, String display1, String display2) {
        this.code = code;
        this.display1 = display1;
        this.display2 = display2;
    }

    public int getCode() {
        return this.code;
    }

    public String getDisplay1() {
        return this.display1;
    }

    public String getDisplay2() {
        return this.display2;
    }

    @Override
    public int getValue() {
        return code;
    }


    public static MailTypeEnum getEnum(int code) {
        for (MailTypeEnum e : MailTypeEnum.values()) {
            if (e.getValue() == code) {
                return e;
            }
        }
        return null;
    }
}
