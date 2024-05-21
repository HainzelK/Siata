package com.data.siata.dto;

import javax.sql.rowset.serial.SerialBlob;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private SerialBlob profilePic;
    private String gender;
    private String noTelp;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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