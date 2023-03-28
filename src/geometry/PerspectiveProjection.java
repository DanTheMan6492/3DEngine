package geometry;

public class PerspectiveProjection {

	public Vec2 computePixelCoordinates(Vec3 pWorld, Matrix44 WtC, float cWidth, float cHeight, int iWidth, int iHeight) {
		Vec3 pCamera = WtC.multPoint(pWorld);
		Vec2 pScreen = new Vec2();
		pScreen.x = pCamera.x / -pCamera.z;
		pScreen.y = pCamera.y / -pCamera.z;
		Vec2 pNDC = new Vec2();
		pNDC.x = (pScreen.x + cWidth  * 0.5f) /  cWidth;
		pNDC.y = (pScreen.x + cHeight * 0.5f) / cHeight;
		return new Vec2((int)(pNDC.x * iWidth), (int)((1 - pNDC.y) * iHeight));
	}
}
