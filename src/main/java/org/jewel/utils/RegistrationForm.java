package org.jewel.utils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RegistrationForm {

    @Size(min = 4, max = 50, message = "Login should be at least 4 character length.")
    @Pattern(regexp = "[a-zA-Z0-9-_.]+", message = "Accepted only digits, letters, minus, underscore and dots ")
    private String login;

    @Size(min = 4, message = "Слишком короткий пароль")
    @NotBlank(message = "Поле пароля должно быть заполнено")
    private String password;

    @NotNull(message = "Укажите роль")
    private String role;


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


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationForm form = (RegistrationForm) o;
        return Objects.equals(login, form.login) &&
                Objects.equals(password, form.password);

    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
