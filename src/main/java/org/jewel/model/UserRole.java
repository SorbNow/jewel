package org.jewel.model;

import javax.persistence.*;

@Entity
@Table
public class UserRole {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String roleName;

    @Column
    private String roleDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
