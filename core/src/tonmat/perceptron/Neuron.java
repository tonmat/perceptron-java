package tonmat.perceptron;

import com.badlogic.gdx.math.MathUtils;

public class Neuron {
    public final float[] weights;
    private final ActivationFunction activationFunction;

    public Neuron(int weights, ActivationFunction activationFunction) {
        this.weights = new float[weights];
        this.activationFunction = activationFunction;
    }

    public void randomizeWeights(float min, float max) {
        for (var i = 0; i < weights.length; i++)
            weights[i] = MathUtils.random(min, max);
    }

    public float guess(float... input) {
        var sum = 0f;
        for (var i = 0; i < weights.length; i++)
            sum += weights[i] * input[i];
        return activationFunction.activate(sum);
    }

    public void train(float learningRate, float target, float... input) {
        final var guess = guess(input);
        final var error = target - guess;
        for (var i = 0; i < weights.length; i++)
            weights[i] += error * input[i] * learningRate;
    }
}
