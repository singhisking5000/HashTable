
public class Main
{
    public static void main(String[] args) {
        HashTable map = new HashTable();
        map.put(new Payload("17824", "Value with a key 1"));
        map.put(new Payload("18231", "a nEW value"));
        System.out.println(((Payload)map.get("17824")).value);
        System.out.println("dodo?");
    }
}