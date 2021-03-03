package Ben;
public class Space2f
{
	public float x;
	public float y;

	public Space2f()
	{
	}

	public Space2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Space2f(Space2f point)
	{
		this.x = point.x;
		this.y = point.y;
	}

	public float distance(Space2f point)
	{
		float distance = (float) Math.sqrt((point.x-x)*(point.x-x)+(point.y-y)*(point.y-y));
		return distance;
	}

}
