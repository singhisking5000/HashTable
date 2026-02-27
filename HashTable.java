
import java.util.*;

import javax.swing.text.AttributeSet;

import java.io.*;

public class HashTable {
    int size = 100;
    int items = 0;

    ArrayList<Payload>[] table = new ArrayList[size];
    //Methods you have to supply:

    public void put(Payload p) {
        int i = (p.key.hashCode() % size);
        if(table[i]== null){
            table[i] = new ArrayList<Payload>();
        }
        table[i].add(p);
        items++;
        if((items / size) > (2/3))
        {
            doubleSize();
        }
    }

    public void doubleSize()
    {
        //To take a snapshot of all of the items
        size *= 2;
        ArrayList<Payload>[] updatedTable = new ArrayList[size];

        Iter items = this.keys();
        
        while(items.hasNext())
        {
            
        }


        // Double the size
        table = updatedTable;
    }

    public Object get(String key) {
        //Hashcode the key to go straight to the point in storage
        int i = key.hashCode() % size;
        if (table[i] == null)
        {
            return new Payload("No Item found", "No Item Found");
        }
        for (Payload p : table[i])
        {
            if(p.key.equals(key))
            {
                return p;
            }
        }
        return new Payload("No Item found", "No Item Found");
    }

    public String remove(String key){
        int i = key.hashCode() % size;
        for (Payload p : table[i])
        {
            if(p.key.equals(key))
            {
                Payload temp = p;
                table[i].remove(p);
                items--;
                return "Item ID: " + temp.key + " was removed from the map";
            }
        }
        return "ERROR: Object not found";
    }


    public Iter keys() {
        return new Iter();
    }

    public void print(){
        String toPrint = "";
        
        Iter i = keys();
        //System.out.println(table[i.x].get(i.y).key + " at index " + i.x);

        while (i.hasNext())
        {
            Payload passed = i.next();
            toPrint += "(K: " + passed.key + ", V: " + passed.value + ") ";
            //System.out.println(toPrint);
        }

        System.out.println(toPrint + " <---- RESULT");
    }
    
    // COMPLETE THIS ITERATOR BEFORE PROCEEDING
    private class Iter implements Iterator<Payload>
    {
        // our position
        // y will ALWAYS be zero, unless we find a spot with collision
        public int x = 0;
        public int y = 0;
        private Payload prev = null; // <------ TO BE CONTINUED

        // now find our starting position when we create an iterator
        public Iter ()
        {
            //Is our occupied spot empty?
            // CAUSING NULL POINTER BELOW VVVVVVVVVVVVVVVV
            while((x + 1) < size)
            {
                if(table[x] == null || table[x].isEmpty())
                {
                    x++;
                } else 
                {
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            //Is there a next item with us?
            if(table[x] != null && y + 1 < table[x].size())
            {
                return true;
            } else 
            {
                //If we are done with the current array, find the next 
                //        if the next one is empty, we go to it
                int fx = x + 1; // to not alter our actual position, we use this future x to check
                while(((table[fx] == null) || (table[fx].isEmpty())) && fx < size - 1)
                {
                    fx++;
                }
                // NOW we stop if we hit a none empty array OR we reached the end, with still nothing good
                if(table[fx] == null || table[fx].isEmpty())
                {
                    return false;
                } else {
                    return true;
                }
            }
        }

        @Override
        public Payload next() {
            if(hasNext())
            {
                // If we have a next, we gotta remember our current...
                prev = table[x].get(y);
                //Is there a next item with us?
                if(table[x] != null && y + 1 < table[x].size())
                {
                    y++;
                    return table[x].get(y);
                } else 
                {
                    // Reset y because were not in the same arraylist anymore
                    y = 0;
                    //If we are done with the current array, find the next 
                    //        if the next one is empty, we go to it
                    x++;
                    while((table[x] == null || table[x].isEmpty()) && x < size - 1)
                    {
                        x++;
                    }
                    // NOW we stop if we hit a none empty array OR we reached the end, with still nothing good
                    if(table[x] == null || table[x].isEmpty())
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
            int i = prev.key.hashCode();
            for (Payload p : table[i])
            {
                if(p.key.equals(prev.key))
                {
                    table[i].remove(p);
                }
            }
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