package desafiotecnicohotmart.service;

import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import desafiotecnicohotmart.model.Product;
import desafiotecnicohotmart.model.ProductScore;
import desafiotecnicohotmart.projection.ProductSalesAmountProjection;
import desafiotecnicohotmart.repository.ProductRepository;
import desafiotecnicohotmart.repository.ProductScoreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductScoreCalculationService {

	private final ProductScoreRepository productScoreRepository;
	private final ProductRepository productRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public void calculateAndPersistScores() {
		LocalDate now = LocalDate.now();
		
		Map<Long, Integer> salesAmountByProductId = productRepository.findAllWithSalesAmount().stream()
				.collect(toMap(ProductSalesAmountProjection::getProductId, ProductSalesAmountProjection::getSalesAmount));
		
		Map<Long, Double> salesAverageRatingByProductId = productRepository.findAllWithSalesAverageRating().stream()
				.collect(toMap(p -> p.getProductId(), p -> p.getSalesAverageRating()));
		
		Page<Product> productPage;
		int pageNumber = 0;
		do {
			productPage = productRepository.findWithCategoryAllByActiveTrue(PageRequest.of(pageNumber, 20));
			
			List<ProductScore> scores = new ArrayList<>();
			for(Product product : productPage) {
				double salesAverageRating = salesAverageRatingByProductId.getOrDefault(product.getId(), 0.0);
				int salesAmount = salesAmountByProductId.getOrDefault(product.getId(), 0);
				int daysOfExistence = (int) product.getDaysOfExistence(now);
				
				ProductScore productScore = new ProductScore(salesAverageRating, salesAmount, daysOfExistence, product);
				scores.add(productScore);
			}
			productScoreRepository.saveAll(scores);
			pageNumber++;
		}while(productPage.hasNext());
	}
	
}
