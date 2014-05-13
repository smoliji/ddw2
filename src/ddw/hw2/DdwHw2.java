package ddw.hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DdwHw2 {

    public static void main(String[] args) {

        String bulk = "v2-hostgraph_weighted.graph-txt";
        String test1 = "test1.txt";
        String test2 = "test2.txt";
        String test3 = "test3.txt";
        
        if (args.length != 3) {
            System.err.println("Wrong input parameters - required 3 parameters");
            System.err.println("Usage: java -jar inputFile rounds dumpingFactor");            
            System.exit(1);
        }
        
        String inputFile = args[0];
        int rounds = new Integer(args[1]);
        double dampingFactor = new Double(args[2]);
        
        Timer t = Timer.getInstance();        
        
        MatrixH result = calculateWithMatrixH(inputFile, rounds, dampingFactor);        
        
        t.put("SaveToFile");
        saveResult(hostnames("full_hostids.txt"), result.getPageRankVector(), "pageRanks.txt");
        t.put("SaveToFile");
        
        t.print();
       
    }

    private static MatrixH calculateWithMatrixH(String inputFile, int rounds, double d) {
        MatrixH h = new MatrixH(inputFile);
        h.dampingFactor = d;
        h.countPr(rounds);        
        return h;   
    }   
    
    private static class Pair<String, Double>{
        public String a;
        public Double b;
        public Pair(String a, Double b) {this.a = a; this.b = b;}
    }
    private static class PairComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair o1, Pair o2) {
            return (int) Math.signum((Double)o1.b - (Double)o2.b);
        }
    }
    
    private static void saveResult(ArrayList<String> hostnames, SparseVector<Double> pageRankVector, String outputFile) {
        
        ArrayList<Pair> sorted = new ArrayList<>();
        for (int i = 0; i < hostnames.size(); i++) {
            sorted.add(new Pair<>(hostnames.get(i), pageRankVector.get(i)));
        }
        Collections.sort(sorted, new PairComparator() );
        
        try {
            FileOutputStream fous = new FileOutputStream(new File(outputFile));
            for (int i = 0; i < sorted.size(); i++) {
                fous.write((sorted.get(i).a+" " + String.format("%.20f", sorted.get(i).b)+"\r\n").getBytes());
            }
            fous.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File full_hostids.txt with hostnames not found.");
        } catch (IOException ex) {
            Logger.getLogger(DdwHw2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArrayList<String> hostnames(String filename) {
        ArrayList<String> output = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filename));
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = bf.readLine()) != null) {
                output.add(line);
            }
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DdwHw2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DdwHw2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
}
