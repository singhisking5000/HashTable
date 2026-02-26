
public class Main
{
    public static void main(String[] args) {
        HashTable map = new HashTable();
        map.put(new Payload("17824", "Value with a key 1"));
        map.put(new Payload("18231", "a nEW value"));
        map.put(new Payload("19203", " sometin"));
        map.put(new Payload("1", " really bizarre"));
        System.out.println(((Payload)map.get("17824")).value);
        System.out.println(map.remove("18231"));
        System.out.println(((Payload)map.get("748312")).value);
        System.out.println("We loop when we print");
        map.print();
        System.out.println("Just chekcing we finish and dont get any loops!");
    }
}