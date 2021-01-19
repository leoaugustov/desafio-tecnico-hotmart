package desafiotecnicohotmart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import desafiotecnicohotmart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@EntityGraph(attributePaths = {"categories"})
	Page<Product> findAll(Pageable pageable);
	
	@EntityGraph(attributePaths = {"categories"})
	Optional<Product> findById(Long id);
	
}
