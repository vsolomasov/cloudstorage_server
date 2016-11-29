package ru.donstu.cloudstorage.domain.news;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donstu.cloudstorage.domain.news.entity.News;

import java.util.List;

/**
 * @author v.solomasov
 */
public interface NewsRepository extends JpaRepository<News, Long> {
}
