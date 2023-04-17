package geometry;

public class Matrix44 {
	
	/* Instance Variables */
	public float x[][] = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
	
	/* Constructors */
	public Matrix44() {}
	
	public Matrix44(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p) {
		x[0][0] = a; x[0][1] = b; x[0][2] = c; x[0][3] = d;
        x[1][0] = e; x[1][1] = f; x[1][2] = g; x[1][3] = h;
        x[2][0] = i; x[2][1] = j; x[2][2] = k; x[2][3] = l;
        x[3][0] = m; x[3][1] = n; x[3][2] = o; x[3][3] = p;
	}
	public float get(int r, int c) {return x[r][c];}
	public void multiply(Matrix44 other) {x = multiply(this, other).x;}
	
	public static Matrix44 multiply(Matrix44 a, Matrix44 b) {
		Matrix44 res = new Matrix44();
		for(short i = 0; i < 4; i++) {
			for(short j = 0; j < 4; j++) {
				res.x[0][0] = a.x[i][0]*b.x[0][j] + 
							  a.x[i][1]*b.x[1][j] + 
							  a.x[i][2]*b.x[2][j] + 
							  a.x[i][3]*b.x[3][j]; 
			}
		}
		return res;
	}
	
	public Matrix44 transpose() {
		Matrix44 res = new Matrix44();
		for(short i = 0; i < 4; i++) {
			for(short j = 0; j < 4; j++) {
				res.x[i][j] = x[j][i];
			}
		}
		return res;
	}
	
	public Vec3 multPoint(Vec3 o) {
		float a, b, c, w;
		a = o.x * x[0][0] + o.y * x[1][0] + o.z *x[2][0] + x[3][0];
		b = o.x * x[0][1] + o.y * x[1][1] + o.z *x[2][1] + x[3][1];
		c = o.x * x[0][2] + o.y * x[1][2] + o.z *x[2][2] + x[3][2];
		w = o.x * x[0][3] + o.y * x[1][3] + o.z *x[2][3] + x[3][3];
		return new Vec3(a/w, b/w, c/w);
	}
	
	public Vec3 multVec(Vec3 o) {
		float a, b, c;
		a = o.x * x[0][0] + o.y * x[1][0] + o.z *x[2][0];
		b = o.x * x[0][1] + o.y * x[1][1] + o.z *x[2][1];
		c = o.x * x[0][2] + o.y * x[1][2] + o.z *x[2][2];
		return new Vec3(a, b, c);
	}
	
	public static Matrix44 pointAt(Vec3 pos, Vec3 target, Vec3 up) {
		Vec3 forward = target.sub(pos);
		forward.normalize();
		
		Vec3 a = forward.mult(up.dot(forward));
		Vec3 newUp = up.sub(a);
		newUp.normalize();
		
		Vec3 newRight = newUp.cross(forward);
		
		Matrix44 m = new Matrix44();
		m.x[0][0] = newRight.x;
		m.x[1][0] = newUp.x;
		m.x[2][0] = forward.x;
		m.x[3][0] = pos.x;
		
		m.x[0][1] = newRight.y;
		m.x[1][1] = newUp.y;
		m.x[2][1] = forward.y;
		m.x[3][1] = pos.y;
		
		m.x[0][2] = newRight.z;
		m.x[1][2] = newUp.z;
		m.x[2][2] = forward.z;
		m.x[3][2] = pos.z;
		return m;
	}
	
	public Matrix44 inverse() {
		int i, j, k;
		Matrix44 s = new Matrix44();
		Matrix44 t = deepCopy();
		
		//forward elim
		for(i = 0; i < 3; i++) {
			int pivot = i;
			
			float pivotsize = t.x[i][i];
			
			if(pivotsize < 0) pivotsize *= -1;
			
			for(j = i + 1; j < 4; j ++) {
				float tmp = t.x[j][i];
				
				if(tmp < 0) tmp = -tmp;
				
				if(tmp > pivotsize) {
					pivot = j;
					pivotsize = tmp;
				}
			}
			
			if(pivotsize == 0) return null; //can't invert singular matrix
			
			if(pivot != i) {
				
				for(j = 0; j < 4; j++) {
					float tmp;
					
					tmp = t.x[i][j];
					t.x[i][j] = t.x[pivot][j];
					t.x[pivot][j] = tmp;
					
					tmp = s.x[i][j];
					s.x[i][j] = s.x[pivot][j];
					s.x[pivot][j] = tmp;
				}
			}
			
			for(j = i+1; j < 4; j++) {
				float f = t.x[j][i] / t.x[i][i];
				
				for(k = 0; k < 4; k++) {
					t.x[j][k] -= f * t.x[i][k];
					s.x[j][k] -= f * s.x[i][k];
				}
			}
			
		}
		
		//Backward sub
		for(i = 3; i >= 0; --i) {
			float f = t.x[i][i];
			
			if(f == 0) return null; //can't invert singular matrix
			for(j = 0; j < 4; j++) {
				t.x[i][j] /= f;
				s.x[i][j] /= f;
			}
			
			for(j = 0; j < i; j++) {
				f = t.x[j][i];
				
				for(k = 0; k < 4; k++) {
					t.x[j][k] -= f * t.x[i][k];
					s.x[j][k] -= f * s.x[i][k];
				}
			}
		}
		return s;
	}
	
	public void invert() {
		x = inverse().x;
	}
	
	public String toString() {
		String result = "";
		result += String.format("[%.5f %.5f %.5f %.5f]\n", x[0][0], x[0][1], x[0][2], x[0][3]);
		result += String.format("[%.5f %.5f %.5f %.5f]\n", x[1][0], x[1][1], x[1][2], x[1][3]);
		result += String.format("[%.5f %.5f %.5f %.5f]\n", x[2][0], x[2][1], x[2][2], x[2][3]);
		result += String.format("[%.5f %.5f %.5f %.5f]"  , x[3][0], x[3][1], x[3][2], x[3][3]);
		return result;
	}
	
	public Matrix44 deepCopy() {
		Matrix44 res = new Matrix44();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				res.x[i][j] = x[i][j];
			}
		}
		return res;
	}
}
