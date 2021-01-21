package desafiotecnicohotmart.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import desafiotecnicohotmart.dto.CreateProductDto;
import desafiotecnicohotmart.model.ProductsSearchSortType;
import desafiotecnicohotmart.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductDto productDto) {
		return ResponseEntity.ok(productService.save(productDto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid CreateProductDto productDto) {
		return ResponseEntity.ok(productService.updateById(id, productDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deactivateProduct(@PathVariable("id") Long id) {
		productService.deactivateById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<?> listAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "7") int pageSize) {
		
		return ResponseEntity.ok(productService.findAll(page, pageSize));
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestParam(defaultValue = "") String q,
			@RequestParam(defaultValue = "NAME_ASC") ProductsSearchSortType sort,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int pageSize) {
		
		return ResponseEntity.ok(productService.search(q, sort, page, pageSize));
	}
	
}
