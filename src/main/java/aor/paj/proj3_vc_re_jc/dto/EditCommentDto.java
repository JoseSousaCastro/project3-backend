package aor.paj.proj3_vc_re_jc.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EditCommentDto {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int commentId;
    @XmlElement
    private String updatedComment;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUpdatedComment() {
        return updatedComment;
    }

    public void setUpdatedComment(String updatedComment) {
        this.updatedComment = updatedComment;
    }
}