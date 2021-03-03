package Ben;

public class Space3d
{
	public double x;
	public double y;
	public double z;

	public Space3d()
	{
	}
	public Space3d(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Space3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Space3d(Space3d space3d)
	{
		this.x = space3d.x;
		this.y = space3d.y;
		this.z = space3d.z;
	}

}
