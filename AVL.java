import java.util.ArrayList;

class AVL
{
    static Node mainRoot = new Node();
    static int numLevels = 0;
    public static void main(String[] args)
    {
        Node root = new Node();
        insert(mainRoot, 6);
        insert(mainRoot, 5);
        insert(mainRoot, 4);
        //insert(root, 13);
        //insert(root, 12);
        //insert(root, 2);
        //insert(root, 4);
        //insert(root, 5);
        //insert(root, 6);
        //inOrder(root);
        //System.out.println();
        //delete(root, 6);
        //delete(root, 5);
        //delete(root, 12);
        System.out.println(mainRoot.value + " " + mainRoot.height);
        System.out.println(mainRoot.leftChild.value + " " + mainRoot.leftChild.height);
        System.out.println(mainRoot.rightChild.value + " " + mainRoot.rightChild.height);
        //System.out.println(root.leftChild.leftChild.value + " " + root.leftChild.leftChild.height);
        //System.out.println(root.leftChild.rightChild.value + " " + root.leftChild.rightChild.height);
        //System.out.println(root.rightChild.leftChild.value + " " + root.rightChild.leftChild.height);
        //System.out.println(root.rightChild.rightChild.value + " " + root.rightChild.rightChild.height);
        //System.out.println(root.leftChild.rightChild.leftChild.value + " " + root.leftChild.rightChild.leftChild.height);
        //System.out.println(root.leftChild.rightChild.rightChild.value + " " + root.leftChild.rightChild.rightChild.height);

        //System.out.println(root.rightChild.rightChild.leftChild.value + " " + root.rightChild.rightChild.leftChild.height);
    }

    public static void insert(Node root, int element) {
        //If root is null, add to root
        //System.out.println("INSERT" + element);
        if (root.value == -12345) {
            root.value = element;
            mainRoot = root;
            return;
        }

        while (true) {
            if (element < root.value) {
                if (root.leftChild == null) {
                    root.leftChild = new Node(element);
                    root.leftChild.parent = root;
                    numLevels++;
                    break;
                }
                numLevels++;
                root = root.leftChild;
            }
            else
            {
                if (root.rightChild == null) {
                    root.rightChild = new Node(element);
                    root.rightChild.parent = root;
                    numLevels++;
                    break;
                }
                numLevels++;
                root = root.rightChild;
            }
        }

        while (true) {
            //Go back up from inserted element, to check BF of each parent
            int BF;
            if (root.leftChild == null) {
                root.height = root.rightChild.height + 1;
                BF = root.rightChild.height * (-1);
            }
            else {
                if (root.rightChild == null) {
                    root.height = root.leftChild.height + 1;
                    BF = root.leftChild.height;
                }
                else {  //Height is max height of left or right child + 1
                    int leftHeight = root.leftChild.height;
                    int rightHeight = root.rightChild.height;

                    if (leftHeight >= rightHeight) {
                        root.height = leftHeight + 1;
                    } else
                        root.height = rightHeight + 1;
                    BF = leftHeight - rightHeight;
                }
            }
            //System.out.println(root.value + "-----" + root.height  + "   " + BF);
            if (BF > 1 || BF < -1) {
                rotations(root, BF);
            }

            if (root.parent == null) {
                break;
            }
            else
                root = root.parent;
        }
    }

    public static void delete(Node root, int element) {
        Node node = findNode(root, element);

        Node parent = node.parent;
        if (node.leftChild == null && node.rightChild == null) { // no children
            if (parent.leftChild.value == node.value)
                parent.leftChild = null;
            else
                parent.rightChild = null;
            node = null;
        }
        else {
            if (node.leftChild == null) // only one child, right
            {
                if (parent.leftChild.value == node.value)
                    parent.leftChild = node.rightChild;
                else
                    parent.rightChild = node.rightChild;
            }
            else
            {
                if (node.rightChild == null) // only one child, left
                {
                    if (parent.leftChild.value == node.value)
                        parent.leftChild = node.leftChild;
                    else
                        parent.rightChild = node.leftChild;
                }
                else // Two children; findNext
                {
                    Node next = findNode(root, findNextIter(root, element));
                    //System.out.println(node.value + "**" + next.value);
                    if (node.leftChild.value == next.value) {
                        node.parent.leftChild = next;
                        next.parent = node.parent;
                        next.rightChild = node.rightChild;
                        node.rightChild.parent = next;
                        node = null;
                        return;
                    }
                    if (node.rightChild.value == next.value)
                    {
                        node.parent.rightChild = next;
                        next.parent = node.parent;
                        next.leftChild = node.leftChild;
                        node.leftChild.parent = next;
                        node = null;
                        return;
                    }
                    //FINDNEXT is a parent
                }
            }
        }
    }

