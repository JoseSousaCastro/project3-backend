package aor.paj.proj3_vc_re_jc.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "retro_event")
@NamedQuery(name = "RetroEvent.findRetroEventById", query = "SELECT a FROM RetroEventEntity a WHERE a.id = :id")
@NamedQuery(name = "RetroEvent.findAllRetroEvents", query = "SELECT a FROM RetroEventEntity a")
@NamedQuery(name = "RetroEvent.findRetroEventByUserId", query = "SELECT a FROM RetroEventEntity a WHERE a.members = :userId")
public class RetroEventEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int eventId;

    @Column(name = "title", nullable = false, unique = false, length = 25, columnDefinition = "TEXT")
    private String title;

    @Column(name = "scheduling_date", nullable = false, unique = false, updatable = false)
    private LocalDate schedulingDate;

    @ManyToMany
    @JoinTable(
            name = "retrospective_event_members",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> members = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<RetroCommentEntity> comments = new ArrayList<>();

    public RetroEventEntity() {
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(LocalDate schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

    public void addMember(UserEntity member) {
        members.add(member);
    }

    public List<RetroCommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<RetroCommentEntity> comments) {
        this.comments = comments;
    }

    public void addComment(RetroCommentEntity comment) {
        comments.add(comment);
    }



    public RetroCommentEntity getComment(int id2) {
        for (RetroCommentEntity comment : comments) {
            if (comment.getCommentId() == id2) {
                return comment;
            }
        }
        return null;
    }

    public void removeComment(RetroCommentEntity retroCommentEntity) {
        comments.remove(retroCommentEntity);
        retroCommentEntity.setRetroEventEntity(null);
    }
}