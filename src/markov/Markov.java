package markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Markov {
    public File file;
    public Map<Pair<String, String>, Pair<String, Integer>> chain;
    
    public Markov(String fileName) {
        file = new File(fileName);
    }
    
    public void init() throws IOException {
        chain = new HashMap<>();
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String a, b = next(reader), c = next(reader);
        Pair<String, String> wordPair;
        Pair<String, Integer> numPair;
        
        while(true) {
            a = b;
            b = c;
            c = next(reader);
            
            if(c == null)
                return;
            
            wordPair = new Pair<>(a, b);
            numPair = chain.get(wordPair);
            if(numPair == null)
                numPair = new Pair<String, Integer>(c, 0);
            else
                numPair.b++;
            
            chain.put(wordPair, numPair);
        }
    }
    
    public String generate() {
        StringBuilder text = new StringBuilder();
        Entry<Pair<String, String>, Pair<String, Integer>>[] values = 
                chain.entrySet().toArray(new Entry[chain.entrySet().size()]);
        int random = (int)(Math.random() * values.length);
        StringBuilder value = new StringBuilder();
        String a = null, b = null;
    }
    
    public static String next(BufferedReader reader) throws IOException {
        String value = "";
        int i;
        
        while((i = reader.read()) != ' ') {
            if(i == -1)
                return null;
            value += (char)i;
        }
        
        return value;
    }
    
    public class Pair<U, V> {
        public U a;
        public V b;
        
        public Pair(U _a, V _b) {
            a = _a;
            b = _b;
        }
    }
}
