public class SilentMultiply extends MultiplyRunnable implements Runnable{

    //constructor
    public SilentMultiply(double[] threadTime, int x, int numThreads, Matrix first, Matrix second, Matrix result) {

        super(threadTime, x, numThreads,first, second, result);
    }

    @Override
    public void run() {

        double start = System.nanoTime();
        multiply();
        double end = System.nanoTime();

        double time = (end - start)/1e6;

        //threadTime is a sum of all the times of the Threads
        threadTime[0]+=time;
    }

}
