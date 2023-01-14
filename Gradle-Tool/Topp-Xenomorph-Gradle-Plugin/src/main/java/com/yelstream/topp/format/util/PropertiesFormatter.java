package com.yelstream.topp.format.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Formatter of properties.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PropertiesFormatter {

    public static final String DEFAULT_LINE_PREFIX="    ";

    public static final boolean DEFAULT_SHOW_INDEX=true;

    public static final boolean DEFAULT_ALIGN_INDEXES=true;

    public static final String DEFAULT_INDEX_SEPARATOR=") ";

    public static final boolean DEFAULT_ALIGN_KEYS=true;

    public static final String DEFAULT_KEY_VALUE_SEPARATOR=" = ";

    public static final boolean DEFAULT_SORT_BY_KEY=true;

    @lombok.Builder.Default
    private final String linePrefix=DEFAULT_LINE_PREFIX;

    @lombok.Builder.Default
    private final boolean showIndex=DEFAULT_SHOW_INDEX;

    @lombok.Builder.Default
    private final boolean alignIndexes=DEFAULT_ALIGN_INDEXES;

    @lombok.Builder.Default
    private final String keyValueSeparator=DEFAULT_KEY_VALUE_SEPARATOR;

    @lombok.Builder.Default
    private final String indexSeparator=DEFAULT_INDEX_SEPARATOR;

    @lombok.Builder.Default
    private final boolean alignKeys=DEFAULT_ALIGN_KEYS;

    @lombok.Builder.Default
    private final boolean sortByKey=DEFAULT_SORT_BY_KEY;

    public <V> String format(Map<String,V> properties) {
        StringBuilder sb=new StringBuilder();

        if (properties!=null) {
            String indexFormat = createIndexFormat(properties, showIndex, indexSeparator);
            String keyValueFormat = createKeyValueFormat(properties,alignKeys, keyValueSeparator);
            String lineFormat = "%s%s%s";

            Map<String,V> sortedProperties=sortByKey?sortByKey(properties):properties;

            final int[] index = {1};
            sortedProperties.forEach((key, value) -> {
                if (!sb.isEmpty()) {
                    sb.append(String.format("%n"));
                }
                String formattedPrefix=String.format(indexFormat, index[0]);
                String formattedKeyValue=String.format(keyValueFormat, key, value);
                String formattedLine=String.format(lineFormat, linePrefix, formattedPrefix, formattedKeyValue);
                sb.append(formattedLine);
                index[0]++;
            });
        }

        return sb.toString();
    }

    private static <V> String createIndexFormat(Map<String,V> properties,
                                                boolean showIndex,
                                                String indexSeparator) {
        String format="";
        if (showIndex) {
            int size=properties.size();
            int fieldLength=Integer.toString(size).length();

            format="%"+fieldLength+"d"+indexSeparator;
        }
        return format;
    }

    private static <V> String createKeyValueFormat(Map<String,V> properties,
                                                   boolean alignKeys,
                                                   String keyValueSeparator) {
        int fieldLength=0;
        if (alignKeys) {
            fieldLength=properties.keySet().stream().map(String::length).max(Integer::compare).orElse(0);
        }
        return "%1$-"+fieldLength+"s"+keyValueSeparator+"%2$s";
    }

    private static <V> SortedMap<String,V> sortByKey(Map<String,V> properties) {
        return sortByKey(properties,String::compareTo);
    }

    private static <K,V> SortedMap<K,V> sortByKey(Map<K,V> properties,
                                                  Comparator<? super K> comparator) {
        TreeMap<K,V> sortedProperties=null;
        if (properties!=null) {
            sortedProperties=new TreeMap<>(comparator);
            sortedProperties.putAll(properties);
        }
        return sortedProperties;
    }
}
