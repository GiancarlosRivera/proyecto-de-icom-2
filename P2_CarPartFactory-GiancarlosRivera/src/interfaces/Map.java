package interfaces;

import java.io.PrintStream;
import java.util.Set;
import java.util.function.BiConsumer;
/**
 * 
 * @author Fernando J Bermudez (bermed28)
 *
 */
public interface Map<K,V> {
	public int size();
	public boolean isEmpty();
	public V get(K key);
	public V put(K key, V value);
	public V remove(K key);
	public boolean containsKey(K key);
	public void clear();
	public List<K> getKeys();
	public List<V> getValues();
	public void print(PrintStream out);
	
	/**
     * Returns a set view of the mappings contained in this map.
     *
     * @return a set view of the mappings contained in this map
     */
    Set<Entry<K, V>> entrySet();

    /**
     * Represents a map entry (key-value pair).
     *
     * @param <K> the type of keys maintained by this map
     * @param <V> the type of mapped values
     */
    interface Entry<K, V> {
        K getKey();
        V getValue();
    }
	/**
     * Returns the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key.
     *
     * @param key          the key whose associated value is to be returned
     * @param defaultValue the default mapping of the key
     * @return the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
     */
    default V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return (value != null) ? value : defaultValue;
    }
    /**
     * Performs the given action for each entry in this map until all entries
     * have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each entry.
     *               The first parameter of the action represents the key,
     *               and the second parameter represents the value.
     * @throws NullPointerException if the specified action is null
     */
    void forEach(BiConsumer<? super K, ? super V> action);
}