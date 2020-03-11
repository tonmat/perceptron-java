package tonmat.perceptron;

@FunctionalInterface
public interface ActivationFunction {
    ActivationFunction SIGN = input -> input < 0f ? -1f : 1f;

    float activate(float input);
}
