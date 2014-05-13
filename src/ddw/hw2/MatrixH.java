package ddw.hw2;

public class MatrixH extends MatrixPR {

    public MatrixH(String fileName) {
        super(readMatrixFromFile(fileName));
    }
    public MatrixH(String fileName, Double dampingFactor) {
        super(readMatrixFromFile(fileName), dampingFactor);
    }

    public static SparseMatrix readMatrixFromFile(String fileName) {
        MatrixReader mr = MatrixReader.getInstance();
        mr.init(fileName);
        return mr.matrix;
    }

    

}
