package desafiotecnicohotmart.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ProductsSearchSortTypeTest {

	@Test
	public void testValueByQueryParam_when_queryParamUnrecognized() {
		assertThat(ProductsSearchSortType.valueByQueryParam("test")).isEmpty();
	}
	
	@Test
	public void testValueByQueryParam_when_queryParamRecognized() {
		ProductsSearchSortType sortType = ProductsSearchSortType.CATEGORY_DESC;
		assertThat(ProductsSearchSortType.valueByQueryParam(sortType.getQueryParam())).contains(sortType);
	}
	
}
