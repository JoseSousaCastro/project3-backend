package aor.paj.proj3_vc_re_jc.enums;

public enum RetroCommentCategory {

    STRENGTHS(100),
    CHALLENGES(200),
    IMPROVEMENTS(300);

    private final int value;

    RetroCommentCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RetroCommentCategory fromValue(int value) {
        for (RetroCommentCategory category : RetroCommentCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("No such category with value: " + value);
    }
}