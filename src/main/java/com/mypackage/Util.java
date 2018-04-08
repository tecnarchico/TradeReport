package com.mypackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {

	public final static DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
	
	// https://stackoverflow.com/a/2581754
	public final static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map, boolean asc) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        if (asc) list.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        else list.sort((o1, o2) -> - o1.getValue().compareTo(o2.getValue()));

        
        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
