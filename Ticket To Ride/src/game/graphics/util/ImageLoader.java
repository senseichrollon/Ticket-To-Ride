package game.graphics.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader {
	 public static BufferedImage loadImage(String path) {
		 BufferedImage img = null;
		 try {
		    img = ImageIO.read(new File(path));
		 } catch (Exception e) {
			 System.out.println("Error loading image!");
		 }
		 return img;
	 }
	 
	 public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
		    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2d = dimg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();

		    return dimg;
		}
}
