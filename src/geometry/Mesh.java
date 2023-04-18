package geometry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mesh {
	public ArrayList<Triangle> tris;
	
	public Mesh() {
		tris = new ArrayList<Triangle>();
	}
	
	public Mesh(Triangle[] tris) {
		this.tris = new ArrayList<Triangle>();
		for(Triangle t : tris) {
			this.tris.add(t);
		}
	}
	
	//path in the form .\\src\\..
	public boolean loadFromFile(String path) {
		
		File file = new File(path);
		Scanner scnr;
		
		try {scnr = new Scanner(file);} 
		catch (FileNotFoundException e) {return false;}
		
		ArrayList<Vec3> verts = new ArrayList<Vec3>();
		
		while(scnr.hasNextLine()){
			
			String line = scnr.nextLine();
			//System.out.println(line);
			if(line.length() != 0 && line.charAt(0) == 'v' && line.charAt(1) == ' ') {
				Vec3 v = new Vec3();
				line = line.trim().replaceAll(" +", " ");
				String[] vals = line.split(" ");
				
				// NOTE: we start from 1 as the 0th string is just "v"
				v.x = Float.parseFloat(vals[1]);
				v.y = Float.parseFloat(vals[2]);
				v.z = Float.parseFloat(vals[3]);
				verts.add(v);
			}
			
			if(line.length() != 0 && line.charAt(0) == 'f') {
				int[] f = new int[3];
				line = line.trim().replaceAll(" +", " ");
				String[] vals = line.split(" ");
				// NOTE: we start from 1 as the 0th string is just "v"
				f[0] = Integer.parseInt(vals[1]);
				f[1] = Integer.parseInt(vals[2]);
				f[2] = Integer.parseInt(vals[3]);
				tris.add(new Triangle(verts.get(f[0]-1), verts.get(f[1]-1), verts.get(f[2]-1)));
			}
		}
		
		scnr.close();
		return true;
	}
}
