package amata1219.old.slash.dsl;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Joiner;

import amata1219.old.slash.monad.Either;

public class ArgumentList<F> {

	private final Queue<String> args;

	public ArgumentList(String[] args){
		this.args = new LinkedList<>(Arrays.asList(args));
	}
	
	public boolean isEmpty(){
		return args.isEmpty();
	}

	public int length(){
		return args.size();
	}
	
	public <R> Either<F, R> next(Function<String, R> converter, Supplier<F> error){
		try{
			return Either.Success(converter.apply(args.poll()));
		}catch(Exception e){
			return Either.Failure(error.get());
		}
	}
	
	public Either<F, String> next(Supplier<F> error){
		return next(Function.identity(), error);
	}
	
	public Either<F, String> nextOr(Supplier<String> other){
		if(isEmpty()) args.add(other.get());
		return next(null);
	}

	public Either<F, Boolean> nextBoolean(Supplier<F> error){
		return next(Boolean::valueOf, error);
	}

	public Either<F, Character> nextChar(Supplier<F> error){
		return next(s -> s.length() == 1 ? s.charAt(0) : null, error);
	}

	public Either<F, Byte> nextByte(Supplier<F> error){
		return next(Byte::valueOf, error);
	}

	public Either<F, Short> nextShort(Supplier<F> error){
		return next(Short::valueOf, error);
	}

	public Either<F, Integer> nextInt(Supplier<F> error){
		return next(Integer::valueOf, error);
	}

	public Either<F, Long> nextLong(Supplier<F> error){
		return next(Long::valueOf, error);
	}

	public Either<F, Float> nextFloat(Supplier<F> error){
		return next(Float::valueOf, error);
	}

	public Either<F, Double> nextDouble(Supplier<F> error){
		return next(Double::valueOf, error);
	}

	public <T> Either<F, T> range(int count, Function<Collection<String>, Either<F, T>> action){
		Collection<String> ranged = IntStream.range(0, count)
				.mapToObj(i -> args.poll())
				.collect(Collectors.toList());
		return action.apply(ranged);
	}
	
	public Either<F, String> join(int count,  Supplier<F> error){
		return range(count, ranged -> ranged.isEmpty() ? Either.Failure(error.get()) : Either.Success(Joiner.on(' ').join(ranged)));
	}
	
	public ArgumentList<F> skip(int count){
		for(int i = Math.min(count, length()); i > 0; i--) args.remove();
		return this;
	}

}
