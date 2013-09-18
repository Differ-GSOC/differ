package cz.nkp.differ.compare.io.pure;
import cz.nkp.differ.plugins.tools.ReportGenerator;
import cz.nkp.differ.compare.io.ImageProcessor;
import cz.nkp.differ.compare.io.ImageProcessorResult;
import cz.nkp.differ.compare.metadata.ImageMetadata;
import cz.nkp.differ.compare.metadata.MetadataExtractor;
import cz.nkp.differ.compare.metadata.MetadataExtractors;
import cz.nkp.differ.compare.metadata.MetadataSource;
import cz.nkp.differ.exceptions.ImageDifferException;
import cz.nkp.differ.images.ImageLoader;
import cz.nkp.differ.images.ImageManipulator;
import cz.nkp.differ.listener.ProgressListener;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.concurrent.Executors;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Implementation of ImageProcessor in Java
 *
 * @author xrosecky
 */
public class PureImageProcessor extends ImageProcessor {

    private ImageLoader imageLoader;
    private ReportGenerator pdfReporter;
    private MetadataExtractors extractors;
    private MetadataSource core = new MetadataSource(0, "", "", "core");

    public PureImageProcessor(ImageLoader imageLoader, MetadataExtractors extractors) {
        this.imageLoader = imageLoader;
        this.extractors = extractors;
        this.pdfReporter = new ReportGenerator();
    }

    private class ProcessImageTask implements Callable<PureImageProcessorResult> {

        private File image;

        public ProcessImageTask(File image) {
            this.image = image;
        }

        @Override
        public PureImageProcessorResult call() throws Exception {
            return processImage(image, null);
        }
    }

    private class ImageMetadataTask implements Callable<List<ImageMetadata>> {

        private MetadataExtractor extractor;
        private File image;

        public ImageMetadataTask(MetadataExtractor extractor, File image) {
            this.extractor = extractor;
            this.image = image;
        }

        @Override
        public List<ImageMetadata> call() throws Exception {
            return extractor.getMetadata(image);
        }
    }

    @Override
    public PureImageProcessorResult processImage(File image, ProgressListener callback) throws ImageDifferException {
        BufferedImage fullImage = imageLoader.load(image);
        if (fullImage == null) {
            throw new ImageDifferException(ImageDifferException.ErrorCode.IMAGE_READ_ERROR, "Unsupported file type");
        }
        java.awt.Image preview = ImageManipulator.getBitmapScaledImage(fullImage, this.getConfig().getImageWidth(), true);
        PureImageProcessorResult result = new PureImageProcessorResult(fullImage, preview);
        result.setType(ImageProcessorResult.Type.IMAGE);
        processImage(fullImage, result);

        result.getMetadata().add(new ImageMetadata("Image height", new Integer(fullImage.getHeight()), core));
        result.getMetadata().add(new ImageMetadata("Image width", new Integer(fullImage.getWidth()), core));
        result.getMetadata().add(new ImageMetadata("File name", image.getName(), core));
        result.getMetadata().add(new ImageMetadata("File path", image.getAbsolutePath(), core));
        List<Callable<List<ImageMetadata>>> tasks = new ArrayList<Callable<List<ImageMetadata>>>();

        String fileName = image.toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        for (MetadataExtractor extractor : extractors.getExtractors()) {
            tasks.add(new ImageMetadataTask(extractor, image));
        }
        List<Future<List<ImageMetadata>>> futures = execute(tasks);
        for (Future<List<ImageMetadata>> future : futures) {
            if (future.isDone()) {
                try {
                    result.getMetadata().addAll(future.get());
                } catch (InterruptedException ie) {
                    throw new ImageDifferException(ImageDifferException.ErrorCode.IMAGE_READ_ERROR, "Timeout exceeded", ie);
                } catch (ExecutionException ee) {
                    handleException(ee);
                }
            }
        }
        Map<String, Object> results = new HashMap<String, Object>();
        Set<String> conflicts = new HashSet<String>();
        for (ImageMetadata data : result.getMetadata()) {
            String key = data.getKey();
            Object val1 = data.getValue();
            Object val2 = results.get(key);
            if (val2 != null && !val2.toString().equals(val1.toString())) {
                conflicts.add(key);
            }
            if (val2 == null) {
                results.put(key, val1);
            }
        }
        for (ImageMetadata data : result.getMetadata()) {
            String key = data.getKey();
            data.setConflict(conflicts.contains(key));
        }
        result.setType(ImageProcessorResult.Type.IMAGE);
        pdfReporter.setDataSource(result);
        pdfReporter.buildAndExport();
        return result;
    }

