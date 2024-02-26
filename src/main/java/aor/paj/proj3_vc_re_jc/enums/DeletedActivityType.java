package aor.paj.proj3_vc_re_jc.enums;

public enum DeletedActivityType {
    USER(100),
    TASK(200),
    CATEGORY(300),
    COMMENT(400);

    private final int deletedActivityType;

    DeletedActivityType(int deletedActivityType) {
        this.deletedActivityType = deletedActivityType;
    }

    public int getDeletedActivityType() {
        return deletedActivityType;
    }
}
