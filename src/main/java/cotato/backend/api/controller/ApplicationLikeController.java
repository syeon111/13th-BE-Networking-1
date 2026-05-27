package cotato.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.like.application.ApplicationLikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/applications")
public class ApplicationLikeController {

	private final ApplicationLikeService applicationLikeService;

	@PostMapping("/{applicationId}/likes")
	public ResponseEntity<DataResponse<Void>> like(
		@PathVariable Long applicationId,
		@RequestParam Long staffId
	) {
		applicationLikeService.like(applicationId, staffId);
		return ResponseEntity.ok(DataResponse.ok());
	}

	@DeleteMapping("/{applicationId}/likes")
	public ResponseEntity<DataResponse<Void>> cancelLike(
		@PathVariable Long applicationId,
		@RequestParam Long staffId
	) {
		applicationLikeService.cancelLike(applicationId, staffId);
		return ResponseEntity.ok(DataResponse.ok());
	}
}
