package cotato.backend.domain.application.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Part {

	PLANNING("기획"),
	DESIGN("디자이너"),
	FRONTEND("프론트엔드"),
	BACKEND("백엔드");

	private final String displayName;

	public static Part from(String displayName) {
		for (Part part : values()) {
			if (part.displayName.equals(displayName)) {
				return part;
			}
		}
		throw new IllegalArgumentException("존재하지 않는 파트입니다: " + displayName);
	}
}
