package MatrixPac;

public class Main {
    public static void main(String[] args){

        Matrix m = new Matrix(new double[][]{{1,2,-3},{13,6,-4},{-4,-3,-6}});
        m.show();
        Matrix n = m.getSubmatrix(0,2,0,2);
        System.out.println('\n');
        n.show();
    }
}
