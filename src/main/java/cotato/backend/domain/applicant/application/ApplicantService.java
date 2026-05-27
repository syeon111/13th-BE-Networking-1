package cotato.backend.domain.applicant.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.applicant.dao.ApplicantRepository;
import cotato.backend.domain.applicant.dto.request.ApplicantUpdateRequest;
import cotato.backend.domain.applicant.dto.response.ApplicantResponse;
import cotato.backend.domain.applicant.entity.Applicant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantService {

	private final ApplicantRepository applicantRepository;

	public ApplicantResponse findById(Long id) {
		return ApplicantResponse.from(
			applicantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.APPLICANT_NOT_FOUND))
		);
	}

	@Transactional
	public void update(Long id, ApplicantUpdateRequest request) {
		Applicant applicant = applicantRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.APPLICANT_NOT_FOUND));
		applicant.update(request.name(), request.age(), request.phoneNumber());
	}
}
