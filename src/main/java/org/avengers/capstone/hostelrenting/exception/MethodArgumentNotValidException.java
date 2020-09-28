package org.avengers.capstone.hostelrenting.exception;

import org.apache.commons.lang3.StringUtils;
import org.avengers.capstone.hostelrenting.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author duattt on 9/28/20
 * @created 28/09/2020 - 15:37
 * @project youthhostelapp
 */
public class MethodArgumentNotValidException extends RuntimeException{

    public MethodArgumentNotValidException(Class clazz, String... searchParamsMap) {
        super(MethodArgumentNotValidException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " has invalid param " +
                searchParams;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}
