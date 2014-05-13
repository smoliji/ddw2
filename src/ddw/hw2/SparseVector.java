package ddw.hw2;

import static java.lang.Math.abs;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class SparseVector<T> {

    protected TreeMap<Integer, T> map;
    protected int dim;

    public SparseVector(int n) {
        map = new TreeMap<>();
        this.dim = n;
    }
    
    public SparseVector(SparseVector<T> v) {
        map = new TreeMap<>(v.map);
        for (Integer i: map.keySet())
            map.put(i, v.get(i));
    }
    
    public void clone (SparseVector<T> v) {
        purge();
        for (Integer i: v.getKeySet())
            set(i, v.get(i));
    }

    public int size() {
        return map.size();
    }

    public boolean set(int index, T value) {
        if (index >= dim) {
            return false;
        }
        map.put(index, value);
        return true;
    }

    public void unset(int index) {
        map.remove(index);
    }

    public T get(int key) {
        return map.get(key);
    }

    public int getDim() {
        return dim;
    }

    public void purge() {
        this.map.clear();
    }
    
    public Set<Integer> getKeySet() {
        return map.keySet();
    }

    public Integer[] getKeys() {
        Integer[] integerArr = new Integer[map.keySet().size()];
        return map.keySet().toArray(integerArr);
    }

    public SparseMatrix asMatrix() {
        SparseMatrix m = new SparseMatrix(1, dim);
        for (Integer key : getKeys()) {
            m.set(0, key, (Double) get(key));
        }
        return m;
    }

    public SparseVector<Double> times(SparseMatrix m) {
        SparseVector<Double> output = new SparseVector(m.getColsCount());
        for (int i = 0; i < dim; i++) {
            double val = dot(this, m.getCol(i));
            if (val != 0.0) {
                output.set(i, val);
            }
        }
        return output;
    }
    
    public SparseMatrix times(SparseVector<Double> v) {
        if (dim != v.dim) throw new IllegalArgumentException("Vector dimensions differ.");
        SparseMatrix output = new SparseMatrix(dim, dim);
        for (int i = 0; i < output.getRowsCount(); i++) {
            for (int j = 0; j < output.getColsCount(); j++) {
                double mult = get(i)==null || v.get(i) == null?0.0:(Double)get(i)*v.get(j);
                if (mult != .0) {
                    output.set(i, j, mult);
                }
            }
        }
        return output;
    }

    public static double dot(SparseVector a, SparseVector b) {
        double o = 0;
        if (a == null || b == null) {
            return o;
        }
        SparseVector spars = a;
        SparseVector dens = b;
        if (a.size() > b.size()) {
            spars = b;
            dens = a;
        }
        for (Integer i : spars.getKeys()) {
            if (dens.get(i) != null && (Double)dens.get(i) > 0) {
                o += (Double)dens.get(i) * (Double)spars.get(i);
            }
        }
        return o;
    }

    public SparseVector<Double> times(double d) {
        SparseVector<Double> output = new SparseVector<>(dim);
        if (d == 0.0) {
            return output;
        }
        for (Integer key : getKeys()) {
            output.set(key, d * (Double) get(key));
        }
        return output;
    }

    public SparseVector<Double> plus(SparseVector<Double> v) {
        SparseVector<Double> output = new SparseVector<>(dim);
        Set<Integer> keys = new HashSet<>();
        keys.addAll(v.getKeySet());
        keys.addAll(this.getKeySet());        
        for (Integer key: keys) {
            output.set(key, ((Double) get(key) == null ? 0.0 : ((Double) get(key))) + (v.get(key) == null ? 0.0 : v.get(key)));
        }
        return output;
    }
    
    public SparseVector<Double> minus(SparseVector<Double> v) {
        SparseVector<Double> output = new SparseVector<>(dim);
        Set<Integer> keys = new HashSet<>();
        keys.addAll(v.getKeySet());
        keys.addAll(this.getKeySet());        
        for (Integer key: keys) {
            output.set(key, ((Double) get(key) == null ? 0.0 : ((Double) get(key))) - (v.get(key) == null ? 0.0 : v.get(key)));
        }
        return output;
    }
    
    public Double l1() {
        double out = 0;
        for (Integer i: getKeySet())
            out += abs((Double)get(i));
        return out;
    }

    public void print() {
        for (int i = 0; i < dim; i++) {
            System.out.printf("%9.4f ", get(i));
        }
        System.out.println("");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            sb.append(String.format("%9.4f ", (Double)get(i)));
        }
        return sb.toString();
    }

}
