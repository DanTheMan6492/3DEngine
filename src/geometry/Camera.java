package geometry;

public class Camera {
	
	public Matrix44 pos;
	
	public Camera() {
		pos = new Matrix44();
		lookAt(new Vec3(0, 0, 0), new Vec3(0, 0, 1), new Vec3(0, 1, 0));
	}
	
	public void lookAt(Vec3 from, Vec3 to, Vec3 up) {
		Vec3 forward = from.sub(to);
		forward.normalize();
		Vec3 right = up.cross(forward);
		right.normalize();
		Vec3 newUp = forward.cross(right);
		
		pos.x[0][0] = right.x  ; pos.x[0][1] = right.y  ; pos.x[0][2] =   right.z;
		pos.x[1][0] = newUp.x  ; pos.x[1][1] = newUp.y  ; pos.x[1][2] =   newUp.z;
		pos.x[2][0] = forward.x; pos.x[2][1] = forward.y; pos.x[2][2] = forward.z;
		pos.x[3][0] = from.x   ; pos.x[3][1] = from.y   ; pos.x[3][2] =    from.z;
	}
	
	public Vec2 calc(Vec3 p) {
		Vec2 result = PerspectiveProjection.computePixelCoordinates(p, pos.inverse());
		return result;
	}
	
	
}
