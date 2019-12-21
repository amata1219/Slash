package amata1219.old.slash;

import org.bukkit.plugin.java.JavaPlugin;

public class Slash extends JavaPlugin {
	
	private static Slash plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public static Slash plugin(){
		return plugin;
	}
	
}
