package cotato.backend.domain.application.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cotato.backend.domain.application.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	// 기수별 조회
	Page<Application> findAllByPeriod(int period, Pageable pageable);

	// 좋아요 순 조회 → Pageable에 Sort.by("likeCount").descending() 적용
	Page<Application> findAll(Pageable pageable);

	// 기수 + 좋아요 순 조회
	Page<Application> findAllByPeriodOrderByLikeCountDesc(int period, Pageable pageable);
}
