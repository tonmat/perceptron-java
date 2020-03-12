package tonmat.perceptron;

import static com.badlogic.gdx.math.MathUtils.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Perceptron extends ApplicationAdapter {
    private static final int POINTS_COUNT = 10000;
    private static final float BIAS = -1f;
    private static final float LEARNING_RATE = 0.1f;
    private Neuron neuron;
    private Point2D[] points;
    private int index;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        neuron = new Neuron(3, ActivationFunction.SIGN);
        neuron.randomizeWeights(-1, 1);
        points = new Point2D[POINTS_COUNT];
        for (var i = 0; i < points.length; i++) {
            points[i] = new Point2D(
                    random(0f, Gdx.graphics.getWidth()),
                    random(0f, Gdx.graphics.getHeight()));
        }
        index = 0;
        shapeRenderer = new ShapeRenderer(POINTS_COUNT);
    }

    private float guess(Point2D p) {
        return neuron.guess(BIAS, p.x, p.y);
    }

    private void train(Point2D p) {
        neuron.train(LEARNING_RATE, p.target, BIAS, p.x, p.y);
    }

    @Override
    public void render() {
        train(points[index]);
        index = (index + 1) % POINTS_COUNT;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (var i = 0; i < points.length; i++) {
            final var p = points[i];
            p.guess = guess(p);
            if (p.target == p.guess) {
                if (p.target < 0f)
                    shapeRenderer.setColor(.5f, .0f, .0f, 1);
                else
                    shapeRenderer.setColor(.0f, .0f, .5f, 1);
                shapeRenderer.circle(p.x, p.y, 6);
            }
        }

        for (var i = 0; i < points.length; i++) {
            final var p = points[i];
            if (p.target == p.guess) {
                if (p.target < 0f)
                    shapeRenderer.setColor(1, .5f, .5f, 1);
                else
                    shapeRenderer.setColor(.5f, .5f, 1, 1);
                shapeRenderer.circle(p.x, p.y, 4);
            }
        }

        shapeRenderer.setColor(0, .5f, 0, 1);
        for (var i = 0; i < points.length; i++) {
            final var p = points[i];
            if (p.target != p.guess)
                shapeRenderer.circle(p.x, p.y, 6);
        }

        shapeRenderer.setColor(0, 1, 0, 1);
        for (var i = 0; i < points.length; i++) {
            final var p = points[i];
            if (p.target != p.guess)
                shapeRenderer.circle(p.x, p.y, 4);
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
