package org.example.webserver.lib;

public class PasswordUpdate {
    String oldPassword;
    String newPassword;

    public PasswordUpdate() {}

    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewEncodedPassword() { return Encoder.getMd5(newPassword); }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
