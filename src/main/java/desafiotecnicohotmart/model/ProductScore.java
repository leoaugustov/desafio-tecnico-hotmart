package desafiotecnicohotmart.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_score")
@Getter
@Setter
@NoArgsConstructor
public class ProductScore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private LocalDate referenceDate;
	
	@Column(nullable = false, updatable = false)
	private double x;
	
	@Column(nullable = false, updatable = false)
	private double y;
	
	@Column(nullable = false)
	private int z;
	
	@Formula("x + y + z")
	@JsonValue
	@Setter(AccessLevel.NONE)
	private double score;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false, updatable = false)
	private Product product;
	
	public ProductScore(double productSalesAverageRating, int productSalesAmount, int productDaysOfExistence, Product product) {
		if(product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		
		x = productSalesAverageRating;
		y = productDaysOfExistence == 0 ? 0 : (double) productSalesAmount / productDaysOfExistence;
		this.product = product;
	}
	
}
