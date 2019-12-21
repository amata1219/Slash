package amata1219.slash.dsl;

import static amata1219.slash.dsl.Either.*;

import java.util.function.Function;

public interface Parser<T>{
	
	static Parser<String> identity(){
		return x -> Success(x);
	}
	
	static Parser<Integer> i32(Message error){
		return convert(error, Integer::valueOf);
	}
	
	static Parser<Long> i64(Message error){
		return convert(error, Long::valueOf);
	}
	
	static Parser<Float> f32(Message error){
		return convert(error, Float::valueOf);
	}
	
	static Parser<Double> f64(Message error){
		return convert(error, Double::valueOf);
	}
	
	static <T> Parser<T> convert(Message error, Function<String, T> converter){
		return x -> {
			try{
				return Success(converter.apply(x));
			}catch(Exception e){
				return Failure(error);
			}
		};
	}
	
	Either<Message, T> parse(String arg);

}
