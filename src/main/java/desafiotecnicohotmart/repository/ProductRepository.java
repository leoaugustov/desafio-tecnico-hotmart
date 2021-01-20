package desafiotecnicohotmart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import desafiotecnicohotmart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@EntityGraph(attributePaths = {"category"})
	Page<Product> findWithCategoryAllByActiveTrue(Pageable pageable);
	
	@EntityGraph(attributePaths = {"category"})
	Optional<Product> findByIdAndActiveTrue(Long id);
	
}
