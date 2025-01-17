package iut.vollart.models;

import java.io.Serializable;

public class Vector3D implements Serializable {
	
	public Vector3D(float x, float y, float z) {
		super();
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public float x=0;
	public float y=0;
	public float z=0;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}
