package aor.paj.proj3_vc_re_jc.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateRetroEventDto {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int eventId;
    @XmlElement
    private String title;
    @XmlElement
    private String schedulingDate;

    public CreateRetroEventDto() {
    }

    // Getters e Setters

    public int getEventId() {
        return eventId;    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(String schedulingDate) {
        this.schedulingDate = schedulingDate;
    }
}