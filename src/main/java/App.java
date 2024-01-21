public class App {
    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(-225, "Alex");
        System.out.println(map);
        System.out.println(map.get(-225));
        map.remove(-225);
        System.out.println(map);
    }
}