    @Override
    public ImageProcessorResult[] processImages(File a, File b, ProgressListener callback) throws ImageDifferException {
        PureImageProcessorResult results[] = new PureImageProcessorResult[3];
        try {
            Callable<PureImageProcessorResult> task1 = new ProcessImageTask(a);
            Callable<PureImageProcessorResult> task2 = new ProcessImageTask(b);
            List<Future<PureImageProcessorResult>> futures = this.execute(Arrays.asList(task1, task2));
            int index = 0;
            for (Future<PureImageProcessorResult> future : futures) {
                if (future.isDone()) {
                    results[index] = future.get();
                } else {
                    results[index] = null;
                }
                index++;
            }
        } catch (InterruptedException ie) {
            throw new ImageDifferException(ImageDifferException.ErrorCode.IMAGE_READ_ERROR, "Timeout exceeded", ie);
        } catch (ExecutionException ee) {
            this.handleException(ee);
        }
        results[0] = this.processImage(a, null);
        results[1] = this.processImage(b, null);
        try {
            BufferedImage compareFull = getImagesDifference(results[0].getFullImage(), results[1].getFullImage());
            java.awt.Image comparePreview = ImageManipulator.getBitmapScaledImage(compareFull, this.getConfig().getImageWidth(), true);
            PureImageProcessorResult result = new PureImageProcessorResult(compareFull, comparePreview);
            result.setType(ImageProcessorResult.Type.COMPARISON);
            this.processImage(compareFull, result);
            this.addMetrics(result);
            result.setType(ImageProcessorResult.Type.COMPARISON);
            results[2] = result;
        } catch (Exception ex) {
            ex.printStackTrace();
            results[2] = null;
        }
        pdfReporter.setDataSource(results);
        pdfReporter.buildAndExport();
        return results;
    }

