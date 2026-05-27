package cotato.backend.domain.like.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.like.dao.ApplicationLikeRepository;
import cotato.backend.domain.like.entity.ApplicationLike;
import cotato.backend.domain.staff.dao.StaffRepository;
import cotato.backend.domain.staff.entity.Staff;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationLikeService {

	private final ApplicationLikeRepository applicationLikeRepository;
	private final ApplicationRepository applicationRepository;
	private final StaffRepository staffRepository;

	public void like(Long applicationId, Long staffId) {
		if (applicationLikeRepository.existsByApplicationIdAndStaffId(applicationId, staffId)) {
			throw new AppException(ErrorCode.ALREADY_LIKED);
		}

		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.APPLICATION_NOT_FOUND));
		Staff staff = staffRepository.findById(staffId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.STAFF_NOT_FOUND));

		applicationLikeRepository.save(
			ApplicationLike.builder()
				.application(application)
				.staff(staff)
				.build()
		);
		application.increaseLikeCount();
	}

	public void cancelLike(Long applicationId, Long staffId) {
		ApplicationLike like = applicationLikeRepository.findByApplicationIdAndStaffId(applicationId, staffId)
			.orElseThrow(() -> new AppException(ErrorCode.NOT_LIKED));

		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.APPLICATION_NOT_FOUND));

		applicationLikeRepository.delete(like);
		application.decreaseLikeCount();
	}
}
