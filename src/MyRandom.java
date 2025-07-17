import java.awt.Point;
import java.util.Random;

public class MyRandom {

    /*

    java.util.Random eksiği custom nextPoint methodu ekler.
    Kullanımı:
    MyRandom.nexPoint(Xbound, Ybound)

    */

    private static final Random rand = new Random();

    public static Point nextPoint(int maxX, int maxY) {
        int x = rand.nextInt(maxX);
        int y = rand.nextInt(maxY);
        return new Point(x, y);
    }


}
