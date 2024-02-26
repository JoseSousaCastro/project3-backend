package aor.paj.proj3_vc_re_jc.dto;



import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleDto {

    @XmlElement
    private int role;

    @XmlElement
    private String username;

    public int getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