    private <T> List<Future<T>> execute(List<Callable<T>> tasks) throws ImageDifferException {
        ExecutorService executor = null;
        try {
            executor = Executors.newCachedThreadPool();
            return executor.invokeAll(tasks);
        } catch (InterruptedException ie) {
            throw new ImageDifferException(ImageDifferException.ErrorCode.IMAGE_READ_ERROR, "Timeout exceeded", ie);
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    private void handleException(ExecutionException ee) throws ImageDifferException {
        if (ee.getCause() instanceof ImageDifferException) {
            throw (ImageDifferException) ee.getCause();
        } else if (ee.getCause() instanceof RuntimeException) {
            throw (RuntimeException) ee.getCause();
        } else {
            throw new ImageDifferException(ImageDifferException.ErrorCode.IMAGE_READ_ERROR, "Error when reading image", ee);
        }
    }

    private void addMetrics(PureImageProcessorResult result) {
        long size = result.getHeight() * result.getWidth();
        int[][] histogram = result.getHistogram();
        String[] colours = new String[]{"red", "green", "blue"};
        MetadataSource mseSource = new MetadataSource(0, "", "", "MSE");
        MetadataSource psnrSource = new MetadataSource(0, "", "", "PSNR");
        for (int i = 0; i < 3; i++) {
            String colour = colours[i];
            long sqSum = 0;
            int[] values = histogram[i];
            for (int j = 0; j < values.length; j++) {
                sqSum += values[j] * j * j;
            }
            double mse = (1.0 / size) * sqSum;
            double psnr = 10 * Math.log10((255 * 255) / mse);
            result.getMetadata().add(new ImageMetadata(colour, mse, mseSource));
            result.getMetadata().add(new ImageMetadata(colour, psnr, psnrSource));
        }
    }

    private PureImageProcessorResult processImage(BufferedImage image, PureImageProcessorResult result) {
        int width = image.getWidth();
        int height = image.getHeight();
        result.setWidth(width);
        result.setHeight(height);
        // histogram
        int[] imagePixelCache = new int[width * height];
        image.getRGB(0, 0, width, height, imagePixelCache, 0, width); //Get all pixels
        int[][] bins = new int[3][256];
        for (int thisPixel = 0; thisPixel < width * height; thisPixel++) {
            int rgbCombined = imagePixelCache[thisPixel];
            int red = new Color(rgbCombined).getRed();
            bins[0][red]++;
            int green = new Color(rgbCombined).getGreen();
            bins[1][green]++;
            int blue = new Color(rgbCombined).getBlue();
            bins[2][blue]++;
        }
        result.setHistogram(bins);
        // checksum
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        for (int i : imagePixelCache) {
            byteStream.write(i);
        }
        String md5 = DigestUtils.md5Hex(byteStream.toByteArray());
        try {
            byteStream.close();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        result.setMD5Checksum(md5);
        return result;
    }

    private static BufferedImage getImagesDifference(BufferedImage image1, BufferedImage image2) {
        if (image1 == null) {
            throw new NullPointerException("image1");
        }
        if (image2 == null) {
            throw new NullPointerException("image2");
        }
        if (image1.getWidth(null) != image2.getWidth(null)
                || image1.getHeight(null) != image2.getHeight(null)) {
            throw new IllegalArgumentException("Cannot XOR images that are differing dimensions. XOR'ing failed.");
        }
        if (image1.getTransparency() != image2.getTransparency()) {
            throw new IllegalArgumentException("Cannot XOR images that are differing transparencies. XOR'ing failed.");
        }
        int type = BufferedImage.TYPE_INT_RGB;
        if (image1.getType() != type) {
            image1 = convert(image1, type);
        }
        if (image2.getType() != type) {
            image2 = convert(image2, type);
        }
        int width = image1.getWidth(null);
        int height = image1.getHeight(null);
        int resolution = width * height;

        int[] combo1Pixels = new int[resolution];
        int[] combo2Pixels = new int[resolution];
        int[] imagePixels = new int[resolution];

        image1.getRGB(0, 0, width, height, combo1Pixels, 0, width); //Get all pixels
        image2.getRGB(0, 0, width, height, combo2Pixels, 0, width); //Get all pixels

        for (int pixel = 0; pixel < resolution; pixel++) {
            Color pixel1 = new Color(combo1Pixels[pixel]);
            Color pixel2 = new Color(combo2Pixels[pixel]);
            Color diff = new Color(
                    Math.abs(pixel1.getRed() - pixel2.getRed()),
                    Math.abs(pixel1.getGreen() - pixel2.getGreen()),
                    Math.abs(pixel1.getBlue() - pixel2.getBlue()));
            //imagePixels[pixel] = Math.abs(combo1Pixels[pixel] - combo2Pixels[pixel]);
            imagePixels[pixel] = diff.getRGB();
        }

        int imageType = image1.getType();
        if (imageType == BufferedImage.TYPE_CUSTOM) {
            imageType = BufferedImage.TYPE_INT_RGB;
        }
        BufferedImage imageXOR = new BufferedImage(width, height, imageType);
        imageXOR.setRGB(0, 0, width, height, imagePixels, 0, width); //Set all pixels

        return imageXOR;
    }

    private static BufferedImage convert(BufferedImage image, int type) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), type);
        ColorConvertOp op = new ColorConvertOp(null);
        op.filter(image, result);
        return result;
    }
}
