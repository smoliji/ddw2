package ddw.hw2;

public class SparseMatrix {

    private int m;//rows
    private int n;//cols
    private SparseVector<SparseVector> rows;
    private SparseVector<SparseVector> cols;

    public SparseMatrix(int m, int n) {
        cols = new SparseVector<>(n);
        rows = new SparseVector<>(m);
        this.m = m;
        this.n = n;
    }

    public int getRowsCount() {
        return m;
    }

    public int getColsCount() {
        return n;
    }
    
    public SparseVector<SparseVector> getCols() {
        return cols;
    }
    public SparseVector<SparseVector> getRows() {
        return rows;
    }

    public SparseVector<Double> getCol(int i) {
        return cols.get(i);
    }

    public SparseVector<Double> getRow(int i) {
        return rows.get(i);
    }

    public double get(int i, int j) {
        Object out = rows.get(i).get(j);
        if (out == null) {
            return 0;
        }
        return (double) out;
    }

    public void set(int i, int j, double val) {
        if (rows.get(i) == null) {
            rows.set(i, new SparseVector<>(n));
        }
        if (cols.get(j) == null) {
            cols.set(j, new SparseVector<>(m));
        }
        rows.get(i).set(j, val);
        cols.get(j).set(i, val);
    }

    public SparseMatrix times(SparseMatrix m) {
        SparseMatrix o = new SparseMatrix(this.m, m.n);
        for (int i = 0; i < o.m; i++) {
            for (int j = 0; j < o.n; j++) {
                double mult = SparseVector.dot(rows.get(i), m.cols.get(j));
                if (mult != .0) {
                    o.set(i, j, mult);
                }
            }
        }
        return o;
    }

    public void print() {
        for (int i = 0; i < m; i++) {
            if (rows.get(i) != null) {
                rows.get(i).print();
            } else {
                for (int j = 0; j < n; j++) {
                    System.out.print("null ");
                }
                System.out.println("");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            if (rows.get(i) != null) {
                sb.append(rows.get(i).toString());
            } else {
                for (int j = 0; j < n; j++) {
                    sb.append(String.format("%9.4f ", 0.0));
                }
                
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }

}
