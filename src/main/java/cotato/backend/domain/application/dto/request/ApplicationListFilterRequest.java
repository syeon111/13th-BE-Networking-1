package cotato.backend.domain.application.dto.request;

public record ApplicationListFilterRequest(
	String filterBy,  // "likes", "gisu", "gisu+likes"
	Integer period,   // filterBy가 "gisu" 또는 "gisu+likes"일 때 사용
	int page,
	int pageSize
) {
}
