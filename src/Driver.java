import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import geometry.*;

public class Driver extends JPanel implements MouseListener, ActionListener, KeyListener {
	
	Timer t;
	static int cWidth = 1000;
	static int cHeight = 1000;
	static double zfar = 1000;
	static double znear = 0.1;
	static double FOV = 80;
	
	static double a = cWidth/((double)cHeight);
	static double f = 1 / Math.tan(Math.toRadians(FOV)/2);
	static double q = (zfar)/(zfar-znear);
	
	static Matrix44 proj = new Matrix44();
	static Mesh meshCube;
	
	static Vec3 vCam = new Vec3();
	static Vec3 vLookDir = new Vec3();
	
	
	double time = 0;
	double fTheta = 0;
	
	float Yaw = 0;
	
	public static void main(String[] args) {
		
		proj.x[0][0] = (float) (a * f);
		proj.x[1][1] = (float) (f);
		proj.x[2][2] = (float) (q);
		proj.x[3][2] = (float) (-q * znear);
		
		proj.x[3][3] = 0;
		proj.x[2][3] = 1;
		
		meshCube = new Mesh();
		System.out.print(meshCube.loadFromFile(".\\src\\objs\\garfield.obj"));
		
		new Driver();
	}
	
	public Driver() {
		JFrame f = new JFrame();
		f.setTitle("3dEngine");
		f.setSize(cWidth, cHeight);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.add(this);
		f.addMouseListener(this);
		f.addKeyListener(this);
		t = new Timer(7, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, cWidth, cHeight);
		g.setColor(Color.white);
		Matrix44 matRotZ = new Matrix44(), matRotX = new Matrix44();
		fTheta += 0.01;
		
		matRotZ.x[0][0] = (float)  Math.cos(fTheta);
		matRotZ.x[0][1] = (float)  Math.sin(fTheta);
		matRotZ.x[1][0] = (float) -Math.sin(fTheta);
		matRotZ.x[1][1] = (float)  Math.cos(fTheta);
		
		matRotX.x[1][1] = (float)  Math.cos(fTheta*0.5);
		matRotX.x[1][2] = (float)  Math.sin(fTheta*0.5);
		matRotX.x[2][1] = (float) -Math.sin(fTheta*0.5);
		matRotX.x[2][2] = (float)  Math.cos(fTheta*0.5);
		
		vLookDir = new Vec3(0, 0, 1);
		Vec3 vUp = new Vec3(0, 1, 0);
		Vec3 vTarget = vCam.add(vLookDir);
		
		Matrix44 matCam = Matrix44.pointAt(vCam, vTarget, vUp);
		Matrix44 matView = matCam.inverse();
		ArrayList<Triangle> toDraw = new ArrayList<Triangle>();
		
		
		for(Triangle t : meshCube.tris) {
			
			//apply rotation
			Triangle rotate = new Triangle(t.p);
			
			rotate.p[0] = matRotZ.multPoint(rotate.p[0]);
			rotate.p[1] = matRotZ.multPoint(rotate.p[1]);
			rotate.p[2] = matRotZ.multPoint(rotate.p[2]);
			
			rotate.p[0] = matRotX.multPoint(rotate.p[0]);
			rotate.p[1] = matRotX.multPoint(rotate.p[1]);
			rotate.p[2] = matRotX.multPoint(rotate.p[2]);
			
			//apply transformation
			Triangle trans = new Triangle(rotate.p);

			trans.p[0].z = rotate.p[0].z + 6.0f;
			trans.p[1].z = rotate.p[1].z + 6.0f;
			trans.p[2].z = rotate.p[2].z + 6.0f;

			//apply projection (if applicable)
			Triangle triProj = new Triangle();
			Vec3 normal = (trans.p[1].sub(trans.p[0])).cross(trans.p[2].sub(trans.p[0]));
			normal.normalize();
			
			
			if(normal.x * (trans.p[0].x - vCam.x) +
			   normal.y * (trans.p[0].y - vCam.y) +
			   normal.z * (trans.p[0].z - vCam.z) < 0) 
			{
				// Ilumination
				Vec3 lightDir = new Vec3(0, 0, -1);
				lightDir.normalize();
				float dp = normal.dot(lightDir);
				dp = Math.abs(dp);
				
				//World space -> View space
				Triangle triView = new Triangle();
				
				triView.p[0] = matView.multPoint(trans.p[0]);
				triView.p[1] = matView.multPoint(trans.p[1]);
				triView.p[2] = matView.multPoint(trans.p[2]);
				
				
				// 3d -> 2d
				triProj.p[0] = proj.multPoint(triView.p[0]);
				triProj.p[1] = proj.multPoint(triView.p[1]);
				triProj.p[2] = proj.multPoint(triView.p[2]);
			
			
				triProj.p[0].x += 1.0f; triProj.p[0].y += 1.0f;
				triProj.p[1].x += 1.0f; triProj.p[1].y += 1.0f;
				triProj.p[2].x += 1.0f; triProj.p[2].y += 1.0f;
			
				triProj.p[0].x *= 0.5f * cWidth; triProj.p[0].y *= 0.5f * cHeight;
				triProj.p[1].x *= 0.5f * cWidth; triProj.p[1].y *= 0.5f * cHeight;
				triProj.p[2].x *= 0.5f * cWidth; triProj.p[2].y *= 0.5f * cHeight;
			
				triProj.rgb = new float[]{dp, dp, dp};
				toDraw.add(triProj);
			
				
			}
		}
		
		Collections.sort(toDraw);
		
		for(Triangle t : toDraw) {
			int[] x = {cWidth - (int) t.p[0].x, cWidth - (int) t.p[1].x, cWidth - (int) t.p[2].x};
			int[] y = {cHeight - (int) t.p[0].y, cHeight - (int) t.p[1].y, cHeight - (int) t.p[2].y};
			g.setColor(new Color(t.rgb[0], t.rgb[1], t.rgb[2]));
			g.fillPolygon(x, y, 3);
			//g.setColor(Color.black);
			//g.drawPolygon(x, y, 3);
		}
		
		time += 0.01;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		
		Vec3 forward = vLookDir.mult(8);
		
		switch(e.getKeyCode()) {
		case 87:
			vCam.y += 1;
			break;
		case 65:
			vCam.x += 1;
			break;
		case 83:
			vCam.y -= 1;
			break;
		case 68:
			vCam.x -= 1;
			break;	
		case 37:
			Yaw -= 2;
			break;
		case 29:
			Yaw += 2;
			break;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
