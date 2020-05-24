package org.jewel.web;

import javax.validation.constraints.NotBlank;

public class ChangePasswordForm {
    @NotBlank(message = "Поле не может быть пустым")
    private String newPassword;

    @NotBlank(message = "Поле не может быть пустым")
    private String confirmNewPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
