
package ddw.hw2;

public class SparseVectorE extends SparseVector {
    
    protected double coef; 
    
    private Integer[] keys = null;

    public SparseVectorE(int n) {
        super(n);
        coef = 1;
        keys = new Integer[dim];
        for (int i = 0; i < dim; i++)
            keys[i] = i;
    }
    
    public void setCoef (double value) {
        coef = value;
    }
    
    @Override
    public Double get(int key) {        
        if (key > dim) return 0.0;
        return 1*coef;        
    }

    @Override
    public Integer[] getKeys() {
        return keys;
    }
    
    
    
    
    
}
