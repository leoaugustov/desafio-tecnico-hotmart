package desafiotecnicohotmart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public void testSave_when_categoryIdUnreconized() {
		Set<Long> categoriesIds = new HashSet<>();
		categoriesIds.add(1L);
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getCategoriesIds()).thenReturn(categoriesIds);
		
		when(productCategoryRepository.findAllById(categoriesIds)).thenReturn(Collections.emptyList());
		
		productService.save(productDto);
	}
	
	@Test
	public void testSave_when_categoryIdReconized() {
		Long productCategoryId = 1L;
		
		Set<Long> categoriesIds = new HashSet<>();
		categoriesIds.add(productCategoryId);
		
		Product product = mock(Product.class);
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getCategoriesIds()).thenReturn(categoriesIds);
		when(productDto.getProduct()).thenReturn(product);
		
		ProductCategory productCategory = mock(ProductCategory.class);
		when(productCategory.getId()).thenReturn(productCategoryId);
		List<ProductCategory> categories = Arrays.asList(productCategory);
		
		when(productCategoryRepository.findAllById(categoriesIds)).thenReturn(categories);
		
		productService.save(productDto);
		
		verify(product).setCategories(categories);
		verify(productRepository).save(product);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testUpdateById_when_productIdUnreconized() {
		Long productId = 1L;
		
		when(productRepository.existsById(productId)).thenReturn(false);
		
		productService.updateById(productId, null);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testUpdateById_when_categoryIdUnreconized() {
		Long productId = 1L;
		
		when(productRepository.existsById(productId)).thenReturn(true);
		
		Set<Long> categoriesIds = new HashSet<>();
		categoriesIds.add(1L);
		
		Product product = mock(Product.class);
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getProduct()).thenReturn(product);
		when(productDto.getCategoriesIds()).thenReturn(categoriesIds);
		
		when(productCategoryRepository.findAllById(categoriesIds)).thenReturn(Collections.emptyList());
		
		productService.updateById(productId, productDto);
	}
	
	@Test
	public void testUpdateById_when_categoryIdReconized() {
		Long productId = 1L;
		Long productCategoryId = 1L;
		
		when(productRepository.existsById(productId)).thenReturn(true);
		
		Set<Long> categoriesIds = new HashSet<>();
		categoriesIds.add(productCategoryId);
		
		Product product = mock(Product.class);
		
		CreateProductDto productDto = mock(CreateProductDto.class);
		when(productDto.getProduct()).thenReturn(product);
		when(productDto.getCategoriesIds()).thenReturn(categoriesIds);
		
		ProductCategory productCategory = mock(ProductCategory.class);
		when(productCategory.getId()).thenReturn(productCategoryId);
		List<ProductCategory> categories = Arrays.asList(productCategory);
		
		when(productCategoryRepository.findAllById(categoriesIds)).thenReturn(categories);
		
		productService.updateById(productId, productDto);
		
		verify(product).setId(productId);
		verify(product).setCategories(categories);
		verify(productRepository).save(product);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testFindById_when_idUnreconized() {
		Long id = 1L;
		
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		
		productService.findById(id);
	}
	
	@Test
	public void testFindById_when_idReconized() {
		Long id = 1L;
		
		Product product = mock(Product.class);
		
		when(productRepository.findById(id)).thenReturn(Optional.of(product));
		
		assertThat(productService.findById(id)).isEqualTo(product);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteById_when_idUnreconized() {
		Long id = 1L;
		
		when(productRepository.existsById(id)).thenReturn(false);
		
		productService.deleteById(id);
	}
	
	@Test
	public void testDeleteById_when_idReconized() {
		Long id = 1L;
		
		when(productRepository.existsById(id)).thenReturn(true);
		
		productService.deleteById(id);
		
		verify(productRepository).deleteById(id);
	}
	
}
