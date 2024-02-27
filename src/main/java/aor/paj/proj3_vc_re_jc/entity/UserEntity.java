package aor.paj.proj3_vc_re_jc.entity;



import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name="user")
@NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username")
@NamedQuery(name = "User.findUserByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email")
@NamedQuery(name = "User.findUserByToken", query = "SELECT DISTINCT u FROM UserEntity u WHERE u.tokenId = :token")
@NamedQuery(name = "User.findAllUsers", query = "SELECT u FROM UserEntity u")
public class UserEntity implements Serializable{


    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;


    @Column(name="email", nullable=false, unique = true, updatable = true)
    private String email;

    @Column(name="first_name", nullable = false, unique = false, updatable = true)
    private String firstName;

    @Column(name="last_name", nullable = false, unique = false, updatable = true)
    private String lastName;

    @Column(name="phone", nullable = false, unique = false, updatable = true)
    private String phone;

    @Column(name="profile_photo", nullable = false, unique = false, updatable = true)
    private String photoURL;

    @Column(name="password", nullable=false, unique = false, updatable = true)
    private String password;

    @Column(name="username", nullable=false, unique = true, updatable = false)
    private String username;

    @Column(name="deleted", nullable = false, unique = false, updatable = true)
    private boolean deleted;

    @Column(name="role", nullable = false, unique = false, updatable = true)
    private int role;

    @Column(name = "token_id")
    private String tokenId;

/*    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_token", referencedColumnName = "id")  //
    private TokenEntity token;*/



    /*@Column(name="tokenExpiration", nullable = true, unique = false, updatable = true)
    private Instant tokenExpiration;*/

    @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST)
    private Set<TaskEntity> tasks;


    //default empty constructor
    public UserEntity() {}

    /*public Instant getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Instant tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    } */



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public int getRole() {
        return role;
    }



    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getId() {
        return id;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public void setPhotoURL(String profilePhoto) {
        this.photoURL = profilePhoto;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}


