import javafx.util.Pair;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main{

    private static void writeResultToFile(String filename, double totalTime, Matrix result) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {

            writer.write(result.toString());
            writer.write(String.format("Total execution time for current run(millis): %f", totalTime));

    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Matrix runMultiplication(double[] totalTime, boolean isQuiet, int numThreads, Matrix first, Matrix second) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        //initialize result matrix
        Matrix result = new Matrix(first.row,second.col);

        double[] threadTime={0};
        if (isQuiet) {
            //взимаме остатъците при деление на numThreads
            for(int x = 0;x < numThreads;x++) {

                Runnable worker = new SilentMultiply(threadTime,x, numThreads, first, second, result);
                executor.execute(worker);

                //add the thread time to the totalTime
                totalTime[0]+=threadTime[0];
            }
        }
        else {
            for(int x = 0;x < numThreads;x++) {
                Runnable worker = new NonSilentMultiply(threadTime,x, numThreads, first, second, result);
                totalTime[0]=threadTime[0];
                executor.execute(worker);
            }

        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException Ðµ) {
            System.out.println();
        }

        return result;
    }


    public static void main(String[] args) {
        if(args.length<1)
            System.out.println("error");
        //declare matrices
        Matrix first = null;
        Matrix second = null;
        Matrix result;
        double[] totalTime={0};


        Options options = new Options();
        options.addOption("m",true, "Value of m");
        options.addOption("n",true, "Value of n");
        options.addOption("k",true, "Value of k");
        options.addOption("i",true, "Input info from file");
        options.addOption("o",true, "Write result to file");
        options.addOption("q",false, "Quiet mode");
        options.addOption("t",true, "Number of threads run");

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine commandLine = parser.parse(options,args);

            if(commandLine.hasOption("m")&&commandLine.hasOption("n")&&commandLine.hasOption("k")) {
                System.out.println("Randomly generate matrices");
                //take the values of the cmd parameters
                int m1 = Integer.parseInt(commandLine.getOptionValue("m"));
                int n1 = Integer.parseInt(commandLine.getOptionValue("n"));
                int k1 = Integer.parseInt(commandLine.getOptionValue("k"));

                //randomly generate the matrices
                first = Matrix.generateRandomMatrix(m1,n1);
                second = Matrix.generateRandomMatrix(n1,k1);

            }

            else if(commandLine.hasOption("i")) {
                System.out.println("Input matrices from file\n");
                String filename = commandLine.getOptionValue("i");
                System.out.println("filename: " + filename);

                Pair<Matrix, Matrix> pair;
                pair = Matrix.createMatricesFromFile(filename);

                //take first and second matrix from the pair
                first = pair.getKey();
                second =pair.getValue();
            }

            if(commandLine.hasOption("t")){

                //get the number of threads from the command line
                int numThreads = Integer.parseInt(commandLine.getOptionValue("t"));
                double time;

                if(commandLine.hasOption("q")) {
                    System.out.println("Working in quiet mode...\n");

                    double startTime= System.nanoTime();
                    result = runMultiplication(totalTime,true, numThreads, first, second);

                    double endTime=System.nanoTime();
                    time = (endTime - startTime)/1e6;
                    System.out.println("Total execution time for current run (millis): " + time);
                }

                else {
                    double startTime= System.nanoTime();
                    result = runMultiplication(totalTime,false,numThreads,first, second);
                    double endTime=System.nanoTime();
                    time = (endTime - startTime)/1e3;
                    System.out.println("Total execution time for current run (millis): " + time);
                }

                if (commandLine.hasOption("o")) {
                    String filename = commandLine.getOptionValue("o");
                    //double tT=totalTime[0];
                    writeResultToFile(filename, time, result);
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}