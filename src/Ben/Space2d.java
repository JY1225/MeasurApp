package Ben;

public class Space2d
{
	public double x;
	public double y;

	public Space2d()
	{
	}
	public Space2d(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public Space2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Space2d(Space2d space2d)
	{
		this.x = space2d.x;
		this.y = space2d.y;
	}

}
