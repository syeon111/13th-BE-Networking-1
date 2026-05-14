package cotato.backend.domain.staff.dto.request;

public record StaffCreateRequest(
	String name,
	int age,
	String phoneNumber,
	String role
) {
}
