import geometry.*;

public class Driver {
	public static void main(String[] args) {
		Vec3 v = new Vec3(0, 1, 2);
		System.out.println(v);
		Matrix44 a, b, c;
		a = new Matrix44();
		b = new Matrix44();
		c = new Matrix44();
		c = Matrix44.multiply(a, b);
		
		Matrix44 d = new Matrix44(0.707107f, 0f, -0.707107f, 0f, -0.331295f, 0.883452f, -0.331295f, 0f, 0.624695f, 0.468521f, 0.624695f, 0f, 4.000574f, 3.00043f, 4.000574f, 1f);
		System.out.println(d);
		Matrix44 e = d.deepCopy();
		d.invert();
		System.out.println(d);
		System.out.println(Matrix44.multiply(d, e));
	}
}
