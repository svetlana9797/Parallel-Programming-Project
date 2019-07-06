import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Matrix {

    public int row;
    public int col;
    public int[][] matrix;

    //correct
    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        this.matrix = new int[row][col];
    }

    public static Matrix generateRandomMatrix(int row, int col) {
        //use constructor
        Matrix mat = new Matrix(row, col);
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        for (int i = 0; i < mat.row; i++)
            for (int j = 0; j < mat.col; j++) {
                int num = rand.nextInt() % 100;
                mat.matrix[i][j] = Math.abs(num);
            }

        return mat;
    }

    //correct
    public static Pair<Matrix, Matrix> createMatricesFromFile(String filename) {
        Matrix first = null;
        Matrix second = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            //get the parameters of the matrices
            //m,n,k -> the first line
            String line = reader.readLine();
            String[] values = line.split(" ");

            first = new Matrix(Integer.parseInt(values[0]),Integer.parseInt(values[1]));
            second = new Matrix(Integer.parseInt(values[1]),Integer.parseInt(values[2]));

            //get the elements of the first matrix
            int[][] matrix_first = new int[first.row][first.col];
            for (int i = 0; i < first.row; i++) {
                if ((line = reader.readLine()) != null) {
                    String[] elem = line.split(" ");
                    for (int j = 0; j < first.col; j++) {
                        matrix_first[i][j] = Integer.parseInt(elem[j]);
                    }
                }
            }
            first.matrix = matrix_first;

            //get the elements of the second matrix
            int[][] matrix_second = new int[second.row][second.col];
            for (int i = 0; i < second.row; i++) {
                if ((line = reader.readLine()) != null) {
                    String[] elem = line.split(" ");
                    for (int j = 0; j < second.col; j++) {
                        matrix_second[i][j] = Integer.parseInt(elem[j]);
                    }
                }
            }
            second.matrix = matrix_second;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pair<Matrix, Matrix> pair = new Pair<Matrix, Matrix>(first, second);
        return pair;
    }


    //correct
    public void printElements() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int value = matrix[i][j];
                result.append(value);
                result.append(" ");
            }
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();
    }

}