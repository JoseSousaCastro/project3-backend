package aor.paj.proj3_vc_re_jc.dto;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;

@XmlRootElement
public class RetroEventDTO {
    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private LocalDate date;
    private ArrayList<String> retroMembers = new ArrayList<>();
    @XmlElement
    private ArrayList<RetroCommentDTO> retroComments = new ArrayList<>();


    public RetroEventDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String generateId() {
        this.id = String.valueOf(System.currentTimeMillis());
        return id;
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

    public void addComment(RetroCommentDTO retroComment) {
        retroComments.add(retroComment);
    }

    public ArrayList<RetroCommentDTO> getRetroComments() {
        return retroComments;
    }

}

