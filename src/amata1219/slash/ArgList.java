package amata1219.slash;

import static amata1219.slash.Command.*;
import static amata1219.slash.Interval.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Joiner;

public class ArgList {

	private final String[] args;
	private int index;

	public ArgList(String[] args){
		this.args = args;
	}

	public int size(){
		return args.length;
	}
	
	public <R> Command<R> next(Function<String, R> converter, Supplier<String> error){
		R result = null;
		try{
			result = converter.apply(index < args.length ? args[index++] : null);
		}catch(Exception e){
			
		}
		return result != null ? Result(result) : Error(error.get());
	}

	public Command<String> next(Supplier<String> error){
		return next(Function.identity(), error);
	}

	public Command<Boolean> nextBool(Supplier<String> error){
		return next(Boolean::valueOf, error);
	}

	public Command<Character> nextChar(Supplier<String> error){
		return next(s -> s.length() == 1 ? s.charAt(0) : null, error);
	}

	public Command<Byte> nextByte(Supplier<String> error){
		return next(Byte::valueOf, error);
	}

	public Command<Short> nextShort(Supplier<String> error){
		return next(Short::valueOf, error);
	}

	public Command<Integer> nextInt(Supplier<String> error){
		return next(Integer::valueOf, error);
	}

	public Command<Long> nextLong(Supplier<String> error){
		return next(Long::valueOf, error);
	}

	public Command<Float> nextFloat(Supplier<String> error){
		return next(Float::valueOf, error);
	}

	public Command<Double> nextDouble(Supplier<String> error){
		return next(Double::valueOf, error);
	}
	
	public <T> Command<T> range(Interval<Integer> range, Function<Collection<String>, Command<T>> action){
		String[] ranged = Arrays.copyOfRange(args, range.lower.x, index = range.upper.x);
		return action.apply(Arrays.asList(ranged));
	}
	
	public <T> Command<T> range(int endInclusive, Function<Collection<String>, Command<T>> action){
		return range(Range(index + 1, endInclusive), action);
	}
	
	public Command<String> join(int endInclusive, Supplier<String> error){
		return range(endInclusive, ranged -> ranged.isEmpty() ? Error(error.get()) : Result(Joiner.on(' ').join(ranged)));
	}
	
	public ArgList skip(int count){
		index += count;
		return this;
	}

}
