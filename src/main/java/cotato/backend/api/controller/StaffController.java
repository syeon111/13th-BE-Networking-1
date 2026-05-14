package cotato.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.api.dto.response.DefaultIdResponse;
import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.staff.application.StaffService;
import cotato.backend.domain.staff.dto.request.StaffCreateRequest;
import cotato.backend.domain.staff.dto.request.StaffUpdateRequest;
import cotato.backend.domain.staff.dto.response.StaffResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/staffs")
public class StaffController {

	private final StaffService staffService;

	@PostMapping
	public ResponseEntity<DataResponse<DefaultIdResponse>> save(
		@RequestBody StaffCreateRequest request
	) {
		return ResponseEntity.ok(
			DataResponse.created(DefaultIdResponse.of(staffService.save(request)))
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DataResponse<StaffResponse>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(DataResponse.from(staffService.findById(id)));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<DataResponse<Void>> update(
		@PathVariable Long id,
		@RequestBody StaffUpdateRequest request
	) {
		staffService.update(id, request);
		return ResponseEntity.ok(DataResponse.ok());
	}
}
