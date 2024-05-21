package com.data.siata.model;

import javax.sql.rowset.serial.SerialBlob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "username", columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "email", columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "full_name", columnDefinition = "VARCHAR(100)")
    private String fullName;

    @Column(name = "profile_pic", columnDefinition = "BLOB")
    private SerialBlob profilePic;

    @Column(name = "gender", columnDefinition = "ENUM('Male', 'Female')")
    private String gender;

    @Column(name = "no_telp", columnDefinition = "VARCHAR(20)")
    private String noTelp;

    public User() {
    }

    //Sign in
    public User(String username, String password, String email, String fullName, String gender,String noTelp) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.noTelp = noTelp;
    }

    public User(SerialBlob profilePic) {
        this.profilePic = profilePic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public SerialBlob getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(SerialBlob profilePic) {
        this.profilePic = profilePic;
    }

}
