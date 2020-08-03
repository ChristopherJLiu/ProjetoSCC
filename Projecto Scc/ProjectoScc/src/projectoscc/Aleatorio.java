package projectoscc;

public class Aleatorio {

    //Exponencial
    static double exponencial (double m, int stream){
	return (-m*Math.log(RandomGenerator.rand64(stream)));
    }

    //Normal baseada na random.nextGaussian do java
    static double[] normal(double media, double desvio,int stream) {
            double v1,v2,s;
            double[] norm = new double[2];
            do{
                v1 = 2* RandomGenerator.rand64(stream) -1;
		v2 = 2* RandomGenerator.rand64(stream) -1;
                s=v1*v1+v2*v2;
            }while(s>1 || s==0);
            double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s)/s);
            norm[0] = media+(v2 * multiplier)*desvio;
            norm[1] = media+(v1 * multiplier)*desvio;
            return norm;
    }
}