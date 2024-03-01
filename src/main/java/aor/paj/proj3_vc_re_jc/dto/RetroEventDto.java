package aor.paj.proj3_vc_re_jc.dto;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class RetroEventDto {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private LocalDate date;
    @XmlElement
    private List<String> retroMembers = new ArrayList<>();
    @XmlElement
    private List<RetroCommentDto> retroComments = new ArrayList<>();


    public RetroEventDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = LocalDate.now();
    }

    public void addMember(String username) {
        retroMembers.add(username);
    }

    public List<String> getRetroMembers() {
        return retroMembers;
    }

    public void addComment(RetroCommentDto retroComment) {
        retroComments.add(retroComment);
    }

    public List<RetroCommentDto> getRetroComments() {
        return retroComments;
    }

}