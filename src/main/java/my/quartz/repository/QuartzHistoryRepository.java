package my.quartz.repository;

import my.quartz.domain.QuartzHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartzHistoryRepository extends JpaRepository<QuartzHistory, Long> {
}
