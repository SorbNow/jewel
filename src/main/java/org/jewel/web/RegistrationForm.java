package org.jewel.web;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RegistrationForm {

    @Size(min = 4, max = 50, message = "Login should be at least 4 character length.")
    @Pattern(regexp = "[a-zA-Z0-9-_.]+", message = "Accepted only digits, letters, minus, underscore and dots ")
    private String login;

    private String password;
    private String selectedGroupName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelectedGroupName() {
        return selectedGroupName;
    }

    public void setSelectedGroupName(String selectedGroupName) {
        this.selectedGroupName = selectedGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationForm form = (RegistrationForm) o;
        return Objects.equals(login, form.login) &&
                Objects.equals(password, form.password) &&
                Objects.equals(selectedGroupName, form.selectedGroupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password,  selectedGroupName);
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", selectedGroup=" + selectedGroupName +
                '}';
    }
}
