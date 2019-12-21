package amata1219.slash.dsl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ArgumentQueue {
	
	private final Queue<String> args;
	
	public ArgumentQueue(String[] args){
		this.args = new LinkedList<>(Arrays.asList(args));
	}

}
