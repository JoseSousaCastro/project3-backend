package aor.paj.proj3_vc_re_jc.entity;


import aor.paj.proj3_vc_re_jc.enums.RetroCommentCategory;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "retro_comment")
@NamedQuery(name = "RetroComment.findretroCommentById", query = "SELECT a FROM RetroCommentEntity a WHERE a.id = :id")
public class RetroCommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false, unique = false, updatable = false)
    private RetroEventEntity event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = false, updatable = false)
    private UserEntity user;

    @Column(name = "comment", nullable = false, unique = false, length = 255, columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RetroCommentCategory category;

    public RetroCommentEntity() {
    }

    public String getCommentId() {
        return commentId;
    }

    public RetroEventEntity getEvent() {
        return event;
    }

    public void setEvent(RetroEventEntity event) {
        this.event = event;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RetroCommentCategory getCategory() {
        return category;
    }

    public void setCategory(RetroCommentCategory category) {
        this.category = category;
    }
}

