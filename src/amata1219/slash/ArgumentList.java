package amata1219.slash;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Joiner;

import amata1219.slash.monad.Either;

public class ArgumentList {

	private final Queue<String> remainings;

	public ArgumentList(String[] args){
		remainings = new LinkedList<>(Arrays.asList(args));
	}

	public int size(){
		return remainings.size();
	}
	
	public <R> Either<String, R> next(Function<String, R> converter, Supplier<String> error){
		try{
			return Either.Success(converter.apply(remainings.poll()));
		}catch(Exception e){
			return Either.Failure(error.get());
		}
	}
	
	public Either<String, String> next(Supplier<String> error){
		return next(Function.identity(), error);
	}

	public Either<String, Boolean> nextBool(Supplier<String> error){
		return next(Boolean::valueOf, error);
	}

	public Either<String, Character> nextChar(Supplier<String> error){
		return next(s -> s.length() == 1 ? s.charAt(0) : null, error);
	}

	public Either<String, Byte> nextByte(Supplier<String> error){
		return next(Byte::valueOf, error);
	}

	public Either<String, Short> nextShort(Supplier<String> error){
		return next(Short::valueOf, error);
	}

	public Either<String, Integer> nextInt(Supplier<String> error){
		return next(Integer::valueOf, error);
	}

	public Either<String, Long> nextLong(Supplier<String> error){
		return next(Long::valueOf, error);
	}

	public Either<String, Float> nextFloat(Supplier<String> error){
		return next(Float::valueOf, error);
	}

	public Either<String, Double> nextDouble(Supplier<String> error){
		return next(Double::valueOf, error);
	}

	public <T> Either<String, T> range(int count, Function<Collection<String>, Either<String, T>> action){
		Collection<String> ranged = IntStream.range(0, count)
				.mapToObj(i -> remainings.poll())
				.collect(Collectors.toList());
		return action.apply(ranged);
	}
	
	public Either<String, String> join(int count,  Supplier<String> error){
		return range(count, ranged -> ranged.isEmpty() ? Either.Failure(error.get()) : Either.Success(Joiner.on(' ').join(ranged)));
	}
	
	public ArgumentList skip(int count){
		for(int i = Math.min(count, size()); i > 0; i--) remainings.remove();
		return this;
	}

}
