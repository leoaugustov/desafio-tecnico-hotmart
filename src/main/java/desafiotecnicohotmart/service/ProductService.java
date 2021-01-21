package desafiotecnicohotmart.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import desafiotecnicohotmart.dto.CreateProductDto;
import desafiotecnicohotmart.dto.PageDto;
import desafiotecnicohotmart.dto.ProductWithoutScoreDto;
import desafiotecnicohotmart.dto.ProductsSearchDto;
import desafiotecnicohotmart.error.exception.EntityNotFoundException;
import desafiotecnicohotmart.model.Product;
import desafiotecnicohotmart.model.ProductCategory;
import desafiotecnicohotmart.model.ProductsSearchSortType;
import desafiotecnicohotmart.repository.ProductCategoryRepository;
import desafiotecnicohotmart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public ProductWithoutScoreDto save(CreateProductDto productDto) {
		ProductCategory productCategory = productCategoryRepository.findById(productDto.getCategoryId())
				.orElseThrow(() -> new EntityNotFoundException(ProductCategory.class, productDto.getCategoryId()));
		
		Product product = productDto.getProduct();
		product.setCategory(productCategory);
		return new ProductWithoutScoreDto(productRepository.save(product));
	}
	
	@Transactional(rollbackFor = Exception.class)
	public ProductWithoutScoreDto updateById(Long id, CreateProductDto updatedProductDto) {
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
	public ProductWithoutScoreDto findById(Long id) {
		return productRepository.findByIdAndActiveTrue(id)
				.map(ProductWithoutScoreDto::new)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, id));
	}
	
	@Transactional(readOnly = true)
	public PageDto<ProductWithoutScoreDto> findAll(int page, int pageSize) {
		PageRequest pageRequest = PageRequest.of(page, pageSize);
		Page<ProductWithoutScoreDto> productsPage = productRepository.findWithCategoryAllByActiveTrue(pageRequest)
				.map(ProductWithoutScoreDto::new);
		
		return new PageDto<>(productsPage);
	}
	
	@Transactional(readOnly = true)
	public ProductsSearchDto search(String q, ProductsSearchSortType sort, int page, int pageSize) {
		PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(sort.getDirection(), sort.getPropertyName()));
		
		Page<Product> productsPage;
		if(q.isEmpty()) {
			productsPage = productRepository.findAllWithLastScoreByActiveTrue(pageRequest);
		}else {
			productsPage = productRepository.findAllWithLastScoreByActiveTrueAndNameOrDescriptionContaining(q, pageRequest);
		}
		return new ProductsSearchDto(LocalDateTime.now(), q, new PageDto<>(productsPage));
	}
	
}
