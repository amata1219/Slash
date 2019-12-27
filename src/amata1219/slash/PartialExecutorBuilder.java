package amata1219.slash;

import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.bukkit.command.CommandSender;

import amata1219.slash.monad.Either;
import static amata1219.slash.monad.Either.*;
import amata1219.slash.parser.Parser;

public class PartialExecutorBuilder<S extends CommandSender> {
	
	public final Function<CommandSender, Either<CharSequence, S>> senderSafeCaster;
	public final BiFunction<S, List<String>, Either<CharSequence, PartiallyParsedArguments>> parser;
	public final TriFunction<S, ParsedArguments, Queue<String>, CharSequence> partialExecution;
	
	public PartialExecutorBuilder(
		Function<CommandSender, Either<CharSequence, S>> senderSafeCaster,
		BiFunction<S, List<String>, Either<CharSequence, PartiallyParsedArguments>> parser,
		TriFunction<S, ParsedArguments, Queue<String>, CharSequence> partialExecution
	){
		this.senderSafeCaster = senderSafeCaster;
		this.parser = parser;
		this.partialExecution = partialExecution;
	}
	
	public PartialExecutorBuilder<S> parsers(CharSequence onMissingArguments, Parser<?>... parsers){
		
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
	
}
