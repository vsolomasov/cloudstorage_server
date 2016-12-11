package ru.donstu.cloudstorage.service.news;

import ru.donstu.cloudstorage.domain.news.entity.News;

import java.util.List;

/**
 * Класс сервисов новостей
 *
 * @author v.solomasov
 */
public interface NewsService {

    /**
     * Найти все новости
     *
     * @return
     */
    List<News> findAllNews();

    /**
     * Найти новость по номеру
     *
     * @param id
     * @return
     */
    News findNewsById(Long id);
}
