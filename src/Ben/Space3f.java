package Ben;
public class Space3f
{
	public float x;
	public float y;
	public float z;

	public Space3f()
	{
	}

	public Space3f(float x, float y,float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Space3f(Space3f point)
	{
		this.x = point.x;
		this.y = point.y;
		this.z = point.z;
	}

	public float distance(Space3f point)
	{
		float distance = (float) Math.sqrt((point.x-x)*(point.x-x)+(point.y-y)*(point.y-y)+(point.z-z)*(point.z-z));
		return distance;
	}

}
