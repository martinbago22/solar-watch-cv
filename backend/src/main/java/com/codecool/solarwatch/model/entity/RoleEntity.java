package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Role role;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserEntity> user;

    public RoleEntity(Role role) {
        this.role = role;
    }

    public RoleEntity() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<UserEntity> getUser() {
        return user;
    }

    public void setUser(Set<UserEntity> user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return id == that.id && role == that.role && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, user);
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "role=" + role +
                '}';
    }
}
