import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyHashMapTest {
    static MyHashMap<Integer, String> map;

    @BeforeClass
    public static void createNewMyHashMap() {
        map = new MyHashMap<>();
    }

    @Test
    public void MapShouldGetValue() {
        map.put(5, "some text");
        Assert.assertEquals("some text", map.get(5));
    }

    @Test
    public void MapShouldRemoveValue() {
        map.remove(5);
        Assert.assertNull(map.get(5));
    }
}
