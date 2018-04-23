package MatrixPac;

import static org.junit.Assert.*;

public class MatrixTest {
    @org.junit.Test
    public void testConstructorMatrix() throws Exception {

    }

    @org.junit.Test
    public void asArray() throws Exception {
        double[][] tab = {{1,2,3},{4,5,6}};
        Matrix a = new Matrix(tab);
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                assertEquals(tab[i][j],a.data[a.cols*i+j],0);
            }
        }
    }

    @org.junit.Test
    public void get() throws Exception {
        double[][] tab = {{1,2,3},{4,5,6}};
        Matrix a = new Matrix(tab);
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                assertEquals(tab[i][j],a.get(i,j),0);
            }
        }
    }

    @org.junit.Test
    public void set() throws Exception {
        double[][] tab = {{1,2,3},{4,5,6}};
        Matrix a = new Matrix(2,3);
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                a.set(i,j,i+j);
                assertEquals(i+j,a.data[a.cols*i+j],0);
            }
        }
    }

    @org.junit.Test
    public void testToString() throws Exception {

    }

    @org.junit.Test
    public void reshape() throws Exception {
    }

    @org.junit.Test
    public void shape() throws Exception {
    }

    @org.junit.Test
    public void add() throws Exception {
    }

    @org.junit.Test
    public void sub() throws Exception {
    }

    @org.junit.Test
    public void mul() throws Exception {
    }

    @org.junit.Test
    public void div() throws Exception {
    }

    @org.junit.Test
    public void add1() throws Exception {
    }

    @org.junit.Test
    public void sub1() throws Exception {
    }

    @org.junit.Test
    public void mul1() throws Exception {
    }

    @org.junit.Test
    public void div1() throws Exception {
    }

    @org.junit.Test
    public void dot() throws Exception {
    }

    @org.junit.Test
    public void frobenius() throws Exception {
    }

    @org.junit.Test
    public void show() throws Exception {
    }

}