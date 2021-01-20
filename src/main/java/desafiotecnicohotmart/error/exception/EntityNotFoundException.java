package desafiotecnicohotmart.error.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(Class<?> entityClass, Long searchedId) {
		super("No " + entityClass.getSimpleName().toLowerCase() + " found for id " + searchedId);
	}
	
}
