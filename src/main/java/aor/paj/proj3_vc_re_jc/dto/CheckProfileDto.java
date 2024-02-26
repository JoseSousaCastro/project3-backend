package aor.paj.proj3_vc_re_jc.dto;



import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckProfileDto {

    @XmlElement
    private String username;

    @XmlElement
    private String email;

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private String phone;

    @XmlElement
    private String photoURL;

    @XmlElement
    private int role;

    @XmlElement
    private Boolean deleted;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public int getRole() {
        return role;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

