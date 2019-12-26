package amata1219.slash;

public class Text implements CharSequence {
	
	private static final String NULL = String.valueOf(Character.MIN_VALUE);
	
	public static Text of(String text){
		return new Text(colored(text));
	}
	
	public static String colored(String text){
		char[] chars = text.toCharArray();

		for(int i = 0; i < chars.length - 1; i++){
			char color = chars[i + 1];

			if(chars[i] != '&' || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(color) <= -1) continue;

			if(i > 0 && chars[i - 1] == '-') chars[i - 1] = Character.MIN_VALUE;

			chars[i] = '§';
			chars[i + 1] = Character.toLowerCase(color);

			if(i < chars.length - 2 && chars[i + 2] == '-'){
				chars[i + 2] = Character.MIN_VALUE;
				i += 2;
			}else{
				i++;
			}
		}
		
		return new String(chars).replace(NULL, "");
	}
	
	public String text;
	
	private Text(String text){
		this.text = text;
	}
	
	public Text format(Object... objects){
		text = String.format(text, objects);
		return this;
	}
	
	@Override
	public int length() {
		return text.length();
	}

	@Override
	public char charAt(int index) {
		return text.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return text.subSequence(start, end);
	}
	
}
