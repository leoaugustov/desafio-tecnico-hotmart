package desafiotecnicohotmart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import desafiotecnicohotmart.dto.CreateProductDto;
import desafiotecnicohotmart.error.exception.EntityNotFoundException;
import desafiotecnicohotmart.model.Product;
import desafiotecnicohotmart.model.ProductCategory;
import desafiotecnicohotmart.repository.ProductCategoryRepository;
import desafiotecnicohotmart.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ProductCategoryRepository productCategoryRepository;
	
	private ProductService productService;
	
	@Before
	public void initializeTestedObject() {
		productService = new ProductService(productRepository, productCategoryRepository);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testSave_when_categoryIdUnrecognized() {
		Long categoryId = 1L;
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getCategoryId()).thenReturn(categoryId);
		
		when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());
		
		productService.save(productDto);
	}
	
	@Test
	public void testSave_when_categoryIdRecognized() {
		Long categoryId = 1L;
		
		Product product = mock(Product.class);
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getCategoryId()).thenReturn(categoryId);
		when(productDto.getProduct()).thenReturn(product);
		
		ProductCategory productCategory = mock(ProductCategory.class);
		
		when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(productCategory));
		
		productService.save(productDto);
		
		verify(product).setCategory(productCategory);
		verify(productRepository).save(product);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testUpdateById_when_productIdUnrecognized() {
		Long productId = 1L;
		
		when(productRepository.findByIdAndActiveTrue(productId)).thenReturn(Optional.empty());
		
		productService.updateById(productId, null);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testUpdateById_when_categoryIdUnrecognized() {
		Long productId = 1L;
		Long categoryId = 1L;
		
		Product product = mock(Product.class);
		
		when(productRepository.findByIdAndActiveTrue(productId)).thenReturn(Optional.of(product));
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getProduct()).thenReturn(product);
		when(productDto.getCategoryId()).thenReturn(categoryId);
		
		when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());
		
		productService.updateById(productId, productDto);
	}
	
	@Test
	public void testUpdateById_when_categoryIdRecognized() {
		Long productId = 1L;
		Long categoryId = 1L;
		LocalDateTime creationDate = LocalDateTime.now();
		
		Product product = mock(Product.class);
		when(product.getCreationDate()).thenReturn(creationDate);
		
		when(productRepository.findByIdAndActiveTrue(productId)).thenReturn(Optional.of(product));
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getProduct()).thenReturn(product);
		when(productDto.getCategoryId()).thenReturn(categoryId);
		
		ProductCategory productCategory = mock(ProductCategory.class);
		
		when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(productCategory));
		
		productService.updateById(productId, productDto);
		
		verify(product).setId(productId);
		verify(product).setCreationDate(creationDate);
		verify(product).setCategory(productCategory);
		verify(productRepository).save(product);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testFindById_when_idUnrecognized() {
		Long id = 1L;
		
		when(productRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());
		
		productService.findById(id);
	}
	
	@Test
	public void testFindById_when_idRecognized() {
		Long id = 1L;
		
		Product product = mock(Product.class);
		
		when(productRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(product));
		
		assertThat(productService.findById(id).getProduct()).isEqualTo(product);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeactivateById_when_idUnrecognized() {
		Long id = 1L;
		
		when(productRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());
		
		productService.deactivateById(id);
	}
	
	@Test
	public void testDeactivateById_when_idRecognized() {
		Long id = 1L;
		
		Product product = mock(Product.class);
		
		when(productRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(product));
		
		productService.deactivateById(id);
		
		verify(product).setActive(false);
		verify(productRepository).save(product);
	}
	
}
