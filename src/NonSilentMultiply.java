public class NonSilentMultiply extends MultiplyRunnable implements Runnable {

    //constructor
    public NonSilentMultiply(double[] threadTime, int x, int numThreads, Matrix first, Matrix second, Matrix result) {

        super(threadTime,x,numThreads,first, second,result);
    }

    @Override
    public void run() {
        System.out.println("Thread-" + x +" started.");
        double start = System.nanoTime();

        multiply();

        double end = System.nanoTime();

        //converting to milliseconds
        double time = (end - start)/1e3;

        System.out.println("Thread-" + x +" stopped.");
        System.out.println("Thread-" + x + " execution time was (millis):" + time);
        //pass the time to threadTime
        threadTime[0]+=time;
        //System.out.println("tread"+x+" = "+threadTime[0]);
    }
}
