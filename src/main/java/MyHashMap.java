import java.util.Objects;

/**
 * <strong>MyHashMap</strong> представляет собой упрощённую реализацию хэш-таблицы, т.е. массив с неизменяемым
 * количеством ячеек (bucket), равным 16. Каждая ячейка представляет собой цепочку связных элементов (entry)
 * вида LinkedList. Каждый элемент является парой ключ-значение (key-value) и обладает ссылкой на следующий
 * элемент цепочки. При отсутствии элементов в ячейке проставляется значение null. Хэширование так же упрощено
 * до стандартной хэш-функции класса Objects, параметром которой является только ключ (key) элемента.
 * Класс MyHashMap обладает методами put, get и remove.
 * Параметры класса MyHashMap являются обобщениями (generics) и обозначаются следующим образом:
 * @param <K> - ключ элемента (key)
 * @param <V> - значение элемента (value)
 */
public class MyHashMap<K,V> {
    /**
     * cap - вместимость (capacity) - по умолчанию 16 ячеек.
     */
    private int cap = 16;
    /**
     * table - хэш-таблица, содержащая в себе ячейки (bucket)
     */
    private final Bucket<K,V>[] table;

    /**
     * public MyHashMap() - дефолт-конструктор, осуществляющий создание ячеек (bucket) в хэш-таблице
     */
    public MyHashMap() {
        table = new Bucket[cap];
        for (int i = 0; i < cap; i++)
            table[i] = new Bucket<>();
    }

    // метод вычисляет значение хэш
    private int hashCode(K key) {
        return Objects.hash(key);
    }

    /**
     * Метод put добавляет элемент в таблицу. На входе он принимает параметры key (ключ элемента) и value (значение
     * элемента). Если ключ записываемой элемента идентичен ключу элемента, уже содержащегося в таблице, происходит
     * перезапись последнего с новым значением.
     * @param key - ключ добавляемого элемента
     * @param value  - значение добавляемого элемента
     */
    public void put(K key, V value) {
        int hash = hashCode(key);
        int bucket = Math.abs(hash%cap);
        table[bucket].add(hash, key, value);
    }

    /**
     * Метод get принимает на вход параметр key (ключ) и, если элемент с таким ключом содержится в таблице, возвращает
     * значение value данного элемента. Если элемент с таким ключом отсутствует, возвращает null.
     * @param key - ключ запрашиваемого элемента
     * @return - при наличии элемента возвращает значение (value), иначе null
     */
    public V get (K key) {
        int hash = hashCode(key);
        int bucket = Math.abs(hash%cap);
        return table[bucket].get(hash, key);
    }

    /**
     * Метод remove принимает на вход параметр key (ключ) и, если элемент с таким ключом содержится в таблице, удаляет
     * его, одновременно возвращая значение value удалённого элемента. Если элемент отсутствует, таблица остаётся
     * неизменной, а метод возвращает значение null.
     * @param key - ключ удаляемого элемента
     * @return  - при наличии элемента возвращает значение (value), иначе null
     */
    public V remove (K key) {
        int hash = hashCode(key);
        int bucket = Math.abs(hash%cap);
        return table[bucket].remove(hash, key);
    }

    /**
     * Переопределённый метод toString
     * @return - возвращает всю таблицу с каждой ячейкой (bucket) построчно
     */
    @Override
    public String toString() {
        for (int i = 0; i < cap; i++) {
            System.out.println(table[i]);
        }
        return "";
    }

    // класс, представляющий собой связный список узлов для хранения записей
    private static class Bucket<K,V> {
        private Entry<K,V> first;

        // метод добавляет узел (запись) в связный список
        void add(int hash, K key, V value) {
            if (first == null) {
                first = new Entry<>(hash, key, value);
                return;
            }
            Entry<K, V> temp = first;
            while (temp.next != null) {
                if (temp.hash == hash && temp.key.equals(key)) {
                    temp.changeValue(value);
                    return;
                }
                temp = temp.next;
            }
            if (temp.hash == hash && temp.key.equals(key)) {
                temp.changeValue(value);
            } else {
                temp.next = new Entry<>(hash, key, value);
            }
        }

        // метод возвращает значение записи по ключу
        V get(int hash, K key) {
            Entry<K, V> temp = first;
            while (temp != null) {
                if (temp.hash == hash && temp.key.equals(key))
                    return temp.value;
                else temp = temp.next;
            }
            return null;
        }

        // метод удаляет узел (запись) по ключу
        V remove(int hash, K key) {
            // проверка первого узла на null
            if (first == null)
                return null;
            // проверка первого узла на соответствие ключу
            if (first.hash == hash && first.key.equals(key) && first.next == null) {
                V v = first.value;
                first = null;
                return v;
            }
            if (first.hash == hash && first.key.equals(key) && first.next != null) {
                V v = first.value;
                first = first.next;
                return v;
            }
            // проверка остальных узлов на соответствие ключу, кроме последнего узла
            Entry<K,V> temp = first.next;
            Entry<K,V> prev = first;
            while (temp.next != null) {
                if (temp.hash == hash && temp.key.equals(key)) {
                    V v = temp.value;
                    prev.next = temp.next;
                    return v;
                }
                prev = temp;
                temp = temp.next;
            }
            // проверка последнего узла на соответствие ключу
            if (temp.hash == hash && temp.key.equals(key)) {
                V v = temp.value;
                prev.next = null;
                return v;
            }
            return null;
        }

        // переопределение метода toString
        @Override
        public String toString() {
            if (first == null)
                return null;
            Entry<K,V> temp = first;
            do {
                System.out.print("{" + temp + "}");
                temp = temp.next;
            } while (temp != null);
            return "";
        }
    }

    // Вложенный класс, представляющий собой узел для хранения записей
    private static class Entry<K,V> {
        int hash;
        K key;
        V value;
        Entry<K,V> next;

        // конструктор узла
        Entry(int hash, K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        // метод изменения значения в узле
        void changeValue(V value) {
            this.value = value;
        }

        // переопределение метода toString
        @Override
        public String toString() {
            return key + ": " + value;
        }
    }
}