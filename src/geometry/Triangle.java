package geometry;

public class Triangle implements Comparable<Triangle> {
	public Vec3[] p;
	public float[] rgb;
	
	public Triangle() {
		p = new Vec3[3];
		for(int i = 0; i < 3; i++) {
			p[i] = new Vec3();
		}
	}
	
	public Triangle(Vec3[] p) {
		this.p = new Vec3[3];
		for(int i = 0; i < 3; i++) {
			this.p[i] = new Vec3(p[i]);
		}
	}
	
	public Triangle(Vec3 a, Vec3 b, Vec3 c) {
		p = new Vec3[3];
		p[0] = a;
		p[1] = b;
		p[2] = c;
	}
	
	public int compareTo(Triangle compareTri) {
		
		double tZ = (p[0].z + p[1].z + p[2].z) / 3.0; 
		double oZ = (compareTri.p[0].z + compareTri.p[1].z + compareTri.p[2].z) / 3.0; 
		//ascending order
		
		
		if(tZ > oZ) {
			return -1;
		} else if(tZ < oZ) {
			return 1;
		} else {
			return 0;
		}
		
		//descending order
		//return compareQuantity - this.quantity;
		
	}	
}
