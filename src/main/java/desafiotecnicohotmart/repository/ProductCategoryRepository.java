package desafiotecnicohotmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import desafiotecnicohotmart.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
