package com.data.siata.dto;

import com.mysql.cj.jdbc.Blob;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Blob profilePic;

    public String getUsername() {
        return username;
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
    public Blob getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(Blob profilePic) {
        this.profilePic = profilePic;
    }
}
