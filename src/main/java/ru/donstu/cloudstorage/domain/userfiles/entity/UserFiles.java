package ru.donstu.cloudstorage.domain.userfiles.entity;

import ru.donstu.cloudstorage.domain.account.entity.Account;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Файлы пользователей
 *
 * @author v.solomasov
 */
@Entity
@Table(name = "user_files")
public class UserFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_gen")
    @SequenceGenerator(name = "id_gen", allocationSize = 1, sequenceName = "FILES_SEQ")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    private Account account;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "date_upload", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar dateUpload;

    public UserFiles() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Calendar getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Calendar dateUpload) {
        this.dateUpload = dateUpload;
    }
}
