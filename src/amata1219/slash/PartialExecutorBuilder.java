package amata1219.slash;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

import amata1219.slash.monad.Either;
import static amata1219.slash.monad.Either.*;
import amata1219.slash.parser.Parser;

public class PartialExecutorBuilder<S extends CommandSender> {
	
	private Function<CommandSender, Either<CharSequence, S>> senderCaster;
	private BiFunction<S, List<String>, Either<CharSequence, PartiallyParsedArguments>> parser;
	private TriFunction<S, ParsedArguments, Queue<String>, CharSequence> execution;
	
	@SuppressWarnings("unchecked")
	public PartialExecutorBuilder<S> castSender(CharSequence error){
		Function<CommandSender, Either<CharSequence, S>> senderCaster = sender -> {
			try{
				return success((S) sender);
			}catch(Exception e){
				return failure(error);
			}
		};
		this.senderCaster = senderCaster;
		return this;
	}
	
	public PartialExecutorBuilder<S> parsers(CharSequence onMissingArguments, Parser<?>... parsers){
		BiFunction<S, List<String>, Either<CharSequence, PartiallyParsedArguments>> combinedParser = (sender, arguments) -> parse(
			Lists.newLinkedList(Arrays.asList(parsers)),
			Lists.newLinkedList(arguments),
			Lists.newArrayList(),
			onMissingArguments
		);
		this.parser = combinedParser;
		return this;
	}
	
	private Either<CharSequence, PartiallyParsedArguments> parse(
		Queue<Parser<?>> remainingParsers,
		Queue<String> remainingArguments,
		List<Object> parsedArgumentAccumulator,
		CharSequence onMissingArguments
	){
		if(remainingParsers.isEmpty()) return success(new PartiallyParsedArguments(parsedArgumentAccumulator, remainingArguments));
		else if(remainingArguments.isEmpty()) return failure(onMissingArguments);
		Parser<?> nextParser = remainingParsers.poll();
		String nextArgument = remainingArguments.poll();
		return nextParser.parse(nextArgument).flatMap(parsedArgument -> {
			parsedArgumentAccumulator.add(parsedArgument);
			return parse(remainingParsers, remainingArguments, parsedArgumentAccumulator, onMissingArguments);
		});
	}
	
	public PartialExecutorBuilder<S> execution(TriFunction<S, ParsedArguments, Queue<String>, CharSequence> execution){
		this.execution = execution;
		return this;
	}
	
	public PartialExecutor<S> build(){
		return null;
	}
	
}
