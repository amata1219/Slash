package amata1219.slash;

import java.util.HashMap;

import org.bukkit.command.CommandSender;

public class BranchedExecutorBuilder<S extends CommandSender> {
	
	public static <S extends CommandSender> BranchedExecutorBuilder<S> builder(){
		return new BranchedExecutorBuilder<>();
	}
	
	private String whenArgumentsInsufficient, whenBranchNotFound;
	private HashMap<String, PartialExecutor<S>> branches = new HashMap<>();
	
	public BranchedExecutorBuilder<S> whenArgumentsInsufficient(CharSequence error){
		whenArgumentsInsufficient = error.toString();
		return this;
	}
	
	public BranchedExecutorBuilder<S> whenBranchNotFound(CharSequence error){
		whenBranchNotFound = error.toString();
		return this;
	}
	
	public BranchedExecutorBuilder<S> branch(PartialExecutor<S> executor, String argument, String... aliases){
		branches.put(argument, executor);
		for(String alias : aliases) branches.put(alias, executor);
		return this;
	}
	
	public PartialExecutor<S> build(){
		return (sender, arguments) -> {
			if(arguments.isEmpty()) sender.sendMessage(whenArgumentsInsufficient);
			else if(branches.containsKey(arguments.element())) branches.get(arguments.poll()).execute(sender, arguments);
			else sender.sendMessage(whenBranchNotFound);
		};
	}

}
