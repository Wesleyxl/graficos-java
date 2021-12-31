import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    
    private BufferedImage spriteSheet;

    // constructor method
    public SpriteSheet(String path) {
        try {
            spriteSheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // getting subimage
    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }


}
