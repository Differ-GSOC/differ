package cz.nkp.differ.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ImageManipulator {

    private static Logger LOGGER = LogManager.getLogger(ImageManipulator.class);

    public static class ImageManipulationException extends Exception {

	public ImageManipulationException(String string) {
	    super(string);
	    LOGGER.warn(string);
	}
    }

    public static BufferedImage getImagesDifference(BufferedImage image1, BufferedImage image2) throws ImageManipulationException {
	if (image1 == null || image2 == null) {
	    throw new ImageManipulationException("Cannot XOR images that are null. XOR'ing failed.");
	}

	if (image1.getWidth(null) != image2.getWidth(null)
		|| image1.getHeight(null) != image2.getHeight(null)) {
	    throw new ImageManipulationException("Cannot XOR images that are differing dimensions. XOR'ing failed.");
	}

	if (image1.getTransparency() != image2.getTransparency()) {
	    throw new ImageManipulationException("Cannot XOR images that are differing transparencies. XOR'ing failed.");
	}

	if (image1.getType() != image2.getType()) {
	    throw new ImageManipulationException("Cannot XOR images that are differing data layout types. XOR'ing failed.");
	}

	int image_width = image1.getWidth(null);
	int image_height = image1.getHeight(null);
	int image_total_pixels = image_width * image_height;

	int[] combo1Pixels = new int[image_total_pixels];
	int[] combo2Pixels = new int[image_total_pixels];
	int[] imagePixels = new int[image_total_pixels];

	image1.getRGB(0, 0, image_width, image_height, combo1Pixels, 0, image_width); //Get all pixels
	image2.getRGB(0, 0, image_width, image_height, combo2Pixels, 0, image_width); //Get all pixels

	for (int pixel = 0; pixel < image_total_pixels; pixel++) {
	    imagePixels[pixel] = Math.abs(combo1Pixels[pixel] - combo2Pixels[pixel]);
	}

	int imageType = image1.getType();
	if (imageType == BufferedImage.TYPE_CUSTOM) {
	    imageType = BufferedImage.TYPE_INT_ARGB;
	}
	BufferedImage imageXOR = new BufferedImage(image_width, image_height, imageType);
	imageXOR.setRGB(0, 0, image_width, image_height, imagePixels, 0, image_width); //Set all pixels

	return imageXOR;
    }

    public static Image getBitmapScaledImage(BufferedImage image, int width, boolean scaleFit) {
        if (image == null) {
            throw new NullPointerException("image");
        }
	if (width < 1) {
	    throw new IllegalArgumentException("Invalid image scale width. Image scaling failed");
	}
	int height = image.getHeight();
	if (scaleFit) {
	    double scale = ((double) width / ((double) image.getWidth()));
	    height = (int) (image.getHeight() * scale);
	}
	return image.getScaledInstance(width, height, BufferedImage.SCALE_FAST);
    }
}
