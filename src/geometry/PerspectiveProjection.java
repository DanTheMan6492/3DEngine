package geometry;

public class PerspectiveProjection {

	static float VPD = 1;
	static float cWidth = 1280;
	static float cHeight = 720;
	
	public static Vec2 computePixelCoordinates(Vec3 pWorld, Matrix44 WtC) {
		Vec3 pCamera = WtC.multPoint(pWorld); //convert the global coordinates to camera coordinates
		Vec2 pScreen = new Vec2();
		Vec2 pNDC = new Vec2();
		
		//calculate the 2d coordinates of the point
		pScreen.x = VPD * pCamera.x / -pCamera.z;
		pScreen.y = VPD * pCamera.y / -pCamera.z;
		
		//System.out.println(pScreen);
		//scale it to the size of the canvas
		pNDC.x = (pScreen.x + cWidth  * 0.5f);
		pNDC.y = (pScreen.y + cHeight * 0.5f);
		
		
		return new Vec2((int)(pNDC.x), (int)(cHeight - pNDC.y));
	}
	
}
