package tonmat.perceptron;

import com.badlogic.gdx.math.MathUtils;

public class Neuron {
    public final float bias;
    public final float[] weights;
    private final ActivationFunction activationFunction;

    public Neuron(float bias, int weights, ActivationFunction activationFunction) {
        this.bias = bias;
        this.weights = new float[weights + 1];
        this.activationFunction = activationFunction;
    }

    public void randomizeWeights(float min, float max) {
        for (var i = 0; i < weights.length; i++)
            weights[i] = MathUtils.random(min, max);
    }

    public float guess(float... input) {
        var sum = weights[0] * bias;
        for (var i = 0; i < input.length; i++)
            sum += weights[i + 1] * input[i];
        return activationFunction.activate(sum);
    }

    public void train(float learningRate, float target, float... input) {
        final var guess = guess(input);
        final var error = target - guess;
        weights[0] += error * bias * learningRate;
        for (var i = 0; i < input.length; i++)
            weights[i + 1] += error * input[i] * learningRate;
    }
}
