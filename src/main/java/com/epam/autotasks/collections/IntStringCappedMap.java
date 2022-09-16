package com.epam.autotasks.collections;

import java.util.*;

class IntStringCappedMap extends AbstractMap<Integer, String> {

    private final long capacity;
    private long currentCapacity;


    LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
    //SimpleEntry<Integer, String>[] entries;

    public IntStringCappedMap(final long capacity) {
        this.capacity = capacity;
    }

    public long getCapacity() {
        return capacity;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String get(final Object key) {
        return map.get(key);
    }

    @Override
    public String put(final Integer key, final String value) {
        if(value.length() > capacity) throw new IllegalArgumentException();

        //якщо вже існує дублікат, то замінити його значення на нове
        if(map.containsKey(key)) {
            //System.out.println("dubl = " + key + " / " + value);

            currentCapacity -= map.get(key).length();
            map.remove(key);

            map.put(key, value);
            currentCapacity += value.length();

            ifCapacityOutOfRange();
            return key.toString();
        }


        String putObj = map.put(key, value);
        currentCapacity += value.length();

        ifCapacityOutOfRange();

        //System.out.println("map = " + map);
        return putObj;

    }

    private void ifCapacityOutOfRange (){
        //якщо результуючий розмір перевищує capacity
        while(currentCapacity > capacity)
        {
            Iterator<Entry<Integer, String>> iterator = map.entrySet().iterator();
            if(iterator.hasNext())
            {
                Entry<Integer, String> value = iterator.next();
                //System.out.println("removeFirst = " + value.getKey() +" / " + currentCapacity + " / " + capacity);
                //iterator.next();
                currentCapacity -= value.getValue().length();
                iterator.remove();
            } else break;
        }
    }
    @Override
    public String remove(final Object key) {
        String remuveObj = map.remove(key);

        if(remuveObj != null) currentCapacity -= remuveObj.length();
        return remuveObj;
    }

    @Override
    public int size() {
        return map.size();
    }

}
