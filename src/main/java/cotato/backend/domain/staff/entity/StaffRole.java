package cotato.backend.domain.staff.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StaffRole {

	PART_LEADER("파트장"),
	PLANNING_LEADER("기획팀장"),
	PR_LEADER("홍보팀장"),
	VICE_PRESIDENT("부회장"),
	PRESIDENT("회장"),
	EDUCATION_LEADER("교육팀장");

	private final String displayName;

	public static StaffRole from(String value) {
		for (StaffRole role : values()) {
			if (role.name().equalsIgnoreCase(value) || role.displayName.equals(value)) {
				return role;
			}
		}
		throw new IllegalArgumentException("존재하지 않는 운영진 역할입니다: " + value);
	}
}
