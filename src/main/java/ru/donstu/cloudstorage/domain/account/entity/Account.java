package ru.donstu.cloudstorage.domain.account.entity;

import ru.donstu.cloudstorage.domain.account.enums.Role;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Аккаунты
 *
 * @author vyacheslafka
 */
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_gen")
    @SequenceGenerator(name = "id_gen", allocationSize = 1, sequenceName = "ACCOUNT_SEQ")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "date_create_account", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar dataCreate;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Calendar getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Calendar dataCreate) {
        this.dataCreate = dataCreate;
    }
}
