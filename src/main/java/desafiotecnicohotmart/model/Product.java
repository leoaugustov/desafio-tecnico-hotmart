package desafiotecnicohotmart.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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
	@JsonProperty(access = Access.READ_ONLY)
	private boolean active = true;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	@JsonProperty(access = Access.READ_ONLY)
	@Setter(AccessLevel.NONE)
	private LocalDateTime creationDate;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "product_category_product", 
			joinColumns = @JoinColumn(name = "product_id"), 
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	@JsonProperty(access = Access.READ_ONLY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private List<ProductCategory> categories = new ArrayList<>();
	
}
