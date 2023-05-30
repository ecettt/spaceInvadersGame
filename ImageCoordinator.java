import javax.swing.*;
//this class is used to make the arms of the aliens up and down
public class ImageCoordinator {
    private ImageIcon image1, image2;
    private int index = 0;

    public ImageCoordinator(String image1Path, String image2Path) {
        this.image1 = new ImageIcon(image1Path);
        this.image2 = new ImageIcon(image2Path);
    }

    public ImageIcon switchImage() {
        if (index == 0) {
            index++;
            return image1;
        } else {
            index = 0;
            return image2;
        }
    }

    public ImageIcon getImage() {
        if (index == 0)
            return image1;
        else
            return image2;
    }
}
