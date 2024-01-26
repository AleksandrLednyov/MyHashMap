import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class MyHashMapTest {
    static MyHashMap<Integer, String> map;

    public static void createNewMyHashMap() {
        map = new MyHashMap<>();
        for (int i = 0; i < 100000; i++) {
            map.put(i, "value " + i);
        }
    }

    @Test
    public void MapShouldPutValue() {
        map.clear();
        map = new MyHashMap<>();
        for (int i = 0; i < 100000; i++)
            map.put(i, "value " + i);
        for (int i = 0; i < 100000; i++)
            Assert.assertEquals("value " + i, map.get(i));
    }

    @Test
    public void MapShouldGetValue() {
        createNewMyHashMap();
        for (int i = 0; i < 100000; i++)
            Assert.assertEquals("value " + i, map.get(i));
    }

    @Test
    public void MapShouldRemoveValue() {
        createNewMyHashMap();
        for (int i = 0; i < 100000; i++) {
            map.remove(i);
            Assert.assertNull(map.get(i));
        }
    }

    @Test
    public void MapShouldCalculateHashCode() {
        Assert.assertEquals(Objects.hash(12345), map.hashCode(12345));
    }
}
