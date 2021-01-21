package desafiotecnicohotmart.model;

import org.springframework.data.domain.Sort.Direction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ProductsSearchSortType {

	NAME_ASC("name", Direction.ASC),
	NAME_DESC("name", Direction.DESC),
	CATEGORY_ASC("category.name", Direction.ASC),
	CATEGORY_DESC("category.name", Direction.DESC),
	SCORE_ASC("lastScore.score", Direction.ASC),
	SCORE_DESC("lastScore.score", Direction.DESC);
	
	private final String propertyName;
	private final Direction direction;
	
}
