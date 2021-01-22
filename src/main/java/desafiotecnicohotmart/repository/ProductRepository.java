package desafiotecnicohotmart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import desafiotecnicohotmart.model.Product;
import desafiotecnicohotmart.projection.ProductSalesAmountProjection;
import desafiotecnicohotmart.projection.ProductSalesAverageRatingProjection;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@EntityGraph(attributePaths = {"category"})
	Page<Product> findWithCategoryAllByActiveTrue(Pageable pageable);
	
	Page<Product> findAllByActiveTrue(Pageable pageable);
	
	@EntityGraph(attributePaths = {"lastScore"})
	Page<Product> findAllWithLastScoreByActiveTrue(Pageable pageable);
	
	@EntityGraph(attributePaths = {"lastScore"})
	@Query("SELECT p FROM Product p WHERE p.active = true AND (p.name LIKE %?1% OR p.description LIKE %?1%)")
	Page<Product> findAllWithLastScoreByActiveTrueAndNameOrDescriptionContaining(String q, Pageable pageable);
	
	@EntityGraph(attributePaths = {"category"})
	Optional<Product> findByIdAndActiveTrue(Long id);
	
	@Query("SELECT p.id as productId, COUNT(p.id) as salesAmount FROM Product p, Sale s WHERE s.product.id = p.id GROUP BY p.id")
	List<ProductSalesAmountProjection> findAllWithSalesAmount();
	
	@Query(
			value = "SELECT p.id as productId, AVG(s.product_rating) as salesAverageRating FROM product p, sale s "
					+ "WHERE s.product_id = p.id AND s.creation_date > DATE_SUB(NOW(), INTERVAL 12 MONTH) GROUP BY p.id", 
			nativeQuery = true
	)
	List<ProductSalesAverageRatingProjection> findAllWithSalesAverageRating();
	
}
