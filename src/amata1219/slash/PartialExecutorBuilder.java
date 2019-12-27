package amata1219.slash;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import amata1219.slash.monad.Either;
import static amata1219.slash.monad.Either.*;

import amata1219.slash.parser.Parser;

public class PartialExecutorBuilder<S extends CommandSender> {
	
	public static PartialExecutorBuilder<CommandSender> builder(){
		return new PartialExecutorBuilder<>().castSender(null);
	}
	
	public static PartialExecutorBuilder<Player> playerCommandBuilder(){
		return new PartialExecutorBuilder<Player>().castSender(Text.of("&c-このコマンドはゲーム内で実行して下さい。"));
	}
	
	private BiConsumer<S, CharSequence> messenger = (sender, message) -> sender.sendMessage(message.toString());
	private Function<CommandSender, Either<CharSequence, S>> senderCaster;
	private BiFunction<S, Queue<String>, Either<CharSequence, PartiallyParsedArguments>> parser = (x, y) -> success(new PartiallyParsedArguments(Lists.newArrayList(), Lists.newLinkedList()));
	private TriFunction<S, ParsedArguments, Queue<String>, CharSequence> execution;
	
	public PartialExecutorBuilder<S> messenger(BiConsumer<S, CharSequence> messenger){
		this.messenger = messenger;
		return this;
	}
	
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
	
	public PartialExecutorBuilder<S> parsers(CharSequence argumentsMissingError, Parser<?>... parsers){
		BiFunction<S, Queue<String>, Either<CharSequence, PartiallyParsedArguments>> combinedParser = (sender, arguments) -> parse(
			Lists.newLinkedList(Arrays.asList(parsers)),
			arguments,
			Lists.newArrayList(),
			argumentsMissingError
		);
		this.parser = combinedParser;
		return this;
	}
	
	private Either<CharSequence, PartiallyParsedArguments> parse(
		Queue<Parser<?>> remainingParsers,
		Queue<String> remainingArguments,
		List<Object> parsedArgumentAccumulator,
		CharSequence argumentsMissingError
	){
		if(remainingParsers.isEmpty()) return success(new PartiallyParsedArguments(parsedArgumentAccumulator, remainingArguments));
		else if(remainingArguments.isEmpty()) return failure(argumentsMissingError);
		Parser<?> nextParser = remainingParsers.poll();
		String nextArgument = remainingArguments.poll();
		return nextParser.parse(nextArgument).flatMap(parsedArgument -> {
			parsedArgumentAccumulator.add(parsedArgument);
			return parse(remainingParsers, remainingArguments, parsedArgumentAccumulator, argumentsMissingError);
		});
	}
	
	public PartialExecutorBuilder<S> execution(TriFunction<S, ParsedArguments, Queue<String>, CharSequence> execution){
		this.execution = execution;
		return this;
	}
	
	public PartialExecutor<S> build(){
		return (sender, arguments) -> senderCaster.apply(sender).flatMap(
				castedSender -> parser.apply(castedSender, arguments).flatMap(
				partiallyParsedArguments -> failure(execution.apply(castedSender, new ParsedArguments(Lists.newLinkedList(partiallyParsedArguments.parsed)), partiallyParsedArguments.unparsed))
				.onFailure(message -> messenger.accept(castedSender, message))
			));
	}
	
}
