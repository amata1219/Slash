package amata1219.slash.parser;

import java.util.function.Predicate;

import amata1219.slash.monad.Either;

public interface Parser<T> {
	
	Either<CharSequence, T> parse(String argment);
	
	default Parser<T> then(Predicate<T> condition, CharSequence error){
		return x -> parse(x).flatMap(y -> condition.test(y) ? Either.success(y) : Either.failure(error));
	}

}
