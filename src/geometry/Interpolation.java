package geometry;

public class Interpolation {
	public static float bilinear(float tx, float ty, float c00, float c01, float c10, float c11) {
		float a = c00 * (1 - tx) + c10 * tx;
		float b = c01 * (1 - tx) + c11 * tx;
		return a * (1 - ty) + b * ty;
	}
}
