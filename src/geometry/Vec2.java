package geometry;

public class Vec2{
	/* Instance Variables */
	public float x;
	public float y;
	
	/* Constructors */
	
	public Vec2() {}
	public Vec2(float xx) {x = xx; y = xx;}
	public Vec2(float xx, float yy) {x = xx; y = yy;}
	
	/* Methods */
	public Vec2 add  (Vec2 v)   {return new Vec2(x + v.x, y + v.y);} 
	public Vec2 div  (Vec2 v)   {return new Vec2(x / v.x, y / v.y);} 
	public Vec2 mult (Vec2 v)   {return new Vec2(x * v.x, y * v.y);} 
	public Vec2 mult (float v) {return new Vec2(x *   v, y *   v);} 
	public String toString() {return "[" + x + "," + y + "]";}
}

