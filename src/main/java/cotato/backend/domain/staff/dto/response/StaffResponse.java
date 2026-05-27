package cotato.backend.domain.staff.dto.response;

import cotato.backend.domain.staff.entity.Staff;

public record StaffResponse(
	String name,
	int age,
	String phoneNumber,
	String role
) {
	public static StaffResponse from(Staff staff) {
		return new StaffResponse(
			staff.getName(),
			staff.getAge(),
			staff.getPhoneNumber(),
			staff.getRole().getDisplayName()
		);
	}
}
