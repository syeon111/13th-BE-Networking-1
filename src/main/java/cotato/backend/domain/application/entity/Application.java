package cotato.backend.domain.application.entity;

import java.time.LocalDateTime;

import cotato.backend.domain.applicant.entity.Applicant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false)
	private Applicant applicant;

	@Column(name = "period", nullable = false)
	private int period;

	@Enumerated(EnumType.STRING)
	@Column(name = "part", nullable = false)
	private Part part;

	@Column(name = "ability", nullable = false)
	private int ability;

	@Column(name = "passion", nullable = false)
	private int passion;

	@Column(name = "application_time", nullable = false)
	private LocalDateTime applicationTime;

	@Column(name = "like_count", nullable = false)
	private int likeCount = 0;

	@Builder
	public Application(Applicant applicant, int period, Part part,
		int ability, int passion, LocalDateTime applicationTime) {
		this.applicant = applicant;
		this.period = period;
		this.part = part;
		this.ability = ability;
		this.passion = passion;
		this.applicationTime = applicationTime;
		this.likeCount = 0;
	}

	public void increaseLikeCount() {
		this.likeCount++;
	}

	public void decreaseLikeCount() {
		if (this.likeCount > 0) {
			this.likeCount--;
		}
	}
}
