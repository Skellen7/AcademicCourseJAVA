package MatrixPac;

public class Matrix {
    double[] data;
    int rows;
    int cols;

    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows*cols];
    }

    Matrix(double[][] d){
        rows = d.length;
        int col_max = 0;
        for(int i=0; i<rows; i++){
            if(d[i].length>col_max) col_max=d[i].length;
        }
        cols = col_max;

        data = new double[rows*cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                data[cols*i+j]=0;
            }
        }
        for(int i=0; i<rows; i++){
            for(int j=0; j<d[i].length; j++){
                data[cols*i+j]=d[i][j];
            }
        }

    }

    double[][] asArray(){
        double array[][] = new double[rows][cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                array[i][j] = data[i*cols+j];
            }
        }
        return array;
    }

    double get(int r, int c){
        return data[r*cols+c];
    }

    void set(int r, int c, double value){
        data[r*cols+c] = value;
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for(int i=0; i<rows; i++){
            buf.append("[");
            for(int j=0; j<cols; j++){
                buf.append("[");
                buf.append(data[cols*i+j]);
                if(j!=cols-1) buf.append(" ");
            }
            buf.append("]");
            buf.append("\n");
        }
        buf.append("]");
        return buf.toString();
    }

    void reshape(int newRows, int newCols){
        if(rows*cols != newRows*newCols){
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        }
        else{
            cols = newCols;
            rows = newRows;
        }
    }

    int[] shape(){
        int dimension[] = new int[2];
        dimension[0] = rows;
        dimension[1] = cols;
        return dimension;
    }

    Matrix add(Matrix m){
        if(this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException(String.format("Dimension of matrixes are not equal"));
        }
        else{
            for(int i=0; i<rows; i++){
                for(int j=0; j<rows; j++){
                    this.data[i*cols+j]+=m.data[i*cols+j];
                }
            }
        }
        return this;
    }

    Matrix sub(Matrix m){
        if(this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException(String.format("Dimension of matrixes are not equal"));
        }
        else{
            for(int i=0; i<rows; i++){
                for(int j=0; j<rows; j++){
                    this.data[i*cols+j]-=m.data[i*cols+j];
                }
            }
        }
        return this;
    }

    Matrix mul(Matrix m){
        if(this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException(String.format("Dimension of matrixes are not equal"));
        }
        else{
            for(int i=0; i<rows; i++){
                for(int j=0; j<rows; j++){
                    this.data[i*cols+j]*=m.data[i*cols+j];
                }
            }
        }
        return this;
    }

    Matrix div(Matrix m){
        if(this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException(String.format("Dimension of matrixes are not equal"));
        }
        else{
            for(int i=0; i<rows; i++){
                for(int j=0; j<rows; j++){
                    if(m.data[i*cols+j]==0) throw new RuntimeException(String.format("You cannot divide by 0"));
                    else this.data[i*cols+j]/=m.data[i*cols+j];
                }
            }
        }
        return this;
    }

    Matrix add(double w){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                data[i*cols+j]+=w;
            }
        }
        return this;
    }

    Matrix sub(double w){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                data[i*cols+j]-=w;
            }
        }
        return this;
    }

    Matrix mul(double w){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                data[i*cols+j]*=w;
            }
        }
        return this;
    }

    Matrix div(double w){
        if(w==0) throw new RuntimeException(String.format("You cannot divide by 0"));
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                data[i*cols+j]+=w;
            }
        }
        return this;
    }

    Matrix dot(Matrix m){
        if(cols!=m.rows) throw new RuntimeException(String.format("Wrong sizes"));
        double sum;
        Matrix c = new Matrix(rows, m.cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                sum=0;
                for(int k=0; k<m.cols; k++){
                    sum+=data[i+k*cols]*m.data[k+j*m.cols];
                }
                c.data[i+c.cols*j]=sum;
            }
        }
        return c;
    }

    double frobenius(){
        double frob=0;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                frob+=data[i+cols*j]*data[i+cols*j];
            }
        }
        return frob;
    }



    void show() {
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                System.out.print(data[cols*i+j]+" ");
            }
            System.out.println();
        }
    }

    Matrix max(int axis){
        if(this.rows==0 && this.cols==0) return new Matrix(0,0);

        Matrix m;
        if(axis==1) {
            double max;
            m = new Matrix(this.rows, 1);
            for (int i = 0; i < rows; i++) {
                max = this.data[i*cols];
                for (int j = 0; j < cols; j++) {
                    if (data[i * cols + j] > max) max = data[i * cols + j];
                }
                m.data[i] = max;
            }
        }
        else if(axis==-1){
            m = new Matrix(1,1);
            double max=this.data[0];
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    if(data[i*cols+j]>max) max=data[i*cols+j];
                }
            }
            m.data[0]=max;
        }
        else if(axis==0){
            double max;
            m = new Matrix(1, this.cols);
            for(int i=0; i< cols; i++){
                max = this.data[i];
                for(int j=0; j<rows; j++){
                    if(this.data[cols*j+i]>max) max=data[cols*j+i];
                }
                m.data[i]=max;
            }
        }
        else m = new Matrix(0,0);
        return m;
    }

    /**
     * Returns a submatrix with upper left corner at (fromRow, fromCol)
     * The shape of the matrix is (toRow-fromRow) x (toCol-fromCol)
     * @param fromRow
     * @param toRow
     * @param fromCol
     * @param toCol
     * @return resulting submatrix
     */

    Matrix getSubmatrix(int fromRow, int toRow, int fromCol, int toCol){
        if(fromRow>=toRow)throw new RuntimeException("Reversed range");
        if(fromCol>=toCol)throw new RuntimeException("Reversed range");
        if(toRow>rows)throw new RuntimeException("Bad range");
        if(toCol>cols)throw new RuntimeException("Bad range");
        Matrix m = new Matrix(toRow-fromRow,toCol-fromCol);
        for(int i=fromRow;i<toRow;i++){
            for(int j=fromCol;j<toCol;j++){
                m.set(i-fromRow,j-fromCol,get(i,j));
            }
        }
        return m;
    }
}
