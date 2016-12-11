package ru.donstu.cloudstorage.service.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.donstu.cloudstorage.domain.news.NewsRepository;
import ru.donstu.cloudstorage.domain.news.entity.News;

import java.util.List;

/**
 * Реализация интерфейса {@link NewsService}
 *
 * @author v.solomasov
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News findNewsById(Long id) {
        if (id == null) {
            return null;
        }
        return newsRepository.findById(id);
    }
}
