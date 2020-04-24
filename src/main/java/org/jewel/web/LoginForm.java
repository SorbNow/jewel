package org.jewel.web;

public class LoginForm {
    private String selectedUserName;
    private String password;
    private String userGroupName;

    public String getGroupName() {
        return userGroupName;
    }

    public void setGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getSelectedUserName() {
        return selectedUserName;
    }

    public void setSelectedUserName(String selectedUserName) {
        this.selectedUserName = selectedUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
