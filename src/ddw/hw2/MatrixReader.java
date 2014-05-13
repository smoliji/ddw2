package ddw.hw2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixReader {
    
    protected SparseMatrix matrix = null;
    protected SparseVector<Double> a = null;
    private String filename = null;
    
    private static MatrixReader instance = null;
    
    public static MatrixReader getInstance() {
        if (instance == null){
            instance = new MatrixReader();
        }
        return instance;
    }
    
    public SparseMatrix getMatrix() { return matrix; }
    public SparseVector<Double> getA() { return a; }

    public void init(String fileName) {
        
        if (fileName.equals(filename)) return;
        
        Timer.getInstance().put("Scanning file "+fileName);
        
        FileInputStream fis = null;
        SparseMatrix matrixH = null;        
        try {
            String line;
            String[] tokens, xy;
            int docsCount, wp = 0;
            fis = new FileInputStream(fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));

            docsCount = Integer.parseInt(bf.readLine());
            matrixH = new SparseMatrix(docsCount, docsCount);
            a = new SparseVector<>(docsCount);

            while ((line = bf.readLine()) != null && wp < docsCount) {
                //if (!line.matches("^(\\d+:\\d+ ?)*$")) {
                //    throw new IllegalArgumentException();
                //}
                if (line.isEmpty()) {
                    a.set(wp, 1.);
                    wp++;
                    continue;
                }
                int outlinksCount = outlinksCount(line);
                tokens = line.trim().split(" ");
                for (String token : tokens) {
                    xy = token.split(":");
                    matrixH.set(wp, Integer.parseInt(xy[0]), (double) Integer.parseInt(xy[1]) / outlinksCount);
                }
                wp++;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatrixH.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MatrixH.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(MatrixH.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Timer.getInstance().put("Scanning file "+fileName);
        matrix = matrixH;
        filename = fileName;
    }
    
    public static int outlinksCount(String line) {
        String[] tokens = line.split(" ");
        String[] xy;
        int count = 0;
        for (String token : tokens) {
            xy = token.split(":");
            count += Integer.parseInt(xy[1]);
        }
        return count;
    }
    
    

}
