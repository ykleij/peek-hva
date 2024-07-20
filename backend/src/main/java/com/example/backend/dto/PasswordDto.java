package com.example.backend.dto;

public class PasswordDto {

    private String email;
    private String url;
    private String newPassword;

    public PasswordDto(String email, String url, String newPassword) {
        this.email = email;
        this.url = url;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
