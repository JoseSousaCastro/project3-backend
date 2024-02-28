package aor.paj.proj3_vc_re_jc.dto;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;

@XmlRootElement
public class RetroEventDto {
    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private LocalDate date;
    @XmlElement
    private ArrayList<String> retroMembers = new ArrayList<>();
    @XmlElement
    private ArrayList<RetroCommentDto> retroComments = new ArrayList<>();


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

    public ArrayList<String> getRetroMembers() {
        return retroMembers;
    }

    public void addComment(RetroCommentDto retroComment) {
        retroComments.add(retroComment);
    }

    public ArrayList<RetroCommentDto> getRetroComments() {
        return retroComments;
    }

}