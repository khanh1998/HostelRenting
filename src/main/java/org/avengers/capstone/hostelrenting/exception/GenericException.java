package org.avengers.capstone.hostelrenting.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author duattt on 10/17/20
 * @created 17/10/2020 - 11:57
 * @project youthhostelapp
 */
public class GenericException extends RuntimeException{

    public GenericException(Class clazz, String msg, String... searchParamsMap) {
        super(GenericException.generateMessage(clazz.getSimpleName(), msg, toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, String msg, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " " + msg + " " +
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