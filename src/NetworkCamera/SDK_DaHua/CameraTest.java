package NetworkCamera.SDK_DaHua;

public class CameraTest
{

	public static void main(String[] args)
	{

		System.out.println("¿ªÊ¼");
		long userID = Camera_DaHua.backCamera.initAndRegist("192.168.1.107", 1);
		System.out.println("µÇÂ½ID£º" + userID);
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		for (int i = 1; i <= 10; i++)
		{
			String picName = "pic" + i;
			Camera_DaHua.backCamera.capturePics(userID, "image//", picName);
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
