package markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class Markov {
    public File file;
    public Map<Pair, List<String>> wordMap;
    
    public Markov(String fileName) throws IOException {
        file = new File(fileName);
        init();
    }
    
    public static void main(String[] args) throws IOException {
        Markov markov = new Markov("text.txt");
        System.out.println(markov.generate(100));
    }
    
    public void init() throws IOException {
        wordMap = new HashMap<Pair, List<String>>();
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String a, b = next(reader), c = next(reader);
        Pair wordPair;
        List<String> values;
        
        while(true) {
            a = b;
            b = c;
            c = next(reader);
            if(c == null)
                break;
            
            wordPair = new Pair(a, b);
            values = wordMap.get(wordPair);
            if(values == null) {
                values = new ArrayList<>();
                values.add(c);
                wordMap.put(wordPair, values);
            } else
                values.add(c);
        }
    }
    
    public String generate(int length) {
        StringBuilder text = new StringBuilder();
        Entry<Pair, List<String>>[] values = 
                wordMap.entrySet().toArray(new Entry[wordMap.size()]);
        int random = (int)(Math.random() * values.length);
        String a = values[random].getKey().a, b = values[random].getKey().b;
        List<String> current;
        
        for(int i = 0; i < length; i++) {
            current = wordMap.get(new Pair(a, b));
            
            text.append(a).append(" ");
            
            a = b;
            b = current.get((int)(Math.random() * current.size()));
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
    
    public class Pair {
        public String a, b;
        
        public Pair(String _a, String _b) {
            a = _a;
            b = _b;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o==null || !(o instanceof Pair))
                return false;
            
            Pair p = (Pair) o;
            
            return a.equals(p.a) && b.equals(p.b);
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Objects.hashCode(a);
            hash = 67 * hash + Objects.hashCode(b);
            return hash;
        }
    }
}
