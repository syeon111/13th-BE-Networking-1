package cotato.backend.domain.staff.dto.request;

public record StaffUpdateRequest(
	String name,
	int age,
	String phoneNumber,
	String role
) {
}
