package aor.paj.proj3_vc_re_jc.dto;

import aor.paj.proj3_vc_re_jc.enums.UserRole;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EditOtherDto {

    @XmlElement
    private String password;
    @XmlElement
    private String email;
    @XmlElement
    private String firstName;
    @XmlElement
    private String lastName;
    @XmlElement
    private String phone;
    @XmlElement
    private boolean deleted;
    @XmlElement
    private String username;

    @XmlElement
    private UserRole role;

    public EditOtherDto() {
    }

    public String getPassword() {
        return password;
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

    public boolean isDeleted() {
        return deleted;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
