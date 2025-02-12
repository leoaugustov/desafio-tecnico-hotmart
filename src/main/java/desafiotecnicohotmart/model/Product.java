package desafiotecnicohotmart.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Audited
@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@NotBlank(message = "Name is mandatory")
	@Column(nullable = false)
	private String name;
	
	@NotBlank(message = "Description is mandatory")
	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;
	
	@Column(name = "is_active", nullable = false)
	@JsonIgnore
	private boolean active = true;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	@JsonProperty(access = Access.READ_ONLY)
	@NotAudited
	private LocalDateTime creationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonProperty(access = Access.READ_ONLY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ProductCategory category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinFormula("(SELECT ps.id FROM product_score ps WHERE ps.product_id = id ORDER BY ps.id DESC LIMIT 1)")
	@JsonProperty(value = "score", access = Access.READ_ONLY)
	@NotAudited
	@Setter(AccessLevel.NONE)
	private ProductScore lastScore;
	
	public long getDaysOfExistence(LocalDate now) {
		if(getCreationDate() == null) {
			return 0;
		}
		LocalDate creationDate = getCreationDate().toLocalDate();
		return creationDate.until(now, ChronoUnit.DAYS);
	}
	
	@JsonGetter("score")
	private double getLastScore() {
		if(lastScore == null) {
			return 0;
		}
		return lastScore.getScore();
	}
	
}
