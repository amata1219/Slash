package amata1219.slash;

public class Interval<N extends Number & Comparable<N>> {
	
	public static <N extends Number & Comparable<N>> Interval<N> of(End<N> lesser, End<N> greater){
		if(!lesser.lesserThan(greater.x) || !greater.greaterThan(lesser.x)) throw new IllegalArgumentException("Interval can not be empty");
		return new Interval<>(lesser, greater);
	}
	
	public final End<N> lesser, greater;
	
	private Interval(End<N> lesser, End<N> greater){
		this.lesser = lesser;
		this.greater = greater;
	}
	
	public boolean contains(N x){
		return lesser.lesserThan(x) && greater.greaterThan(x);
	}
	
	public static class End<N extends Number & Comparable<N>> {
		
		public static <N extends Number & Comparable<N>> End<N> open(N x){
			return new End<>(x, false);
		}
		
		public static <N extends Number & Comparable<N>> End<N> closed(N x){
			return new End<>(x, true);
		}
		
		public final N x;
		public final boolean containsEqualElement;
		
		private End(N x, boolean containsEqualElement){
			this.x = x;
			this.containsEqualElement = containsEqualElement;
		}
		
		private boolean greaterThan(N x){
			int res = this.x.compareTo(x);
			return containsEqualElement ? res >= 0 : res > 0;
		}
		
		private boolean lesserThan(N x){
			int res = this.x.compareTo(x);
			return containsEqualElement ? res <= 0 : res < 0;
		}
		
	}

}
