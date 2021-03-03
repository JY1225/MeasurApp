package Ben.CNN;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.JPanel;

public class MatrixView
{
	private int rows;
	private int cols;
	private BufferedImage bufferedImage;
	private JPanel matrixPanel;

	public MatrixView(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		bufferedImage = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
	}

	public JPanel createJPanel()
	{
		matrixPanel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				g.drawImage(bufferedImage, (matrixPanel.getWidth() - cols) / 2, (matrixPanel.getHeight() - cols) / 2, matrixPanel);
			}
		};
		return matrixPanel;
	}

	public BufferedImage MatrixToImage(double[][] imageMatrix)
	{
		Graphics g = bufferedImage.getGraphics();
		int pointSize = 1;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				if (imageMatrix[i][j] == 1)
				{
					g.setColor(Color.white);
				}
				else
				{
					g.setColor(Color.black);
				}
				g.fillRect(j * pointSize,(rows-i) * pointSize, pointSize, pointSize);
			}
		}
		return bufferedImage;
	}

	public void createImage(String path) throws IOException
	{
		File file = new File(path);
		ImageIO.write(bufferedImage, "jpg", file);
	}

	

}