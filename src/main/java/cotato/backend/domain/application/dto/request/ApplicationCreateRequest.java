package cotato.backend.domain.application.dto.request;

public record ApplicationCreateRequest(
	String name,
	int period,
	int age,
	String part,
	int ability,
	int passion,
	String phoneNumber,
	String applicationTime
) {
}
