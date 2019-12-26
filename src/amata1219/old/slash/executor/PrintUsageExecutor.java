package amata1219.old.slash.executor;

import amata1219.old.slash.ContextualExecutor;
import amata1219.old.slash.contexts.RawCommandContext;

public class PrintUsageExecutor implements ContextualExecutor {
	
	public static final PrintUsageExecutor executor = new PrintUsageExecutor();

	@Override
	public void executeWith(RawCommandContext context) {
		context.sender.sendMessage(context.command.aliasUsed);
	}

}
