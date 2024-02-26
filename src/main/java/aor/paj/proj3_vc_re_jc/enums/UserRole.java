package aor.paj.proj3_vc_re_jc.enums;

public enum UserRole {

    DEVELOPER(100),

    SCRUM_MASTER(200),
    PRODUCT_OWNER(300);

    private final int value;

    UserRole (int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
