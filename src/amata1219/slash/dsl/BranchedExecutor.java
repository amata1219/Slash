package amata1219.slash.dsl;

import java.util.List;
import java.util.Map;

public class BranchedExecutor {
	
	private final Map<String, ContextualExecutor> branches;
	private final Maybe<ContextualExecutor> whenArgumentInsufficient;
	private final Maybe<ContextualExecutor> whenBranchNotFound;
	
	public BranchedExecutor(
		Map<String, ContextualExecutor> branches,
		Maybe<ContextualExecutor> whenArgumentInsufficient,
		Maybe<ContextualExecutor> whenBranchNotFound
	){
		this.branches = branches;
		this.whenArgumentInsufficient = whenArgumentInsufficient;
		this.whenBranchNotFound = whenBranchNotFound;
	}
	
	public void executeWith(RawCommandContext rawContext){
		List<String> args= rawContext.arguments;
		if(args.isEmpty()) {
			executeOptionally(rawContext, whenArgumentInsufficient);
			return;
		}
		
		ContextualExecutor branch = branches.get(args.get(0));
		if(branch == null){
			executeOptionally(rawContext, whenBranchNotFound);
			return;
		}
		
		RawCommandContext argumentShiftedContext = new RawCommandContext(rawContext.sender, rawContext.command, args.subList(1, args.size()));
		
		branch.executeWith(argumentShiftedContext);
	}
	
	private void executeOptionally(RawCommandContext rawContext, Maybe<ContextualExecutor> executor){
		executor.apply(e -> e.executeWith(rawContext));
	}

}
