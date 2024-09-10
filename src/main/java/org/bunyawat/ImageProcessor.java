package org.bunyawat;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ImageProcessor {

    private final int nThreads;

    public ImageProcessor(int nThreads) {
        this.nThreads = nThreads;
    }

    public BufferedImage convertToBlackAndWhite(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Use TYPE_BYTE_BINARY to optimize memory usage
        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        // Create and reuse n threads for rows
        for (int i = 0; i < nThreads; i++) {
            int finalI = i;
            executor.submit(() -> processRows(originalImage, convertedImage, width, height, finalI));
        }

        // Shutdown the executor
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        return convertedImage;
    }

    private void processRows(BufferedImage originalImage, BufferedImage convertedImage, int width, int height, int threadIndex) {
        for (int y = threadIndex; y < height; y += nThreads) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                int gray = (rgb >> 16) & 0xFF; // Extract grayscale value from the red channel

                // Use integer thresholding for black-and-white conversion
                int newPixel = (gray < 128) ? 0 : 255;
                int error = gray - newPixel;

                convertedImage.setRGB(x, y, (newPixel << 16) | (newPixel << 8) | newPixel);

                // Distribute error using the Floyd-Steinberg dithering algorithm
                distributeError(originalImage, x, y, error, width, height);
            }
        }
    }

    private void distributeError(BufferedImage image, int x, int y, int error, int width, int height) {
        // Floyd-Steinberg error diffusion, adjusting neighboring pixels
        distributeErrorToPixel(image, x + 1, y, error * 7 / 16, width, height);
        distributeErrorToPixel(image, x - 1, y + 1, error * 3 / 16, width, height);
        distributeErrorToPixel(image, x, y + 1, error * 5 / 16, width, height);
        distributeErrorToPixel(image, x + 1, y + 1, error / 16, width, height);
    }

    private void distributeErrorToPixel(BufferedImage image, int x, int y, int error, int width, int height) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            int rgb = image.getRGB(x, y);
            int gray = (rgb >> 16) & 0xFF; // Extract grayscale value from the red channel
            int newGray = Math.max(0, Math.min(255, gray + error));
            image.setRGB(x, y, (newGray << 16) | (newGray << 8) | newGray);
        }
    }
}
