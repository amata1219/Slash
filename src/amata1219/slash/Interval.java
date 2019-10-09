package amata1219.slash;

public class Interval<N extends Number & Comparable<N>> {
	
	public final EndPoint<N> lower, upper;
	
	public static <N extends Number & Comparable<N>> EndPoint<N> OpenEnd(N x){
		return new EndPoint<>(x, false);
	}
	
	public static <N extends Number & Comparable<N>> EndPoint<N> ClosedEnd(N x){
		return new EndPoint<>(x, true);
	}
	
	public static <N extends Number & Comparable<N>> Interval<N> Range(N startInclusive, N endExclusive){
		return Range(ClosedEnd(startInclusive), OpenEnd(endExclusive));
	}
	
	public static <N extends Number & Comparable<N>> Interval<N> RangeClosed(N startInclusive, N endInclusive){
		return Range(ClosedEnd(startInclusive), ClosedEnd(endInclusive));
	}
	
	public static <N extends Number & Comparable<N>> Interval<N> Range(EndPoint<N> lower, EndPoint<N> upper){
		if(lower.greater(upper.x) || upper.lesser(lower.x)) throw new IllegalArgumentException("Range can not be empty");
		return new Interval<>(lower, upper);
	}
	
	private Interval(EndPoint<N> startInclusive, EndPoint<N> endExclusive){
		this.lower = startInclusive;
		this.upper = endExclusive;
	}
	
	public boolean contains(N x){
		return lower.lesser(x) && upper.greater(x);
	}
	
	public static class EndPoint<T extends Number & Comparable<T>> {
		
		public final T x;
		public final boolean containsEqualElement;
		
		private EndPoint(T x, boolean containsEqualElement){
			this.x = x;
			this.containsEqualElement = containsEqualElement;
		}
		
		public boolean greater(T x){
			int result = this.x.compareTo(x);
			return containsEqualElement ? result >= 0 : result > 0;
		}
		
		public boolean lesser(T x){
			int result = this.x.compareTo(x);
			return containsEqualElement ? result <= 0 : result < 0;
		}
	}

}
