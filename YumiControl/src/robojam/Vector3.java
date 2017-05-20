package robojam;

import java.util.Vector;

public class Vector3
{
	private double _x;
	private double _y;
	private double _z;

	public Vector3(){

	}

	public Vector3(double x, double y, double z)
	{
		_x=x;
		_y=y;
		_z=z;
	}

	
	public double getX(){return _x;}
	public double getY(){return _y;}
	public double getZ(){return _z;}
	public void setX(double value){_x=value;}
	public void setY(double value){_y=value;}
	public void setZ(double value){_z=value;}

}