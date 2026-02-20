
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

        // now find our starting position when we create an iterator
        public Iter ()
        {
            //Is our occupied spot empty?
            while(table[x].isEmpty() && (x + 1) < size)
            {
                // find when its nOT to start :)
                x++;
            }
        }

        @Override
        public boolean hasNext() {
            //Is there a next item with us?
            if(y + 1 < table[x].size())
            {
                return true;
            } else 
            {
                //If we are done with the current array, find the next 
                //        if the next one is empty, we go to it
                int fx = x; // to not alter our actual position, we use this future x to check
                while(table[fx].isEmpty() && fx < size - 1)
                {
                    fx++;
                }
                // NOW we stop if we hit a none empty array OR we reached the end, with still nothing good
                if(table[fx].isEmpty())
                {
                    return false;
                } else {
                    return true;
                }
            }
        }


        @Override
        public Object next() {
            if(hasNext())
            {
                //Is there a next item with us?
                if(y + 1 < table[x].size())
                {
                    y++;
                    return table[x].get(y);
                } else 
                {
                    // Reset y because were not in the same arraylist anymore
                    y = 0;
                    //If we are done with the current array, find the next 
                    //        if the next one is empty, we go to it
                    while(table[x].isEmpty() && x < size - 1)
                    {
                        x++;
                    }
                    // NOW we stop if we hit a none empty array OR we reached the end, with still nothing good
                    if(table[x].isEmpty())
                    {
                        return null;
                    } else {
                        return table[x].get(y);
                    }
                }
            }

            return null;
        }

        public void remove()
        {
            // Complete this function
        }
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