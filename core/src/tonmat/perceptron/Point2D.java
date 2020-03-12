package tonmat.perceptron;

public class Point2D {
    public final float x;
    public final float y;
    public final float target;
    public float guess;

    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
        target = x > y ? -1f : 1f;
    }
}
