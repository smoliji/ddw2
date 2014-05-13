package ddw.hw2;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Timer {
    private static Timer instance = null;
    private Map<String, Date> timestamps = null;
    private Map<String, Long> results = null;
    
    private Timer() {        
        timestamps = new LinkedHashMap<>();
        results = new LinkedHashMap<>();
    }
    
    public static Timer getInstance() {
        if (instance == null)
            instance = new Timer();
        return instance;
    }
    
    public void put(String label) {
        if (timestamps.containsKey(label)) {
            results.put(label, new Date().getTime() - timestamps.get(label).getTime());
            timestamps.remove(label);
            System.out.println("Finished [" + label+"]");
        }
        else {
            System.out.println("Started  [" + label+"]");
            timestamps.put(label, new Date());
        }
    }
    
    public void print() {
        for (String label: results.keySet()) {
            System.out.println(label + " : " + results.get(label));
        }
    }
}
