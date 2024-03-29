package net.bondarik.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Map<Integer, Integer> map = new HashMap<>();
        map.compute(1, (k, v) -> v == null ? null : v + 1);

        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.compute(1, (k, v) -> v == null ? null : v + 1);
        concurrentHashMap.merge(1, 1, Integer::sum);
    }
}
