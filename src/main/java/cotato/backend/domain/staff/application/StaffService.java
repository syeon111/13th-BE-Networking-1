package cotato.backend.domain.staff.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.staff.dao.StaffRepository;
import cotato.backend.domain.staff.dto.request.StaffCreateRequest;
import cotato.backend.domain.staff.dto.request.StaffUpdateRequest;
import cotato.backend.domain.staff.dto.response.StaffResponse;
import cotato.backend.domain.staff.entity.Staff;
import cotato.backend.domain.staff.entity.StaffRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StaffService {

	private final StaffRepository staffRepository;

	@Transactional
	public Long save(StaffCreateRequest request) {
		Staff staff = Staff.builder()
			.name(request.name())
			.age(request.age())
			.phoneNumber(request.phoneNumber())
			.role(StaffRole.from(request.role()))
			.build();
		return staffRepository.save(staff).getId();
	}

	public StaffResponse findById(Long id) {
		return StaffResponse.from(
			staffRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.STAFF_NOT_FOUND))
		);
	}

	@Transactional
	public void update(Long id, StaffUpdateRequest request) {
		Staff staff = staffRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.STAFF_NOT_FOUND));
		staff.update(request.name(), request.age(), request.phoneNumber(), StaffRole.from(request.role()));
	}
}
