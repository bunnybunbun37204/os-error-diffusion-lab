package org.bunyawat;

import java.awt.image.BufferedImage;

class ImageProcessor {
    public BufferedImage convertToBlackAndWhite(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                int gray = (rgb >> 16) & 0xFF; // Assuming the image is already grayscale

                int newPixel = (gray < 128) ? 0 : 255;
                int error = gray - newPixel;

                convertedImage.setRGB(x, y, (newPixel << 16) | (newPixel << 8) | newPixel);

                distributeError(originalImage, x, y, error, width, height);
            }
        }

        return convertedImage;
    }

    private void distributeError(BufferedImage image, int x, int y, int error, int width, int height) {
        distributeErrorToPixel(image, x + 1, y, error * 7 / 16, width, height);
        distributeErrorToPixel(image, x - 1, y + 1, error * 3 / 16, width, height);
        distributeErrorToPixel(image, x, y + 1, error * 5 / 16, width, height);
        distributeErrorToPixel(image, x + 1, y + 1, error / 16, width, height);
    }

    private void distributeErrorToPixel(BufferedImage image, int x, int y, int error, int width, int height) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            int rgb = image.getRGB(x, y);
            int gray = (rgb >> 16) & 0xFF;
            int newGray = Math.max(0, Math.min(255, gray + error));
            image.setRGB(x, y, (newGray << 16) | (newGray << 8) | newGray);
        }
    }
}
