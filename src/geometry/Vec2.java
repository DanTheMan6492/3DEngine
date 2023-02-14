package geometry;

public class Vec2<T extends Number> {
	/* Instance Variables */
	T x, y;
	
	/* Constructors */
	
	public Vec2() {}
	public Vec2(T x) {
		this.x = x;
		this.y = x;
	}
	public Vec2(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	/* Methods */
	
	//Whoever decided to not have operator overloading in java can go fuck themselves
	@SuppressWarnings("unchecked")
	public Vec2<T> add  (Vec2<T> v) {return new Vec2<T>((T) (Number) (x.doubleValue() + v.x.doubleValue()), (T) (Number) (y.doubleValue() + v.y.doubleValue()));} 
	@SuppressWarnings("unchecked")
	public Vec2<T> div  (Vec2<T> v) {return new Vec2<T>((T) (Number) (x.doubleValue() / v.x.doubleValue()), (T) (Number) (y.doubleValue() / v.y.doubleValue()));} 
	@SuppressWarnings("unchecked")
	public Vec2<T> mult (Vec2<T> v) {return new Vec2<T>((T) (Number) (x.doubleValue() * v.x.doubleValue()), (T) (Number) (y.doubleValue() * v.y.doubleValue()));} 
	@SuppressWarnings("unchecked")
	public Vec2<T> mult (T    v) {return new Vec2<T>((T) (Number) (x.doubleValue() *   v.doubleValue()), (T) (Number) (y.doubleValue() *   v.doubleValue()));} 
	public String toString() {return "[" + x + "," + y + "]";}
}

