package other_stuff;

import net.minecraft.util.math.MathHelper;

import java.util.Random;
import java.util.function.Function;

public class calcMonteCarlo
{
    private final Random rand_gen = new Random();
    private double result = 0;
    private int number_of_points;

    public calcMonteCarlo(int number_of_points_in)
    {
        this.number_of_points = number_of_points_in;

        if(this.number_of_points <= 0)
        {
            throw new IllegalArgumentException("Invalid value");
        }
    }

    public double integrate(Function<Double, Double> function_in)
    {
        for (int j = 0; j < this.number_of_points; j++)
        {
            result += function_in.apply(MathHelper.nextDouble(rand_gen, 0, 1));
        }

        return result/this.number_of_points;
    }
}
