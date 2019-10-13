package amata1219.slash.dsl;

import org.bukkit.command.CommandSender;

import amata1219.slash.ArgumentList;

public enum TestCommand implements Command {
	
	EXECUTOR(null),
	UNSPECIFIED_ARGUMENT_ERROR("");
	
	private final String m;
	
	private TestCommand(String m){
		this.m = m;
	}
	
	@Override
	public String message() {
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCommand(CommandSender sender, ArgumentList args) {
		args.next(UNSPECIFIED_ARGUMENT_ERROR).match(
			//Case("")
		);
	}

}
