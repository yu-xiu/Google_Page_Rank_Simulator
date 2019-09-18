// import static javafx.scene.input.KeyCode.T;

/**
 * Binary search tree class which allows users to search a specific page rank
 */
public class BinarySearchTree {
    /**
     * Node class
     */
    static class  Node {
        Node left;
        Node right;
        int key;
        WebPageInformation info;
        Node p;
        // constructor
        Node (int PageRank, WebPageInformation info) {
            key = PageRank;
            this.info = info;
        }
        public String toString () {
            return key + " " + info.toString();
        }
    }

    /**
     * Tree class
     */
    static class Tree {
        Node root;
    }

    /**
     * Create a default BST
     */
    BinarySearchTree() {
    }

    /**
     * treeSearch method using to search a specific page rank
     * @param x
     * @param k
     * @return
     */
    static Node treeSearch(Node x, int k) {
        if (x == null || k == x.key) {
            return x;
        }
        else if (k < x.key) {
            return treeSearch(x.left, k);
        }
        else {return treeSearch(x.right, k);}
    }

    /**
     * Inorder Traversal
     * @param x
     */
    static void inorderTreeWalk(Node x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x);
            inorderTreeWalk(x.right);

        }
    }

    /**
     * Insertion
     *
     * Insert node to BST
     * @param T
     * @param z
     */
    static void treeInsert(Tree T, Node z) {
        Node y = null;
        Node x = T.root;
        while(x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == null) {
            // Tree T was empty
            T.root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {y.right = z;}
    }

    /**
     * transplant subtrees
     * replacing one subtree as a child of its parent with another subtree
     * @param T
     * @param u
     * @param v
     */
    static void transplant(Tree T, Node u, Node v) {
        // u is the root of T
        if (u.p == null) {
            T.root = v;
        }
        // u is left child of T
        else if (u == u.p.left) {
            u.p.left = v;
        }
        // u is right child of T
        else {
            u.p.right = v;
        }
        if (v != null) {
            v.p = u.p;
        }
    }


    /**
     * Minimum
     * Searching minimum key by following left child pointer.
     * It will be called in treeDelete function to find a node's successor
     * @param x
     * @return
     */
    static Node treeMinimum(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * Deletion
     *
     * Time complexity O(h)
     * @param T
     * @param z
     */
    static protected < E extends Comparable< ? super E > >
    void treeDelete(Tree T, Node z) {
        // node z has no left child
        if (z.left == null) {
            transplant(T,z,z.right);
        }
        // node z has left child but no right child
        else if (z.right == null) {
            transplant(T,z,z.left);
            }
        // node z has two children
        else {
            // find successor of node z; the node with the smallest key in the right subtree
            Node y = treeMinimum(z.right);
            // if y is not z's child
            if (y.p != z) {
                transplant(T,y,y.right);
                y.right = z.right;
                y.right.p = y;
            }
            // if y is z's right child
            transplant(T,z,y);
            y.left = z.left;
            y.left.p = y;
        }
    }


}
