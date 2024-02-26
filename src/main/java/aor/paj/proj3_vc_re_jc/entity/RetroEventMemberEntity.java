package aor.paj.proj3_vc_re_jc.entity;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "RetrospectiveEventMembers")
public class RetroEventMemberEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private RetroEventEntity event;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public RetroEventMemberEntity() {
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

}


