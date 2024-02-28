package aor.paj.proj3_vc_re_jc.enums;

public enum DeletedActivityType {

    USER(100),
    TASK(200),
    CATEGORY(300),
    COMMENT(400);

    private final int value;

    DeletedActivityType(int deletedActivityType) {
        this.value = deletedActivityType;
    }

    public int getValue() {
        return value;
    }

    public static DeletedActivityType fromValue(int value) {
        for (DeletedActivityType deletedActivityType : DeletedActivityType.values()) {
            if (deletedActivityType.getValue() == value) {
                return deletedActivityType;
            }
        }
        throw new IllegalArgumentException("No such deleted activity type with value: " + value);
    }
}