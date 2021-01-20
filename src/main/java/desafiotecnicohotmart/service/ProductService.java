package desafiotecnicohotmart.service;

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
		ProductCategory productCategory = productCategoryRepository.findById(productDto.getCategoryId())
				.orElseThrow(() -> new EntityNotFoundException(ProductCategory.class, productDto.getCategoryId()));
		
		Product product = productDto.getProduct();
		product.setCategory(productCategory);
		return productRepository.save(product);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Product updateById(Long id, CreateProductDto updatedProductDto) {
		Product product = productRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, id));
		
		updatedProductDto.getProduct().setId(id);
		updatedProductDto.getProduct().setCreationDate(product.getCreationDate());
		return save(updatedProductDto);
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
