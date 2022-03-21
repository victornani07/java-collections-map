package com.endava.internship.collections;

import java.util.*;

public class StudentMap implements Map<Student, Integer> {
    private Node root;
    private Integer lastOverriddenValue;
    private Integer lastRemovedValue;

    private static class Node {
        private Student key;
        private Integer value;
        private Node left;
        private Node right;

        public Node(Student key, Integer value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }

        @Override
        public String toString() {
            return "{key=" + key + ", value=" + value + "}";
        }
    }

    public StudentMap() {
        root = null;
        lastOverriddenValue = null;
        lastRemovedValue = null;
    }

    @Override
    public int size() {
        return getSize(root);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        Student s;

        if (o == null) {
            throw new NullPointerException();
        }

        if (!(o instanceof Student)) {
            return false;
        }
        s = (Student) o;
        return searchKeyReturnValue(root, s) != null;
    }

    @Override
    public boolean containsValue(Object o) {
        Integer i;

        if (!(o instanceof Integer)) {
            return false;
        }
        i = (Integer) o;
        return searchValue(root, i);
    }

    @Override
    public Integer get(Object o) {
        Student s;

        if (o == null) {
            throw new NullPointerException();
        }

        if (!(o instanceof Student)) {
            return null;
        }
        s = (Student) o;
        return searchKeyReturnValue(root, s);
    }

    @Override
    public Integer put(Student student, Integer integer) {
        lastOverriddenValue = null;
        root = putPair(root, student, integer);
        return lastOverriddenValue;
    }

    @Override
    public Integer remove(Object o) {
        Student s;

        if (o == null) {
            throw new NullPointerException();
        }

        if (!(o instanceof Student)) {
            return null;
        }
        s = (Student) o;
        lastRemovedValue = null;
        root = removeKey(root, s);
        return lastRemovedValue;
    }

    @Override
    public void putAll(Map<? extends Student, ? extends Integer> map) {
        for (Entry<? extends Student, ? extends Integer> pair : map.entrySet()) {
            put(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void clear() {
        root = clearMap(root);
    }

    @Override
    public Set<Student> keySet() {
        Set<Student> s = new HashSet<>();
        collectStudents(root, s);
        return s;
    }

    @Override
    public Collection<Integer> values() {
        Collection<Integer> c = new ArrayList<>();
        collectValues(root, c);
        return c;
    }

    @Override
    public Set<Entry<Student, Integer>> entrySet() {
        Set<Entry<Student, Integer>> s = new HashSet<>();
        collectKeysAndValues(root, s);
        return s;
    }

    private Node putPair(Node node, Student key, Integer value) {
        if (node == null) {
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = putPair(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = putPair(node.right, key, value);
        } else {
            lastOverriddenValue = node.value;
            node.value = value;
        }
        return node;
    }

    private int getSize(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + getSize(root.left) + getSize(root.right);
    }

    private Integer searchKeyReturnValue(Node root, Student s) {
        if (root == null) {
            return null;
        }

        if (s.equals(root.key)) {
            return root.value;
        }

        if (s.compareTo(root.key) < 0) {
            return searchKeyReturnValue(root.left, s);
        }
        return searchKeyReturnValue(root.right, s);
    }

    private boolean searchValue(Node root, Integer i) {
        if (root == null) {
            return false;
        }

        if (i.intValue() == root.value.intValue()) {
            return true;
        }
        return searchValue(root.left, i) || searchValue(root.right, i);
    }

    private Node removeKey(Node root, Student s) {
        if (root == null) {
            return null;
        }

        if (s.compareTo(root.key) < 0) {
            root.left = removeKey(root.left, s);
        } else if (s.compareTo(root.key) > 0) {
            root.right = removeKey(root.right, s);
        } else {
            lastRemovedValue = root.value;
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.key = getMinValue(root.right);
            root.right = removeKey(root.right, root.key);
        }
        return root;
    }

    private Student getMinValue(Node root) {
        Student minStudent = root.key;

        while (root.left != null) {
            minStudent = root.left.key;
            root = root.left;
        }
        return minStudent;
    }

    private void collectStudents(Node root, Set<Student> s) {
        if (root == null) {
            return;
        }
        collectStudents(root.left, s);
        s.add(root.key);
        collectStudents(root.right, s);
    }

    private void collectValues(Node root, Collection<Integer> c) {
        if (root == null) {
            return;
        }
        collectValues(root.left, c);
        c.add(root.value);
        collectValues(root.right, c);
    }

    private Node clearMap(Node root) {
        if (root == null) {
            return null;
        }
        root.left = clearMap(root.left);
        root.right = clearMap(root.right);
        return null;
    }

    private void collectKeysAndValues(Node root, Set<Entry<Student, Integer>> s) {
        if (root == null) {
            return;
        }
        collectKeysAndValues(root.left, s);
        s.add(new AbstractMap.SimpleEntry<>(root.key, root.value));
        collectKeysAndValues(root.right, s);
    }

}

