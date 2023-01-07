import java.util.ArrayList;
import java.util.List;

public class TST<Value> {
    public Node<Value> root;

    public static class Node<Value> {
        public char c;
        public Node<Value> left, mid, right;
        public Value val;
    }

    // Inserts the key value pair into ternary search tree
    public void put(String key, Value val) {
        /* Code here */
    }

    // Returns a list of values using the given prefix
    public List<Value> valuesWithPrefix(String prefix) {
        /* Code here */
        return new ArrayList<>();
    }
}