package cotato.backend.domain.application.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.applicant.dao.ApplicantRepository;
import cotato.backend.domain.applicant.entity.Applicant;
import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.dto.request.ApplicationCreateRequest;
import cotato.backend.domain.application.dto.request.ApplicationListFilterRequest;
import cotato.backend.domain.application.dto.response.ApplicationDetailResponse;
import cotato.backend.domain.application.dto.response.ApplicationListResponse;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.application.entity.Part;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationService {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final ApplicationRepository applicationRepository;
	private final ApplicantRepository applicantRepository;

	@Transactional
	public Long save(ApplicationCreateRequest request) {
		// 핸드폰 번호로 기존 지원자 조회, 없으면 새로 생성
		Applicant applicant = applicantRepository.findByPhoneNumber(request.phoneNumber())
			.orElseGet(() -> applicantRepository.save(
				Applicant.builder()
					.name(request.name())
					.age(request.age())
					.phoneNumber(request.phoneNumber())
					.build()
			));

		Application application = Application.builder()
			.applicant(applicant)
			.period(request.period())
			.part(Part.from(request.part()))
			.ability(request.ability())
			.passion(request.passion())
			.applicationTime(LocalDateTime.parse(request.applicationTime(), FORMATTER))
			.build();

		return applicationRepository.save(application).getId();
	}

	public ApplicationDetailResponse findById(Long id) {
		Application application = applicationRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.APPLICATION_NOT_FOUND));
		return ApplicationDetailResponse.from(application);
	}

	public List<ApplicationListResponse> findAll(ApplicationListFilterRequest request) {
		int page = request.page() - 1; // 0-based
		int pageSize = request.pageSize();

		Page<Application> applications = switch (request.filterBy()) {
			case "gisu" -> {
				Pageable pageable = PageRequest.of(page, pageSize);
				yield applicationRepository.findAllByPeriod(request.period(), pageable);
			}
			case "likes" -> {
				Pageable pageable = PageRequest.of(page, pageSize, Sort.by("likeCount").descending());
				yield applicationRepository.findAll(pageable);
			}
			case "gisu+likes" -> {
				Pageable pageable = PageRequest.of(page, pageSize);
				yield applicationRepository.findAllByPeriodOrderByLikeCountDesc(request.period(), pageable);
			}
			default -> throw new AppException(ErrorCode.INVALID_FILTER_TYPE);
		};

		return applications.stream()
			.map(ApplicationListResponse::from)
			.toList();
	}
}
