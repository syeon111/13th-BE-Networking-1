package cotato.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.applicant.application.ApplicantService;
import cotato.backend.domain.applicant.dto.request.ApplicantUpdateRequest;
import cotato.backend.domain.applicant.dto.response.ApplicantResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/applicants")
public class ApplicantController {

	private final ApplicantService applicantService;

	@GetMapping("/{id}")
	public ResponseEntity<DataResponse<ApplicantResponse>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(DataResponse.from(applicantService.findById(id)));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<DataResponse<Void>> update(
		@PathVariable Long id,
		@RequestBody ApplicantUpdateRequest request
	) {
		applicantService.update(id, request);
		return ResponseEntity.ok(DataResponse.ok());
	}
}
