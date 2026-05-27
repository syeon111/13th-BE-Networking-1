package cotato.backend.domain.application.dto.response;

import java.time.format.DateTimeFormatter;

import cotato.backend.domain.application.entity.Application;

public record ApplicationDetailResponse(
	String name,
	int period,
	int age,
	String part,
	int ability,
	int passion,
	String phoneNumber,
	String applicationTime
) {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static ApplicationDetailResponse from(Application application) {
		return new ApplicationDetailResponse(
			application.getApplicant().getName(),
			application.getPeriod(),
			application.getApplicant().getAge(),
			application.getPart().getDisplayName(),
			application.getAbility(),
			application.getPassion(),
			application.getApplicant().getPhoneNumber(),
			application.getApplicationTime().format(FORMATTER)
		);
	}
}
