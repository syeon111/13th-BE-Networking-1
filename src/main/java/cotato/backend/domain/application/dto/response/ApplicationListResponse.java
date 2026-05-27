package cotato.backend.domain.application.dto.response;

import cotato.backend.domain.application.entity.Application;

public record ApplicationListResponse(
	Long applicationId,
	String name,
	int period,
	String part,
	int likeCount
) {
	public static ApplicationListResponse from(Application application) {
		return new ApplicationListResponse(
			application.getId(),
			application.getApplicant().getName(),
			application.getPeriod(),
			application.getPart().getDisplayName(),
			application.getLikeCount()
		);
	}
}
