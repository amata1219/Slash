package amata1219.slash;

import static amata1219.slash.Command.*;
import static amata1219.slash.Interval.*;
import static amata1219.slash.Matcher.*;

import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class Sample {
	
	public static void main(String[] $){
		new Sample().onCommand(new DummySender(), new ArgList(new String[]{"add", "1"}));
	}

	@SuppressWarnings("unchecked")
	public void onCommand(CommandSender sender, ArgList args){
		args.next(() -> "err1").match(
			Case("add", "+").label(() -> args.nextInt(() -> "err2").otherwise(Range(0, 10)::contains, () -> "err3").whenR(
				n -> add(sender, n)
			)),
			Case("sub", "-").label(() -> args.nextInt(() -> "err4").when(Range(Integer.MIN_VALUE, 0)::contains, () -> "err5").whenR(
				n -> sub(sender, n)
			)),
			Case(String.class, s -> s.equalsIgnoreCase("execute")).label(() -> args.next(() -> "").flatBind(
				s -> args.next(() -> "err6").whenR(
				t -> execute(s, t)
			))),
			Else(() -> Error("err7"))
		).whenE(sender::sendMessage);
		
	}
	
	public void add(CommandSender sender, int n){
		System.out.println("add: " + n);
	}
	
	public void sub(CommandSender sender, int n){
		System.out.println("sub: " + n);
	}
	
	public void execute(String s, String t){
		System.out.println("exe: " + s + " : " + t);
	}
	
	public static class DummySender implements CommandSender {

		@Override
		public PermissionAttachment addAttachment(Plugin arg0) {
			return null;
		}

		@Override
		public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
			return null;
		}

		@Override
		public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
			return null;
		}

		@Override
		public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
			return null;
		}

		@Override
		public Set<PermissionAttachmentInfo> getEffectivePermissions() {
			return null;
		}

		@Override
		public boolean hasPermission(String arg0) {
			return false;
		}

		@Override
		public boolean hasPermission(Permission arg0) {
			return false;
		}

		@Override
		public boolean isPermissionSet(String arg0) {
			return false;
		}

		@Override
		public boolean isPermissionSet(Permission arg0) {
			return false;
		}

		@Override
		public void recalculatePermissions() {
		}

		@Override
		public void removeAttachment(PermissionAttachment arg0) {
		}

		@Override
		public boolean isOp() {
			return false;
		}

		@Override
		public void setOp(boolean arg0) {
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public Server getServer() {
			return null;
		}

		@Override
		public void sendMessage(String arg0) {
		}

		@Override
		public void sendMessage(String[] arg0) {
		}

		@Override
		public Spigot spigot() {
			return null;
		}
		
		
	}
	
}
