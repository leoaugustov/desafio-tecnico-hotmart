package desafiotecnicohotmart.validation;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ProductSearchSortTypeValidator.class)
public @interface ProductSearchSortTypeConstraint {
    String message() default "Must match one of the following values: [name.asc, name.desc, category.asc, category.desc, score.asc, score.desc]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
