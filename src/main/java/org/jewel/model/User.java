package org.jewel.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "personal")
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    @Size(min = 4, max = 50, message = "Login should be at least 4 character length.")
    @Pattern(regexp = "[a-zA-Z0-9-_.]+", message = "Accepted only digits, letters, minus, underscore and dots ")
    private String login;

    @Column(nullable = false)
    @JsonIgnore
    @Size(min = 3, message = "Password must be at least 3 chars")
    private String encodedPassword;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date lastLogin;

    @Column
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
