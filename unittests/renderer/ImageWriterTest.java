/**
 * The ImageWriterTest class is a JUnit test class for the ImageWriter class, which is responsible for writing
 * pixel data to an image file. It includes a test method to validate the functionality of the ImageWriter class.
 */
package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

public class ImageWriterTest {

    /**
     * Test method to validate the functionality of the ImageWriter class. It creates an ImageWriter object,
     * writes red pixels to a 16x10 image, and saves the result to a file named "test".
     */
    @Test
    void testImageWriter() {
        // Create an ImageWriter object with a filename "test", width 16, and height 10.
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);

        // Iterate through each pixel in the 16x10 image and set it to red (255, 0, 0).
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                if(i%50 == 0 || j%50 == 0)
                    imageWriter.writePixel(i, j, new Color(255, 0, 0)); // Set pixel color to red
                else imageWriter.writePixel(i, j, new Color(255, 255, 0)); // Set pixel color to green
            }
        }

        // Save the image to a file named "test".
        imageWriter.writeToImage();
    }
}
