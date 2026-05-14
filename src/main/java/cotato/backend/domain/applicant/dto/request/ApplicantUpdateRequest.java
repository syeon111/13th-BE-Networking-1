package cotato.backend.domain.applicant.dto.request;

public record ApplicantUpdateRequest(
	String name,
	int age,
	String phoneNumber
) {
}
