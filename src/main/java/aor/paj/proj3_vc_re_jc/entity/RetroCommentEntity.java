package aor.paj.proj3_vc_re_jc.entity;

import aor.paj.proj3_vc_re_jc.enums.RetroCommentCategory;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "retro_comment")
@NamedQuery(name = "RetroComment.findRetroCommentById", query = "SELECT a FROM RetroCommentEntity a WHERE a.id = :id")
@NamedQuery(name = "RetroComment.findAllRetroComments", query = "SELECT a FROM RetroCommentEntity a")
@NamedQuery(name = "RetroComment.findRetroCommentByEventId", query = "SELECT a FROM RetroCommentEntity a WHERE a.event = :eventId")
@NamedQuery(name = "RetroComment.findRetroCommentByUserId", query = "SELECT a FROM RetroCommentEntity a WHERE a.user = :userId")
@NamedQuery(name = "RetroComment.findAllRetroCommentsByEventId", query = "SELECT a FROM RetroCommentEntity a WHERE a.event = :eventId")
@NamedQuery(name = "RetroComment.findAllRetroCommentsByUserId", query = "SELECT a FROM RetroCommentEntity a WHERE a.user = :userId")
public class RetroCommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false, unique = false, updatable = false)
    private RetroEventEntity event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = false, updatable = false)
    private UserEntity user;

    @Column(name = "comment", nullable = false, unique = false, length = 500, columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RetroCommentCategory category;

    public RetroCommentEntity() {
    }

    public int getCommentId() {
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

    public String getCategory() {
        return String.valueOf(category);
    }

    public void setCategory(RetroCommentCategory category) {
        this.category = category;
    }

    public void setRetroEventEntity(Object o) {
    }
}