    public static int findNextIter(Node root, int element) {
        int max = findMaxIter(root);
        // Search for the node with element
        root = findNode(root, element);
        if (max == element)
            return -1;
        if (root.rightChild != null) // Check right child
            return findMinIter(root.rightChild);
        else
            return findNextParent(root.parent, element).value; //Go up until you find parent that is > element
    }

    public static int findPrevIter(Node root, int element) {
        // Search for the node with element
        int min = findMinIter(root);
        root = findNode(root, element);

        if (min == element)
            return -1;
        if (root.leftChild != null) // Check the left Child then find max
            return findMaxIter(root.leftChild);
        else
            return findPrevParent(root.parent, element).value; // If no left children, go up to parent that is < element
    }

    public static int findMinIter(Node root) {
        while (root.leftChild != null) {
            root = root.leftChild;
        }
        return root.value;
    }

    public static int findMaxIter(Node root) {
        while (root.rightChild != null) {
            root = root.rightChild;
        }
        return root.value;
    }

    public static Node findNode(Node root, int element)
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

    public static Node findNextParent(Node root, int element)
    {
        while (root.value < element) {
            if (root.value > element)
                return root;
            else
                root = root.parent;
        }
        return root;
    }

    public static Node findPrevParent(Node root, int element)
    {
        while (root.value > element) {
            if (root.value < element)
                return root;
            else
                root = root.parent;
        }
        return root;
    }

    public static void rotations(Node node, int BalFact) {
        //If BF < 1, check right child. If child is negative, rotate left, else rotate right then left
        //If BF > 1, check left child. If child is positive, rotate right, else rotate left then right
        //System.out.println(node.value + " + " + BalFact);
        //System.out.println(node.leftChild.value + " " + node.leftChild.rightChild.value);
        if (BalFact < -1) {
            int rightBF;
            if (node.rightChild.leftChild == null) {
                leftRotate(node);//leftRotation
                return;
            }
            if (node.rightChild.rightChild == null) {
                rightLeftRotate(node.rightChild);//right then left
                return;
            }
            /*rightBF = node.rightChild.leftChild.height - node.leftChild.rightChild.height;
            if (rightBF < 1)
                leftRotate(node);
            else {
                rightRotate(node);
                leftRotate(node);
            }*/
        }
        if (BalFact > 1)
        {
            //int leftBF;
            if (node.leftChild.rightChild == null && node.leftChild.leftChild != null) {
                //System.out.println("hi");
                rightRotate(node.leftChild);
                return;
            }
            if (node.leftChild.leftChild == null && node.leftChild.rightChild != null) {
                leftRightRotate(node.leftChild);
                return;
            }
            //System.out.println(node.value);
            /*leftBF = node.leftChild.leftChild.height - node.rightChild.rightChild.height;
            if (leftBF < 1)
                rightRotate(node);
            else {
                leftRotate(node);
                rightRotate(node);
            }*/
        }
    }

    public static void leftRotate(Node node)
    {
        //EDGE CASE: rotating on root
        if (node.parent == null) {
            Node newRoot = node.rightChild;
            node.rightChild = newRoot.leftChild;
            //node.rightChild.parent = node;
            newRoot.leftChild = node;
            node.parent = newRoot;
            newRoot.parent = null;
            node.height = node.height-2;
            mainRoot = newRoot;
            //System.out.println("**" + mainRoot.value);
            return;
        }
        Node parent = node.parent;
        Node middle = node.rightChild;
        //System.out.println("LEFT" + node.value + "---" + parent.value + "---" + middle.value);
        parent.rightChild = middle;
        middle.parent = parent;
        middle.leftChild = node;
        node.parent = middle;
        node.rightChild = middle.rightChild;
        node.height = node.height - 2;
    }

