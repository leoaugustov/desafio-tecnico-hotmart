package desafiotecnicohotmart.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

public class ProductTest {

	@Test(expected = NullPointerException.class)
	public void testGetDaysOfExistence_when_nowNull() {
		LocalDateTime creationDate = LocalDateTime.parse("2020-12-01T12:30:10");
		
		Product product = spy(Product.class);
		doReturn(creationDate).when(product).getCreationDate();
		
		product.getDaysOfExistence(null);
	}
	
	@Test
	public void testGetDaysOfExistence_when_creationDateNull() {
		Product product = new Product();
		assertThat(product.getDaysOfExistence(null)).isZero();
	}
	
	@Test
	public void testGetDaysOfExistence_when_creationDateNotNull() {
		LocalDateTime creationDate = LocalDateTime.parse("2020-12-01T12:30:10");
		LocalDate now = LocalDate.parse("2021-01-20");
		
		Product product = spy(Product.class);
		doReturn(creationDate).when(product).getCreationDate();
		
		assertThat(product.getDaysOfExistence(now)).isEqualTo(50);
	}
	
}
