class BSTIter {
    static NodeIter root;
    public static int numLevels = 0;

    public BSTIter() {
        root = new NodeIter();
    }

    public static void main(String[] args) {
        BSTIter tree = new BSTIter();
        insert(root, 5);
        insert(root, 7);
        insert(root, 3);
        insert(root, 4);
        insert(root, 1);
        System.out.println(root.value);
        System.out.println(root.rightChild.value);
        System.out.println(root.leftChild.value);
        System.out.println(root.leftChild.rightChild.value);
        System.out.println(root.leftChild.leftChild.value);

        System.out.println("FindMinRec: " + findMin(root));
        System.out.println("FindMaxRec: " + findMax(root));
        System.out.println("Element next after 3: " + findNext(root, 3));
        System.out.println("Element next before 1: " + findPrev(root, 1));
    }

    public static void insert(NodeIter root, int element) {
        // Start at root of tree
        if (root.value == -12345) {
            root.value = element;
            return;
        }

        while (true) {
            numLevels++;
            if (element < root.value && root.leftChild == null)
            {
                NodeIter node = new NodeIter(element);
                root.leftChild = node;
                break;
            }
            else
                if (element > root.value && root.rightChild == null)
                {
                    NodeIter node = new NodeIter(element);
                    root.rightChild = node;
                    break;
                }

            // if element < root.value, go left
            if (element < root.value) {
                root = root.leftChild;
            } else {
                root = root.rightChild;
            }
        }
    }

    public static void delete(NodeIter root, int element) {
        // Search for the node
        NodeIter node = root;
        root = findNode(root, element);

        if (root.leftChild == null && root.rightChild == null)  // If it's a leaf, just delete
        {
            root.value = -1;
            return;
        }
        else
        {
            if ((root.leftChild == null && root.rightChild != null))  // If only one child, move child to root's spot
            {
                root.parent.rightChild = root.rightChild;
                root.value = -1;
                return;
            }
            if (root.leftChild != null && root.rightChild == null)
            {
                root.parent.leftChild = root.leftChild;
                root.value = -1;
                return;
            }
        }
        NodeIter next = new NodeIter(findNext(node, element));
        root = next;
        next = null;
    }

    public static int findNext(NodeIter root, int element) {
        int max = findMax(root);
        // Search for the node with element
        root = findNode(root, element);
        if (max == element)
            return -1;
        if (root.rightChild != null) // Check right child
            return findMin(root.rightChild);
        else
            return findNextParent(root.parent, element).value; //Go up until you find parent that is > element
    }

    public static int findPrev(NodeIter root, int element) {
        // Search for the node with element
        int min = findMin(root);
        root = findNode(root, element);

        if (min == element)
            return -1;
        if (root.leftChild != null) // Check the left Child then find max
            return findMax(root.leftChild);
        else
            return findPrevParent(root.parent, element).value; // If no left children, go up to parent that is < element
    }

    public static int findMin(NodeIter root) {
        while (root.leftChild != null) {
            root = root.leftChild;
        }
        return root.value;
    }

    public static int findMax(NodeIter root) {
        while (root.rightChild != null) {
            root = root.rightChild;
        }
        return root.value;
    }

    public static NodeIter findNode(NodeIter root, int element)
    {
        if (root.value == element)
            return root;

        while (root.value != element) { // While the element isn't found

            if (root.value == element) // If the value equals element return the node
                return root;

            if (element < root.value) // If element < value, go left
            {
                root = root.leftChild;
            }
            else {  // If element > value, go right
                root = root.rightChild;
            }
        }
        return root;
    }

    public static NodeIter findNextParent(NodeIter root, int element)
    {
        while (root.value < element) {
            if (root.value > element)
                return root;
            else
                root = root.parent;
        }
        return root;
    }

    public static NodeIter findPrevParent(NodeIter root, int element)
    {
        while (root.value > element) {
            if (root.value < element)
                return root;
            else
                root = root.parent;
        }
        return root;
    }
}

class NodeIter {
    int value;
    NodeIter leftChild;
    NodeIter rightChild;
    NodeIter parent;

    public NodeIter() {
        value = -12345;
        rightChild = null;
        leftChild = null;
        parent = null;
    }

    public NodeIter(int value) {
        this.value = value;
        rightChild = null;
        leftChild = null;
        parent = null;
    }
}