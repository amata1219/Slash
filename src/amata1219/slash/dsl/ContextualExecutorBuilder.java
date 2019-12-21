package amata1219.slash.dsl;

import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

import javax.swing.text.html.Option;

import org.bukkit.command.CommandSender;

public class ContextualExecutorBuilder<S extends CommandSender> {
	
	public final BiFunction<S, RawCommandContext, Option<PartiallyParsedArguments>> argumentsParser;
	
	public ContextualExecutorBuilder(Maybe<PartiallyParsedArguments> argumentsParser){
		this.argumentsParser = argumentsParser;
	}
	
	public ContextualExecutorBuilder parsers(Parser<?> parsers, Message onMissingArguments){
		
	}
	
	private Either<Message, PartiallyParsedArguments> parse(Queue<Parser<?>> remainingParsers, Queue<String> remainingArguments, List<Object> accumulator, Message onMissingArguments){
		if(remainingParsers.isEmpty()) return Either.Success(new PartiallyParsedArguments(accumulator, remainingArguments));
		if(remainingArguments.isEmpty()) return Either.Failure(onMissingArguments);
		Parser<?> parser = remainingParsers.poll();
		String arg = remainingArguments.poll();
		return parser.parse(arg).flatMap(result -> {
			accumulator.add(result);
			return parse(remainingParsers, remainingArguments, accumulator, onMissingArguments);
		});
	}

}
