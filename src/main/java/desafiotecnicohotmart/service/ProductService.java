package desafiotecnicohotmart.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import desafiotecnicohotmart.dto.CreateProductDto;
import desafiotecnicohotmart.dto.PageDto;
import desafiotecnicohotmart.error.exception.EntityNotFoundException;
import desafiotecnicohotmart.model.Product;
import desafiotecnicohotmart.model.ProductCategory;
import desafiotecnicohotmart.repository.ProductCategoryRepository;
import desafiotecnicohotmart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public Product save(CreateProductDto productDto) {
		List<ProductCategory> productCategories = productCategoryRepository.findAllById(productDto.getCategoriesIds());
		Set<Long> foundCategories = productCategories.stream()
				.map(ProductCategory::getId)
				.collect(Collectors.toSet());
		
		Set<Long> notFoundCategories = productDto.getCategoriesIds().stream()
				.filter(categoryId -> ! foundCategories.contains(categoryId))
				.collect(Collectors.toSet());
		
		if(notFoundCategories.isEmpty()) {
			Product product = productDto.getProduct();
			product.setCategories(productCategories);
			
			return productRepository.save(product);
		}
		throw new EntityNotFoundException(ProductCategory.class, notFoundCategories);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Product updateById(Long id, CreateProductDto updatedProductDto) {
		if(productRepository.existsByIdAndActiveTrue(id)) {
			updatedProductDto.getProduct().setId(id);
			return save(updatedProductDto);
		}
		throw new EntityNotFoundException(Product.class, id);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deactivateById(Long id) {
		Product product = productRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, id));
		
		product.setActive(false);
		productRepository.save(product);
	}

	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, id));
	}
	
	@Transactional(readOnly = true)
	public PageDto<Product> findAll(int page, int pageSize) {
		return new PageDto<>(productRepository.findAllByActiveTrue(PageRequest.of(page, pageSize)));
	}
	
}
