
public class Main
{
    public static void main(String[] args) {
        HashTable map = new HashTable();
        map.put(new Payload("17824", "Value with a key"));
        map.put(new Payload("18231", "a nEW value"));
        map.put(new Payload("19203", " sometin"));
        map.put(new Payload("1", " really bizarre"));
        
        //COLLISION PAIR BELOW!
        map.put(new Payload("Aa", "Item 1"));
        map.put(new Payload("BB", "Item 2"));
        System.out.println(((Payload)map.get("17824")).value);
        System.out.println(map.remove("18231"));
        System.out.println(((Payload)map.get("748312")).value);
        System.out.println("We loop when we print");
        map.print();


        // TESTING items during a collision :)
        System.out.println(((Payload)map.get("Aa")).value);
        System.out.println(((Payload)map.get("BB")).value);
        System.out.println(map.remove("Aa"));
        System.out.println(((Payload)map.get("BB")).value);

        System.out.println("Completed without any errors!");
    }
}