    public static void leftRightRotate(Node node)
    {
        //EDGE CASE: rotating on root
        //System.out.println("LR" + node.value);
        if (node.parent.parent == null) {
            //Right
            Node newRoot = node.rightChild;
            Node parent = node.parent;
            parent.leftChild = newRoot;
            newRoot.parent = parent;
            newRoot.leftChild = node;
            node.parent = newRoot;
            node.rightChild = null;
            //Left
            newRoot.leftChild = parent;
            parent.parent = newRoot;
            newRoot.parent = null;
            parent.leftChild = null;
            newRoot.height = newRoot.height + 1;
            parent.height = parent.height - 2;
            node.height = node.height - 1;
            mainRoot = newRoot;
            //System.out.println("*RL*" + mainRoot.value);
            return;
        }
        Node parent = node.parent;
        Node middle = node.rightChild;
        //System.out.println("LEFTRIGHT" + node.value + "---" + parent.value + "---" + middle.value);
        parent.leftChild = middle;
        middle.parent = parent;
        middle.leftChild = node;
        node.parent = middle;
        node.rightChild = null; ///////////////////////////////////
        node.height = node.height - 1;
        parent = parent.parent;
        node = node.parent.parent;
        //System.out.println("LEFTRIGHT" + node.value + "---" + parent.value + "---" + middle.value);
        //System.out.println(middle.leftChild.value);
        parent.rightChild = middle;
        middle.parent = parent;
        middle.rightChild = node;
        node.parent = middle;
        node.leftChild = null;
        node.height = node.height - 2;
        parent.height = parent.height - 1;
        middle.height = middle.height + 1;
    }

    public static void rightRotate(Node node)
    {
        //EDGE CASE: rotating on root
        if (node.parent == null) {
            Node newRoot = node.leftChild;
            node.leftChild = newRoot.rightChild;
            //node.rightChild.parent = node;
            newRoot.rightChild = node;
            node.parent = newRoot;
            newRoot.parent = null;
            node.height = node.height-2;
            mainRoot = newRoot;
            //System.out.println("**" + mainRoot.value);
            return;
        }
        Node parent = node.parent;
        Node middle = node.leftChild;
        //System.out.println("LEFT" + node.value + "---" + parent.value + "---" + middle.value);
        parent.leftChild = middle;
        middle.parent = parent;
        middle.rightChild = node;
        node.parent = middle;
        node.leftChild = middle.leftChild;
        node.height = node.height - 2;
    }

    public static void rightLeftRotate(Node node)
    {
        //EDGE CASE: rotating on root
        //Right Rotation
        //System.out.println("RL" + node.value);
        if (node.parent.parent == null) {
            //Left
            Node newRoot = node.leftChild;
            Node parent = node.parent;
            parent.rightChild = newRoot;
            newRoot.parent = parent;
            newRoot.rightChild = node;
            node.parent = newRoot;
            node.leftChild = null;
            //Right
            newRoot.leftChild = parent;
            parent.parent = newRoot;
            newRoot.parent = null;
            parent.rightChild = null;
            newRoot.height = newRoot.height + 1;
            parent.height = parent.height - 2;
            node.height = node.height - 1;
            mainRoot = newRoot;
            //System.out.println("*RL*" + mainRoot.value);
            return;
        }
        Node parent = node.parent;
        Node middle = node.leftChild;
        //System.out.println("LEFTRIGHT" + node.value + "---" + parent.value + "---" + middle.value);
        parent.rightChild = middle;
        middle.parent = parent;
        middle.rightChild = node;
        node.parent = middle;
        node.leftChild = null; ///////////////////////////////////
        node.height = node.height - 1;
        parent = parent.parent;
        node = node.parent.parent;
        //System.out.println("LEFTRIGHT" + node.value + "---" + parent.value + "---" + middle.value);
        //System.out.println(middle.leftChild.value);
        parent.leftChild = middle;
        middle.parent = parent;
        middle.leftChild = node;
        node.parent = middle;
        node.rightChild = null;
        node.height = node.height - 2;
        parent.height = parent.height - 1;
        middle.height = middle.height + 1;
    }

    public static void inOrder(Node root)
    {
        if (root == null)
            return;

        inOrder(root.leftChild);
        System.out.print(root.value + " ");
        inOrder(root.rightChild);
    }
}

class Node {
    int value;
    Node leftChild;
    Node rightChild;
    Node parent;
    int height;

    public Node() {
        value = -12345;
        rightChild = null;
        leftChild = null;
        parent = null;
        height = 0;
    }

    public Node(int value) {
        this.value = value;
        rightChild = null;
        leftChild = null;
        parent = null;
        height = 1;
    }
}