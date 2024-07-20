package com.example.backend.dto;

public class UserRegistrationDTO {
    private String email;
    private String password;
    private String invitationURL;

    public UserRegistrationDTO(String email, String password, String invitationURL) {
        this.email = email;
        this.password = password;
        this.invitationURL = invitationURL;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getInvitationURL() {
        return invitationURL;
    }
}
