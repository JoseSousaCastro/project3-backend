package aor.paj.proj3_vc_re_jc.enums;

public enum RetroCommentCategory {
    STRENGTHS(100),
    CHALLENGES(200),
    IMPROVEMENTS(300);

    private final int retroCommentCategory;

    RetroCommentCategory(int retroCommentCategory) {
        this.retroCommentCategory = retroCommentCategory;
    }

    public int getRetroCommentCategory() {
        return retroCommentCategory;
    }
}
