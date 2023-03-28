package geometry;

public class Camera {
	
	private Matrix44 pos;
	
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
	
	//using Rodrigues' rotation formula to rotate around up vector
	public void turnRight(float degrees) {
		Vec3 right   = new Vec3(pos.x[0][0], pos.x[0][1], pos.x[0][2]);
		Vec3 up      = new Vec3(pos.x[0][0], pos.x[0][1], pos.x[0][2]);
		Vec3 forward = new Vec3(pos.x[0][0], pos.x[0][1], pos.x[0][2]);
	}
}
