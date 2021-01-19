package desafiotecnicohotmart.error.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(Class<?> entityClass, Long searchedId) {
		super(buildPartialMessage(entityClass) + " " + searchedId);
	}
	
	public EntityNotFoundException(Class<?> entityClass, Iterable<Long> searchedIds) {
		super(buildPartialMessage(entityClass) + "s " + searchedIds);
	}
	
	public static String buildPartialMessage(Class<?> entityClass) {
		return "No " + entityClass.getSimpleName().toLowerCase() + " found for id";
	}
	
}
