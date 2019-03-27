package game.graphics.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader {
	 public static BufferedImage loadImage(String path) {
		 BufferedImage img = null;
		 try {
		    img = ImageIO.read(new File(path));
		 } catch (Exception e) {
		 }

		 return img;
	 }
}
