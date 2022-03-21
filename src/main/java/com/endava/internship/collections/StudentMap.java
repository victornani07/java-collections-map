package com.endava.internship.collections;

import java.util.*;

public class StudentMap<K extends Comparable<K>, V> implements Map<K, V> {

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }

        @Override
        public String toString() {
            return "{key=" + key.toString() + ", value=" + value.toString() + "}";
        }
    }

    private Node<K, V> root;
    private V lastAddedValue;
    private V lastRemovedValue;

    @Override
    public int size() {
        return countPairs(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean containsKey(Object o) {
        if(o == null) {
            throw new NullPointerException();
        }

        @SuppressWarnings("unchecked")
        Comparable<? super K> key = (Comparable<? super K>) o;

        return getValueByKey(root, key) != null;
    }

    @Override
    public boolean containsValue(Object o) {
        if(o == null) {
            throw new NullPointerException();
        }

        @SuppressWarnings("unchecked")
                Comparable<? super V> value = (Comparable<? super V>) o;

        return searchValue(root, value);
    }

    @Override
    public V get(Object o) {
        if(o == null) {
            throw new NullPointerException();
        }

        @SuppressWarnings("unchecked")
                Comparable<? super K> key = (Comparable<? super K>) o;

        return getValueByKey(root, key);
    }

    @Override
    public V put(K key, V value) {
        if(key == null) {
            throw new NullPointerException();
        }

        lastAddedValue = null;

        root = putPair(root, key, value);

        return lastAddedValue;
    }

    @Override
    public V remove(Object o) {
        if(o == null) {
            throw new NullPointerException();
        }

        lastRemovedValue = null;

        @SuppressWarnings("unchecked")
                Comparable<? super K> key = (Comparable<? super K>) o;

        root = removeKey(root, key);

        return lastRemovedValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for(Entry<? extends K, ? extends V> pair : map.entrySet()) {
            put(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void clear() {
        root = clearMap(root);
    }

    @Override
    public Set<K> keySet() {
        Set<K> s = new HashSet<>();

        collectKeys(root, s);

        return s;
    }

    @Override
    public Collection<V> values() {
        Collection<V> c = new ArrayList<>();

        collectValues(root, c);

        return c;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> s = new HashSet<>();

        collectKeysAndValues(root, s);

        return s;
    }

    private Node<K, V> putPair(Node<K, V> root, K key, V value) {
        if(root == null) {
            return new Node<>(key, value);
        }

        if(key.compareTo(root.key) < 0) {
            root.left = putPair(root.left, key, value);
        } else if(key.compareTo(root.key) > 0) {
            root.right = putPair(root.right, key, value);
        } else {
            lastAddedValue = root.value;
            root.value = value;
        }

        return root;
    }

    private V getValueByKey(Node<K, V> root, Comparable<? super K> key) {
        if(root == null) {
            return null;
        }

        if(key.equals(root.key)) {
            return root.value;
        }

        if(key.compareTo(root.key) < 0) {
            return getValueByKey(root.left, key);
        }

        return getValueByKey(root.right, key);
    }

    private Node<K, V> removeKey(Node<K, V> root, Comparable<? super K> key) {
        if(root == null) {
            return null;
        }

        if(key.compareTo(root.key) < 0) {
            root.left = removeKey(root.left, key);
        } else if(key.compareTo(root.key) > 0) {
            root.right = removeKey(root.right, key);
        } else {
            lastRemovedValue = root.value;

            if(root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            }

            root.key = getMinValue(root.right);
            root.right = removeKey(root.right, root.key);
        }

        return root;
    }

    private K getMinValue(Node<K, V> root) {
        K minKey = root.key;

        while(root.left != null) {
            minKey = root.left.key;
            root = root.left;
        }

        return minKey;
    }

    private boolean searchValue(Node<K, V> root, Comparable<? super V> value) {
        if(root == null) {
            return false;
        }

        if(value.equals(root.value)) {
            return true;
        }

        return searchValue(root.left, value) || searchValue(root.right, value);
    }

    private Node<K, V> clearMap(Node<K, V> root) {
        if(root == null) {
            return null;
        }

        root.left = clearMap(root.left);
        root.right = clearMap(root.right);

        return null;
    }

    private void collectKeys(Node<K, V> root, Set<K> s) {
        if(root == null) {
            return;
        }

        collectKeys(root.left, s);
        s.add(root.key);
        collectKeys(root.right, s);
    }

    private void collectValues(Node<K, V> root, Collection<V> c) {
        if(root == null) {
            return;
        }

        collectValues(root.left, c);
        c.add(root.value);
        collectValues(root.right, c);
    }

    private void collectKeysAndValues(Node<K, V> root, Set<Entry<K, V>> s) {
        if(root == null) {
            return;
        }

        collectKeysAndValues(root.left, s);
        s.add(new AbstractMap.SimpleEntry<>(root.key, root.value));
        collectKeysAndValues(root.right, s);
    }

    private int countPairs(Node<K, V> root) {
        if(root == null) {
            return 0;
        }

        return 1 + countPairs(root.left) + countPairs(root.right);
    }
}

