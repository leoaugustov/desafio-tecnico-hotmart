package desafiotecnicohotmart.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import desafiotecnicohotmart.model.Product;
import desafiotecnicohotmart.repository.ProductRepository;
import desafiotecnicohotmart.repository.ProductScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class NewsCountService {

	private final NewsApiService newsApi;
	private final ProductRepository productRepository;
	private final ProductScoreRepository productScoreRepository;
	
	/**
	 * Finds all active products with the respective categories, counts the main headlines of 
	 * the day for each distinct category and updates the product score in the database.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void countAndUpdateNews() {
		LocalDate today = LocalDate.now();
		Page<Product> productPage;
		int pageNumber = 0;
		
		Map<String, Integer> newsAmountByCategoryName = new HashMap<>();
		do {
			productPage = productRepository.findWithCategoryAllByActiveTrue(PageRequest.of(pageNumber, 20));
			Set<String> categories = productPage.stream()
					.map(product -> product.getCategory().getName())
					.collect(Collectors.toSet());
			
			for(String categoryName : categories) {
				if(newsAmountByCategoryName.containsKey(categoryName)) {
					continue;
				}
				
				int newsAmount = 0;
				try {
					newsAmount = newsApi.countTopHeadlines(categoryName, today.atStartOfDay().plusHours(3));
				} catch (Exception e) {
					log.error("Error when trying to count the top headlines of the day for product category '{}'", categoryName, e);
				}
				newsAmountByCategoryName.put(categoryName, newsAmount);
			}
			
			productPage.forEach(product -> {
				int z = newsAmountByCategoryName.get(product.getCategory().getName());
				if(z != 0) {
					productScoreRepository.updateZByProductIdAndReferenceDate(z, product.getId(), today);
				}
				
			});
			
			pageNumber++;
		}while(productPage.hasNext());
	}
	
}
