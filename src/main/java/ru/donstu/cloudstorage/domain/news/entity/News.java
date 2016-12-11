package ru.donstu.cloudstorage.domain.news.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Новости для страницы инфо
 *
 * @author v.solomasov
 */
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_gen")
    @SequenceGenerator(name = "id_gen", allocationSize = 1, sequenceName = "NEWS_SEQ")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String titleNews;

    @Column(name = "description", nullable = false)
    private String descriptionNews;

    @Type(type = "text")
    @Column(name = "text", nullable = false)
    private String textNews;

    public News() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleNews() {
        return titleNews;
    }

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    public String getDescriptionNews() {
        return descriptionNews;
    }

    public void setDescriptionNews(String descriptionNews) {
        this.descriptionNews = descriptionNews;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textNews) {
        this.textNews = textNews;
    }
}
