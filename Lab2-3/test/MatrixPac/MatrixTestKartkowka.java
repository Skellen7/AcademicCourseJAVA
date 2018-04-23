/*******
 *
 * Jakub Cholewa, grupa czwartek 14:00
 */


package MatrixPac;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MatrixTestKartkowka {
    @Test
    public void getSubmatrix() throws Exception {
        Random generator = new Random();
        int m = generator.nextInt(100)+1;
        int n = generator.nextInt(100)+1;


        double[][] d = new double[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                d[i][j] = 10*generator.nextDouble()-5;
            }
        }

        Matrix mat = new Matrix(d);

        int fromRow;
        int toRow;
        int fromCol;
        int toCol;
        for(int i=0; i<100; i++){
            fromRow = generator.nextInt(2*m+m+1)-m;
            fromCol = generator.nextInt(2*n+n+1)-n;

            toRow = generator.nextInt(2*m+m+1)-m;
            toCol = generator.nextInt(2*n+n+1)-n;

            try{
                mat.getSubmatrix(fromRow,toRow,fromCol,toCol);
            }catch(RuntimeException err){
                assertEquals(fromRow>=toRow || fromCol>=toCol || toRow>mat.rows || toCol>mat.cols
                        || fromRow<0 || fromCol<0 || toRow<0 || toCol<0, true);
            }
        }
    }
}