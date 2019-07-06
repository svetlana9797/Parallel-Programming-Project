public abstract class MultiplyRunnable {
    double[] threadTime;
    int x;
    int numThreads;
    Matrix first;
    Matrix second;
    Matrix result;

    //constructor
    public MultiplyRunnable(double[] threadTime,int x, int numThreads, Matrix first, Matrix second, Matrix result) {

        this.threadTime=threadTime;
        this.x=x;
        this.numThreads = numThreads;
        this.first = first;
        this.second=second;
        this.result=result;

    }

    //take all rows divisible by x and calculate the elements of the result matrix
    public void multiply() {

        for(int row=0;row<first.row;row++) {
            if (row % numThreads == x) {

                for (int col = 0; col < second.col; col++) {
                    result.matrix[row][col] = (first.matrix[row][0] * second.matrix[0][col])
                                                 + (first.matrix[row][1]*second.matrix[1][col]);
                }
            }
        }
    }
}
