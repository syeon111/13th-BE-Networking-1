package cotato.backend.domain.applicant.dto.response;

import cotato.backend.domain.applicant.entity.Applicant;

public record ApplicantResponse(
	String name,
	int age,
	String phoneNumber
) {
	public static ApplicantResponse from(Applicant applicant) {
		return new ApplicantResponse(
			applicant.getName(),
			applicant.getAge(),
			applicant.getPhoneNumber()
		);
	}
}
