package ddw.hw2;

public abstract class MatrixPR {

    protected SparseMatrix matrix;
    protected SparseVector<Double> pageRankVector, pageRankVectorPrev;
    protected Double dampingFactor = 0.9;

    public MatrixPR(SparseMatrix matrix) {

        this.matrix = matrix;
        pageRankVector = new SparseVector(matrix.getColsCount());
        pageRankVectorPrev = new SparseVector(matrix.getColsCount());

        double val = (double)1/pageRankVector.dim;
        for (int i = 0; i < pageRankVector.dim; i++) {
                pageRankVector.set(i, val);
        }

    }

    public MatrixPR(SparseMatrix matrix, double dampingFactor) {
        this(matrix);
        this.dampingFactor = dampingFactor;
    }

    public SparseMatrix getMatrix() {
        return matrix;
    }

    private void checkSum() {
        double sum = 0;
        for (Integer key : pageRankVector.getKeys()) {
            sum += pageRankVector.get(key);
        }
        System.out.println("Sum: " + sum);
    }
    
    public void pageRankDiff() {
        String format = String.format("%.15f", pageRankVector.minus(pageRankVectorPrev).l1());
        System.out.println("l1 diff: "+format);
        MyLogger.log(format);
        
    }
    
    public SparseVector getPageRankVector() {
        return pageRankVector;
    }

    public void countPr(int iterations) {
        Timer t = Timer.getInstance();
        t.put("PageRank");
        //pageRankVector.print();
        //System.out.println("=============================================");

        for (int i = 1; i <= iterations; i++) {
            t.put("PageRank Iteration " + i);
            countPageRankIterationSparse();
            //pageRankVector.print();
            pageRankDiff();            
            checkSum();
            pageRankVectorPrev.clone(pageRankVector);
            t.put("PageRank Iteration " + i);
            //System.out.println("=============================================");
        }
        t.put("PageRank");
    }

    private void countPageRankIteration() {
        pageRankVector = pageRankVector.times(matrix);
    }

    private void countPageRankIterationSparse() {
        if (this.dampingFactor == null) {
            throw new IllegalStateException("Damping factor not provided for PageRank calculation.");
        }
        
        SparseVector<Double> a = MatrixReader.getInstance().getA();
        
        SparseVectorE etn = new SparseVectorE(a.getDim());
        etn.setCoef((double)1/a.getDim());
        
        pageRankVector = pageRankVector.times(dampingFactor).times(matrix).plus( // pi*alpha*H + 
                etn.times( // 1/n * e^t *
                    SparseVector.dot(a, pageRankVector.times(dampingFactor))//(a*pi*alpha 
                    +                                                       //+
                    1-dampingFactor                                         //1-alpha )
                )
        );
    }

}
