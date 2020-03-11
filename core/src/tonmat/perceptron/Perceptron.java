package tonmat.perceptron;

import static com.badlogic.gdx.math.MathUtils.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.SnapshotArray;

public class Perceptron extends ApplicationAdapter {
    private static final float BIAS = -1f;
    private static final float LEARNING_RATE = 0.1f;
    private Neuron neuron;
    private SnapshotArray<Point2D> points;
    private float delta;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        neuron = new Neuron(3, ActivationFunction.SIGN);
        points = new SnapshotArray<>(false, 10000, Point2D.class);
        delta = 0f;
        shapeRenderer = new ShapeRenderer(10000);
    }

    private Point2D addPoint() {
        final var p = new Point2D(
                random(0f, Gdx.graphics.getWidth()),
                random(0f, Gdx.graphics.getHeight()));
        points.add(p);
        return p;
    }

    private float guess(Point2D p) {
        return neuron.guess(BIAS, p.x, p.y);
    }

    private void train(Point2D p) {
        neuron.train(LEARNING_RATE, p.target, BIAS, p.x, p.y);
    }

    @Override
    public void render() {
        delta += Gdx.graphics.getDeltaTime();

        if (delta >= 0.1f) {
            delta -= 0.1f;
            for (int i = 0; i < 10; i++) {
                final var p = addPoint();
                final var guess = guess(p);
                if (p.target != guess)
                    train(p);
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        final var array = points.begin();
        for (var i = 0; i < points.size; i++) {
            final var p = array[i];
            shapeRenderer.circle(p.x, p.y, 10);
        }
        for (var i = 0; i < points.size; i++) {
            final var p = array[i];
            final var guess = guess(p);
            if (p.target != guess)
                shapeRenderer.setColor(0, 1, 0, 1);
            else if (p.target < 0f)
                shapeRenderer.setColor(1, .5f, .5f, 1);
            else
                shapeRenderer.setColor(.5f, .5f, 1, 1);
            shapeRenderer.circle(p.x, p.y, 8);
        }
        points.end();
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
