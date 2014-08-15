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
    
    public Markov(String fileName) throws IOException {
        file = new File(fileName);
        init();
    }
    
    public static void main(String[] args) throws IOException {
        Markov markov = new Markov("src/markov/text.txt");
        System.out.println(markov.generate(250));
    }
    
    public void init() throws IOException {
        chain = new HashMap<Pair<String, String>, Pair<String, Integer>>();
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String a, b = next(reader), c = next(reader);
        Pair<String, String> wordPair;
        Pair<String, Integer> numPair;
        
        while(true) {
            a = b;
            b = c;
            c = next(reader);
            
            if(c == null)
                break;
            
            wordPair = new Pair<>(a, b);
            numPair = chain.get(wordPair);
            if(numPair == null)
                numPair = new Pair<String, Integer>(c, 0);
            else
                numPair.b++;
            
            chain.put(wordPair, numPair);
        }
    }
    
    public String generate(int length) {
        StringBuilder text = new StringBuilder();
        Entry<Pair<String, String>, Pair<String, Integer>>[] values = 
                chain.entrySet().toArray(new Entry[chain.entrySet().size()]);
        int random = (int)(Math.random() * values.length);
        String a, b;
        Pair<String, Integer> current = null;
        
        do {
            a = values[(int)(Math.random() * values.length)].getKey().a;
            b = values[(int)(Math.random() * values.length)].getKey().b;
            current = chain.get(new Pair<String, String>(a, b));
        } while(current == null);
        
        for(int i = 0; i < length; i++) {
            text.append(b).append(" ");
            
            a = b;
            b = current.a;
            
            current = chain.get(new Pair<String, String>(a, b));
        }
        
        return text.toString();
    }
    
    public static int sumTo(int value) {
        int sum = 0;
        for(int i = 1; i <= value; i++)
            sum += i;
        return sum;
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
