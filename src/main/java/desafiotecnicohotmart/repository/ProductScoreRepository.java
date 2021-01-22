package desafiotecnicohotmart.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import desafiotecnicohotmart.model.ProductScore;

public interface ProductScoreRepository extends JpaRepository<ProductScore, Long> {

	@Modifying
	@Query("UPDATE ProductScore ps SET z = ?1 WHERE ps.product.id = ?2 AND ps.referenceDate = ?3")
	void updateZByProductIdAndReferenceDate(int z, Long productId, LocalDate referenceDate);
	
}
