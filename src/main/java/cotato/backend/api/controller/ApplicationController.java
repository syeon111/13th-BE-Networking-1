package cotato.backend.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.api.dto.response.DefaultIdResponse;
import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.application.application.ApplicationService;
import cotato.backend.domain.application.dto.request.ApplicationCreateRequest;
import cotato.backend.domain.application.dto.request.ApplicationListFilterRequest;
import cotato.backend.domain.application.dto.response.ApplicationDetailResponse;
import cotato.backend.domain.application.dto.response.ApplicationListResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/applications")
public class ApplicationController {

	private final ApplicationService applicationService;

	@PostMapping
	public ResponseEntity<DataResponse<DefaultIdResponse>> save(
		@RequestBody ApplicationCreateRequest request
	) {
		return ResponseEntity.ok(
			DataResponse.created(DefaultIdResponse.of(applicationService.save(request)))
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DataResponse<ApplicationDetailResponse>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(DataResponse.from(applicationService.findById(id)));
	}

	@GetMapping
	public ResponseEntity<DataResponse<List<ApplicationListResponse>>> findAll(
		@RequestBody ApplicationListFilterRequest request
	) {
		return ResponseEntity.ok(DataResponse.from(applicationService.findAll(request)));
	}
}
