package ex12;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class PictureApplication {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner pictureFile = new Scanner(new FileReader("picture.txt"));
        Picture picture = new Picture();
// look ahead for first line
        while (pictureFile.hasNext()) {
            int x1, y1, x2, y2;
            x1 = pictureFile.nextInt();
            y1 = pictureFile.nextInt();
            x2 = pictureFile.nextInt();
            y2 = pictureFile.nextInt();
            picture.add(new Line(x1, y1, x2, y2));
        }
        System.out.println(picture);
PictureFrame f = new PictureFrame(picture);
    }
}