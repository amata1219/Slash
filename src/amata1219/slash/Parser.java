package amata1219.slash;

import java.util.function.Predicate;

public interface Parser<T> {
	
	Either<CharSequence, T> parse(String argment);
	
	default Parser<T> then(Predicate<T> condition, CharSequence error){
		return x -> parse(x).flatMap(y -> condition.test(y) ? Either.success(y) : Either.failure(error));
	}

}
