class BST {
    static NodeBST root;

    public BST() {
        root = new NodeBST();
    }

    public static void main(String[] args) {
        BST tree = new BST();
        insertRec(root, 5);
        insertRec(root, 7);
        insertRec(root, 3);
        insertRec(root, 4);
        insertRec(root, 1);
        //deleteRec(root, 7);
        System.out.println(root.value);
        System.out.println(root.rightChild.value);
        System.out.println(root.leftChild.value);
        System.out.println(root.leftChild.rightChild.value);
        System.out.println(root.leftChild.leftChild.value);

        System.out.println("FindMinRec: " + findMinRec(root).value);
        System.out.println("FindMaxRec: " + findMaxRec(root).value);
        System.out.println("Element next after 3: " + findNextRec(root, 3).value);
        System.out.println("Element next before 5: " + findPrevRec(root, 5).value);
    }

    public static void insertRec(NodeBST root, int element) {
        // Start at root of tree
        if (root.value == -12345) {
            root.value = element;
            return;
        }

        // if element < root.value, go left
        if (element < root.value) {
            if (root.leftChild == null) // if left child is null, make a new node with element
            {
                root.leftChild = new NodeBST(element);
                root.leftChild.parent = root;
            }
            else // otherwise keep going to the left
            {
                insertRec(root.leftChild, element);
            }
        }
        else {
            if (root.rightChild == null) // if right child is null, make a new node with element
            {
                root.rightChild = new NodeBST(element);
                root.rightChild.parent = root;
            }
            else // otherwise keep going to the right
            {
                insertRec(root.rightChild, element);
            }
        }
    }

    public static void deleteRec(NodeBST root, int element) {
        // Search for the node
        NodeBST node = root;
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
        NodeBST next = findNextRec(node, element);
        root = next;
        next = null;
    }

    public static NodeBST findNextRec(NodeBST root, int element) {
        NodeBST max = findMaxRec(root);
        // Search for the node with element
        root = findNode(root, element);
        if (max.value == element)
            return null;
        if (root.rightChild != null) // Check right child
            return findMinRec(root.rightChild);
        else
            return findNextParent(root.parent, element); //Go up until you find parent that is > element
    }

    public static NodeBST findPrevRec(NodeBST root, int element) {
        // Search for the node with element
        NodeBST min = findMinRec(root);
        root = findNode(root, element);

        if (min.value == element)
            return null;
        if (root.leftChild != null) // Check the left Child then find max
            return findMaxRec(root.leftChild);
        else
            return findPrevParent(root.parent, element); // If no left children, go up to parent that is < element
    }

    public static NodeBST findMinRec(NodeBST root) {
        if (root.leftChild == null)
            return root;
        return findMinRec(root.leftChild);
    }

    public static NodeBST findMaxRec(NodeBST root) {
        if (root.rightChild == null)
            return root;
        return findMaxRec(root.rightChild);
    }

    public static NodeBST findNode(NodeBST root, int element)
    {
        if (root.value == element)
            return root;

        if (element < root.value)
            return findNode(root.leftChild, element);
        else {
            return findNode(root.rightChild, element);
        }
    }

    public static NodeBST findNextParent(NodeBST root, int element)
    {
        if (root.value > element)
            return root;
        else
            return findNextParent(root.parent, element);
    }

    public static NodeBST findPrevParent(NodeBST root, int element)
    {
        if (root.value < element)
            return root;
        else
            return findNextParent(root.parent, element);
    }

    public static void inOrder(NodeBST root)
    {
        if (root == null)
            return;
        inOrder(root.leftChild);
        System.out.print(root.value + " ");
        inOrder(root.rightChild);
    }
}

class NodeBST {
    int value;
    NodeBST leftChild;
    NodeBST rightChild;
    NodeBST parent;

    public NodeBST() {
        value = -12345;
        rightChild = null;
        leftChild = null;
        parent = null;
    }

    public NodeBST(int value) {
        this.value = value;
        rightChild = null;
        leftChild = null;
    }
}