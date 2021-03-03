package Ben;

public class Space4d
{
	public double x;
	public double y;
	public double z;
	public double t;

	public Space4d()
	{
	}
	public Space4d(float x, float y, float z,float t)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}
	public Space4d(double x, double y, double z,double t)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}

	public Space4d(Space4d space4d)
	{
		this.x = space4d.x;
		this.y = space4d.y;
		this.z = space4d.z;
		this.t = space4d.t;
		
	}

}
