package desafiotecnicohotmart.model;

import java.util.Optional;

import org.springframework.data.domain.Sort.Direction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ProductsSearchSortType {

	NAME_ASC("name.asc", "name", Direction.ASC),
	NAME_DESC("name.desc", "name", Direction.DESC),
	CATEGORY_ASC("category.asc", "category.name", Direction.ASC),
	CATEGORY_DESC("category.desc", "category.name", Direction.DESC),
	SCORE_ASC("score.asc", "lastScore.score", Direction.ASC),
	SCORE_DESC("score.desc", "lastScore.score", Direction.DESC);
	
	private final String queryParam;
	private final String propertyName;
	private final Direction direction;
	
	public static Optional<ProductsSearchSortType> valueByQueryParam(String queryParam) {
		for(ProductsSearchSortType sortType : values()) {
			if(sortType.getQueryParam().equals(queryParam)) {
				return Optional.of(sortType);
			}
		}
		return Optional.empty();
	}
	
}
