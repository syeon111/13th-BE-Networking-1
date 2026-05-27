package cotato.backend.domain.applicant.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cotato.backend.domain.applicant.entity.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
	Optional<Applicant> findByPhoneNumber(String phoneNumber);
}
