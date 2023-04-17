package geometry;

public class Vec3{
	/* Instance Variables */
	public float x, y, z;
	
	/* Constructors */
	public Vec3() {}
	public Vec3(float xx) {x = xx; y = xx; z = xx;}
	public Vec3(float xx, float yy, float zz) {x = xx; y = yy; z = zz;}
	public Vec3(Vec3 o) {x = o.x; y = o.y; z = o.z;}
	/* Methods */
	
	//Operations
	public Vec3 add  (Vec3 v) {return new Vec3(x + v.x, y + v.y, z + v.z);}
	public Vec3 sub  (Vec3 v) {return new Vec3(x - v.x, y - v.y, z - v.z);}
	public Vec3 div  (Vec3 v) {return new Vec3(x / v.x, y / v.y, z / v.z);}
	public Vec3 mult (Vec3 v) {return new Vec3(x * v.x, y * v.y, z * v.z);}
	
	public Vec3 add (float e){return add (new Vec3(e));}
	public Vec3 sub (float e){return sub (new Vec3(e));}
	public Vec3 div (float e){return div (new Vec3(e));}
	public Vec3 mult(float e){return mult(new Vec3(e));}
	
	//Calculates the dot product of 2 vectors
	public float dot(Vec3 v) {return (x * v.x + y * v.y +  z * v.z);}
	
	//Calculates the cross product
	public Vec3 cross(Vec3 v){
		return new Vec3(y * v.z - z * v.y, 
						z * v.x - x * v.z, 
						x * v.y - y * v.x);
	}
	
	//Measurments of a vector
	public float length() {return (float) Math.sqrt(norm());}
	public float norm()   {return dot(this);}
	
	//Normalize the vector
	public void normalize() {
		float n = length();
		if(n > 0) {
			float factor = 1 / n;
			x = x * factor;
			y = y * factor;
			z = z * factor;
		}
	}
	
	public String toString() {return "[" + x + "," + y + "," + z + "]";}
	
}
