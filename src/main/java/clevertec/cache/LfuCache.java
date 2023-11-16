package clevertec.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация стратегии кэширования "Least Frequently Used" (LFU).
 * Этот кэш удаляет элементы, которые используются наименее часто.
 *
 * @param <K> тип ключей, поддерживаемых этим кэшем
 * @param <V> тип значений, хранящихся в кэше
 */
@Slf4j
public class LfuCache<K, V> implements Cache<K, V>{

    private final int capacity;
    private final Map<K, V> mainMap;
    private final Map<K, Integer> freqMap;

    /**
     * Конструктор для создания кэша LFU с заданной вместимостью.
     *
     * @param capacity максимальное количество элементов, которое может хранить кэш
     */
    public LfuCache(int capacity) {
        this.capacity = capacity;
        this.mainMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        log.info("LFU Cache initialized with capacity: {}", capacity);
    }

    /**
     * Вставляет или обновляет значение, связанное с указанным ключом.
     * Если кэш заполнен, удаляется элемент, который используется наименее часто.
     *
     * @param key   ключ, с которым связано указанное значение
     * @param value значение, которое должно быть связано с указанным ключом
     */
    @Override
    public void put(K key, V value) {
        if (mainMap.containsKey(key)) {
            freqMap.put(key, freqMap.get(key) + 1);
            mainMap.put(key, value);
            log.debug("Updated key: {}, Frequency: {}", key, freqMap.get(key));
        } else {
            if (mainMap.size() >= capacity) {
                Optional<K> freqKey = freqMap.entrySet().stream()
                        .min(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey);
                freqKey.ifPresent(f -> {
                    mainMap.remove(f);
                    freqMap.remove(f);
                });
            }
            mainMap.put(key, value);
            freqMap.put(key, 1);
            log.debug("Added new key: {}, capacity: {}", key,  capacity);
        }
    }

    /**
     * Возвращает значение, связанное с указанным ключом, или {@code null}, если в кэше нет значения.
     *
     * @param key ключ, значение которого нужно вернуть
     * @return значение, связанное с указанным ключом, или {@code null}, если в кэше нет значения
     */
    @Override
    public V get(K key) {
        if (!mainMap.containsKey(key)) {
            log.debug("Key not found: {}", key);
            return null;
        }
        freqMap.put(key, freqMap.get(key) + 1);
        log.debug("Retrieved key: {}, Frequency: {}", key, freqMap.get(key));
        return mainMap.get(key);
    }

    /**
     * Удаляет значение для ключа из кэша, если оно присутствует.
     *
     * @param key ключ, значение которого должно быть удалено из кэша
     */
    @Override
    public void delete(K key) {
        if (mainMap.containsKey(key)) {
            mainMap.remove(key);
            freqMap.remove(key);
            log.debug("Deleted key: {}", key);
        }
    }
}
