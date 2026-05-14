package cotato.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "COMMON-001"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다.", "COMMON-002"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다.", "COMMON-003"),

	// Applicant
	APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND, "지원자를 찾을 수 없습니다.", "APPLICANT-001"),

	// Application
	APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원 서류를 찾을 수 없습니다.", "APPLICATION-001"),
	INVALID_FILTER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 필터 타입입니다.", "APPLICATION-002"),

	// Staff
	STAFF_NOT_FOUND(HttpStatus.NOT_FOUND, "운영진을 찾을 수 없습니다.", "STAFF-001"),

	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생하였습니다.", "COMMON-004"),
	;

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}