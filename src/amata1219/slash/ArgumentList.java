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

import amata1219.slash.dsl.CommandMonad;

public class ArgumentList {

	private final Queue<String> remainings;

	public ArgumentList(String[] args){
		remainings = new LinkedList<>(Arrays.asList(args));
	}

	public int size(){
		return remainings.size();
	}
	
	public <R> CommandMonad<R> next(Function<String, R> converter, Supplier<String> error){
		R result = null;
		try{
			result = converter.apply(remainings.poll());
		}catch(Exception e){
			
		}
		return CommandMonad.unit(result, error);
	}
	
	public CommandMonad<String> next(Supplier<String> error){
		return next(Function.identity(), error);
	}

	public CommandMonad<Boolean> nextBool(Supplier<String> error){
		return next(Boolean::valueOf, error);
	}

	public CommandMonad<Character> nextChar(Supplier<String> error){
		return next(s -> s.length() == 1 ? s.charAt(0) : null, error);
	}

	public CommandMonad<Byte> nextByte(Supplier<String> error){
		return next(Byte::valueOf, error);
	}

	public CommandMonad<Short> nextShort(Supplier<String> error){
		return next(Short::valueOf, error);
	}

	public CommandMonad<Integer> nextInt(Supplier<String> error){
		return next(Integer::valueOf, error);
	}

	public CommandMonad<Long> nextLong(Supplier<String> error){
		return next(Long::valueOf, error);
	}

	public CommandMonad<Float> nextFloat(Supplier<String> error){
		return next(Float::valueOf, error);
	}

	public CommandMonad<Double> nextDouble(Supplier<String> error){
		return next(Double::valueOf, error);
	}
	
	public <T> CommandMonad<T> range(int count, Function<Collection<String>, CommandMonad<T>> action){
		Collection<String> ranged = IntStream.range(0, count)
				.mapToObj(i -> remainings.poll())
				.collect(Collectors.toList());
		return action.apply(ranged);
	}
	
	public CommandMonad<String> join(int count,  Supplier<String> error){
		return range(count, ranged -> ranged.isEmpty() ? CommandMonad.Error(error.get()) : CommandMonad.Result(Joiner.on(' ').join(ranged)));
	}
	
	public ArgumentList skip(int count){
		for(int i = Math.min(count, size()); i > 0; i--) remainings.remove();
		return this;
	}

}
