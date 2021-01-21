package desafiotecnicohotmart.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class ProductScoreTest {

	private static final double SALES_AVERAGE_RATING = 4.35;
	private static final int SALES_AMOUNT = 316;
	private static final int DAYS_OF_EXISTENCE = 243;
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorMethodWithParams_when_productNull() {
		new ProductScore(SALES_AVERAGE_RATING, SALES_AMOUNT, DAYS_OF_EXISTENCE, null);
	}

	@Test
	public void testConstructorMethodWithParams_when_daysOfExistenceIsZero() {
		Product product = mock(Product.class);
		ProductScore productScore = new ProductScore(SALES_AVERAGE_RATING, SALES_AMOUNT, 0, product);
		
		assertThat(productScore.getX()).isEqualTo(SALES_AVERAGE_RATING);
		assertThat(productScore.getY()).isZero();
		assertThat(productScore.getZ()).isZero();
		assertThat(productScore.getProduct()).isEqualTo(product);
	}
	
	@Test
	public void testConstructorMethodWithParams_when_productNotNull_daysOfExistenceIsNotZero() {
		Product product = mock(Product.class);
		ProductScore productScore = new ProductScore(SALES_AVERAGE_RATING, SALES_AMOUNT, DAYS_OF_EXISTENCE, product);
		
		assertThat(productScore.getX()).isEqualTo(SALES_AVERAGE_RATING);
		assertEquals(1.30041152, productScore.getY(), 0.00000001);
		assertThat(productScore.getZ()).isZero();
		assertThat(productScore.getProduct()).isEqualTo(product);
	}
	
}
