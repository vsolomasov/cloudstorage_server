package ru.donstu.cloudstorage.domain.account.entity;

import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "increment")
    @GenericGenerator(strategy = "increment", name = "increment")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "date_birthday")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar dateBirthday;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(Calendar dateBirthday) {
        this.dateBirthday = dateBirthday;
    }
}
