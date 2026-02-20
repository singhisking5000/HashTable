
import java.util.*;

import javax.swing.text.AttributeSet;

import java.io.*;

public class HashTable {
    int size = 100;

    ArrayList<Payload>[] table = new ArrayList[size];
    //Methods you have to supply:

    public void put(Payload p) {
        int i = (p.key.hashCode() % size);
        //NOW regardless of a collision, we add it into this box, so any that are there are 
        //bundled!
        table[i].add(p);
    }

    public Object get(String key) {
        //Hashcode the key to go straight to the point in storage
        int i = key.hashCode();
        for (Payload p : table[i])
        {
            if(p.key.equals(key))
            {
                return p;
            }
        }
        return false;
    }

    public String remove(String key){
        int i = key.hashCode();
        for (Payload p : table[i])
        {
            if(p.key.equals(key))
            {
                String temp = p.key;
                table[i].remove(p);
                return temp;
            }
        }
        return "ERROR: Object not found";
    }


    public Iterator keys() {
        return null;
    }

    public void print(){
	
    }
    
    // COMPLETE THIS ITERATOR BEFORE PROCEEDING
    private class Iter implements Iterator
    {
        // our position
        // y will ALWAYS be zero, unless we find a spot with collision
        int x = 0;
        int y = 0;
        // now find our starting position
        public Iter ()
        {
            //Find the first spot in our hashtable thats occupied
            while(table[x].isEmpty())
            {
                x += 1;
            }
        }

        @Override
        public boolean hasNext() {
            //If there is more in our collumn, go there
            if(y + 1 < table[x].size())
            {
                return true;
            } else 
            {
                // If we are done here, move on to the next one while in bount
                while(table[x + 1].isEmpty() && (x + 1) < size)
                {
                    x += 1;
                }
            }
            
            
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasNext'");
        }

        @Override
        public Object next() {
            
            throw new UnsupportedOperationException("Unimplemented method 'next'");
        }
        // do a remove too
    }

	/**
	 * Loads this HashTable from a file named "Lookup.dat".
	 */
    public void load() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        
        // Open the file for reading
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            fileReader = new FileReader(f);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (FileNotFoundException e) {
            System.err.println("Cannot find input file \"Lookup.dat\"");
        }
        
        // Read the file contents and save in the HashTable
        try {
            while (true) {
                String key = bufferedReader.readLine();
                if (key == null) return;
                String value = bufferedReader.readLine();
                if (value == null) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                String blankLine = bufferedReader.readLine();
                if (!"".equals(blankLine)) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                put(new Payload(key, value));
            }
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        
        // Close the file when we're done
        try {
            bufferedReader.close( );
        }
        catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }

	/**
	 * Saves this HashTable onto a file named "Lookup.dat".
	 */
	public void save() {
        FileOutputStream stream;
        PrintWriter printWriter = null;
        Iterator iterator;
        
        // Open the file for writing
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            stream = new FileOutputStream(f);
            printWriter = new PrintWriter(stream);
        }
        catch (Exception e) {
            System.err.println("Cannot use output file \"Lookup.dat\"");
        }
       
        // Write the contents of this HashTable to the file
        iterator = keys();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            printWriter.println(key);
            String value = (String)get(key);
            value = removeNewlines(value);
            printWriter.println(value);
            printWriter.println();
        }
       
        // Close the file when we're done
        printWriter.close( );
    }
    
    /**
     * Replaces all line separator characters (which vary from one platform
     * to the next) with spaces.
     * 
     * @param value The input string, possibly containing line separators.
     * @return The input string with line separators replaced by spaces.
     */
    private String removeNewlines(String value) {
        return value.replaceAll("\r|\n", " ");
    }
}