class RecAVL {
    static NodeBST root;

    static NodeRec mainRoot = new NodeRec();

    public static void main(String[] args) {
        //NodeRec root = new NodeRec();
        insertRec(mainRoot, 4);
        insertRec(mainRoot, 5);
        insertRec(mainRoot, 6);
        //insertRec(mainRoot, 7);
        //insertRec(mainRoot, 6);

        System.out.println(mainRoot.value + " " + mainRoot.height);
        System.out.println(mainRoot.leftChild.value + " " + mainRoot.leftChild.height);
        System.out.println(mainRoot.rightChild.value + " " + mainRoot.rightChild.height);
        //System.out.println(mainRoot.leftChild.leftChild.value + " " + mainRoot.leftChild.leftChild.height);
        //System.out.println(mainRoot.leftChild.rightChild.value + " " + mainRoot.leftChild.rightChild.height);
        //System.out.println(mainRoot.rightChild.leftChild.value + " " + mainRoot.rightChild.leftChild.height);
        //System.out.println(mainRoot.rightChild.rightChild.value + " " + mainRoot.rightChild.rightChild.height);
    }

    public static void insertRec(NodeRec root, int element) {
        // Start at root of tree
        if (root.value == -12345) {
            root.value = element;
            mainRoot = root;
        }

        while (true) {
            if (element < root.value) {
                if (root.leftChild == null) {
                    root.leftChild = new NodeRec(element);
                    root.leftChild.parent = root;
                    break;
                }
                root = root.leftChild;
            }
            else
            {
                if (root.rightChild == null) {
                    root.rightChild = new NodeRec(element);
                    root.rightChild.parent = root;
                    break;
                }
                root = root.rightChild;
            }
        }
        findHeightRec(mainRoot);

        while (true) {
            //Go back up from inserted element, to check BF of each parent
            int BF;

            if (root.leftChild == null) {
                BF = root.rightChild.height * (-1);
            }
            else {
                if (root.rightChild == null) {
                    BF = root.leftChild.height;
                }
                else {  //Height is max height of left or right child + 1
                    int leftHeight = root.leftChild.height;
                    int rightHeight = root.rightChild.height;
                    BF = leftHeight - rightHeight;
                }
            }

            if (BF > 1 || BF < -1) {
                rotations(root, BF);
                //break;
            }

            if (root.parent == null) {
                return;
            }
            else
                root = root.parent;
        }
    }

    public static void deleteRec(NodeRec root, int element) {
        // Search for the node
        NodeRec node = findNodeRec(root, element);

        NodeRec parent = node.parent;
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
                    NodeRec next = findNextRec(root, element);
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
                }
            }
        }
    }

    public static NodeRec findNextRec(NodeRec root, int element) {
        NodeRec max = findMaxRec(root);
        // Search for the node with element
        root = findNodeRec(root, element);
        if (max.value == element)
            return null;
        if (root.rightChild != null) // Check right child
            return findMinRec(root.rightChild);
        else
            return findNextParentRec(root.parent, element); //Go up until you find parent that is > element
    }

    public static NodeRec findPrevRec(NodeRec root, int element) {
        // Search for the node with element
        NodeRec min = findMinRec(root);
        root = findNodeRec(root, element);

        if (min.value == element)
            return null;
        if (root.leftChild != null) // Check the left Child then find max
            return findMaxRec(root.leftChild);
        else
            return findPrevParentRec(root.parent, element); // If no left children, go up to parent that is < element
    }

    public static NodeRec findMinRec(NodeRec root) {
        if (root.leftChild == null)
            return root;
        return findMinRec(root.leftChild);
    }

    public static NodeRec findMaxRec(NodeRec root) {
        if (root.rightChild == null)
            return root;
        return findMaxRec(root.rightChild);
    }

    public static NodeRec findNodeRec(NodeRec root, int element)
    {
        if (root.value == element)
            return root;

        if (element < root.value)
            return findNodeRec(root.leftChild, element);
        else {
            return findNodeRec(root.rightChild, element);
        }
    }

    public static NodeRec findNextParentRec(NodeRec root, int element)
    {
        if (root.value > element)
            return root;
        else
            return findNextParentRec(root.parent, element);
    }

    public static NodeRec findPrevParentRec(NodeRec root, int element)
    {
        if (root.value < element)
            return root;
        else
            return findNextParentRec(root.parent, element);
    }

    public static void rotations(NodeRec node, int BalFact) {
        //If BF < 1, check right child. If child is negative, rotate left, else rotate right then left
        //If BF > 1, check left child. If child is positive, rotate right, else rotate left then right

        if (BalFact < -1) {
            int rightBF;
            if (node.rightChild.leftChild == null) {
                leftRotateRec(node);//leftRotation
                findHeightRec(mainRoot);
                return;
            }
            if (node.rightChild.rightChild == null) {
                rightLeftRotateRec(node.rightChild);//right then left
                findHeightRec(mainRoot);
                return;
            }
        }
        if (BalFact > 1)
        {
            //int leftBF;
            if (node.leftChild.rightChild == null){
                rightRotateRec(node);
                findHeightRec(mainRoot);
                return;
            }
            if (node.leftChild.leftChild == null){
                leftRightRotateRec(node.leftChild);
                findHeightRec(mainRoot);
                return;
            }
        }
    }

    public static void leftRotateRec(NodeRec node)
    {
        //EDGE CASE: rotating on root
        if (node.parent == null) {
            NodeRec newRoot = node.rightChild;
            node.rightChild = newRoot.leftChild;
            //node.rightChild.parent = node;
            newRoot.leftChild = node;
            node.parent = newRoot;
            newRoot.parent = null;
            mainRoot = newRoot;
            //System.out.println("**" + mainRoot.value);
            return;
        }
        NodeRec parent = node.parent;
        NodeRec middle = node.rightChild;
        NodeRec temp = middle.leftChild;
        parent.rightChild = middle;
        middle.parent = parent;
        middle.leftChild = node;
        node.parent = middle;
        node.rightChild = temp;
    }

    public static void leftRightRotateRec(NodeRec node)
    {
        //EDGE CASE: rotating on root
        //System.out.println("LR" + node.value);
        if (node.parent.parent == null) {
            //Left
            NodeRec newRoot = node.rightChild;
            NodeRec parent = node.parent;
            NodeRec temp = newRoot.leftChild;
            parent.leftChild = newRoot;
            newRoot.parent = parent;
            newRoot.leftChild = node;
            node.parent = newRoot;
            node.rightChild = temp;
            //Right
            NodeRec temp2 = newRoot.rightChild;
            newRoot.rightChild = parent;
            parent.parent = newRoot;
            newRoot.parent = null;
            parent.leftChild = temp2;
            mainRoot = newRoot;
            //System.out.println("*RL*" + mainRoot.value);
            return;
        }
        NodeRec parent = node.parent;
        NodeRec middle = node.rightChild;
        NodeRec temp = middle.leftChild;
        parent.leftChild = middle;
        middle.parent = parent;
        node.rightChild = temp;
        middle.leftChild = node;
        node.parent = middle;
        parent = parent.parent;
        node = node.parent.parent;
        //Left
        NodeRec temp2 = middle.rightChild;
        parent.leftChild = middle;
        middle.parent = parent;
        middle.rightChild = node;
        node.parent = middle;
        node.leftChild = temp2;
    }

    public static void rightRotateRec(NodeRec node)
    {
        //EDGE CASE: rotating on root
        if (node.parent == null) {
            NodeRec newRoot = node.leftChild;
            node.leftChild = newRoot.rightChild;
            newRoot.rightChild = node;
            node.parent = newRoot;
            newRoot.parent = null;
            mainRoot = newRoot;
            return;
        }
        NodeRec parent = node.parent;
        NodeRec middle = node.leftChild;
        NodeRec temp = middle.rightChild;
        parent.leftChild = middle;
        middle.parent = parent;
        middle.rightChild = node;
        node.parent = middle;
        node.leftChild = temp;
    }

    public static void rightLeftRotateRec(NodeRec node)
    {
        //EDGE CASE: rotating on root
        //Right Rotation
        //System.out.println("RL" + node.value);
        if (node.parent.parent == null) {
            //Left
            NodeRec newRoot = node.leftChild;
            NodeRec parent = node.parent;
            NodeRec temp = newRoot.rightChild;
            parent.rightChild = newRoot;
            newRoot.parent = parent;
            newRoot.rightChild = node;
            node.parent = newRoot;
            node.leftChild = temp;
            //Right
            NodeRec temp2 = newRoot.leftChild;
            newRoot.leftChild = parent;
            parent.parent = newRoot;
            newRoot.parent = null;
            parent.rightChild = temp2;
            mainRoot = newRoot;
            //System.out.println("*RL*" + mainRoot.value);
            return;
        }
        NodeRec parent = node.parent;
        NodeRec middle = node.leftChild;
        NodeRec temp = middle.rightChild;
        parent.rightChild = middle;
        middle.parent = parent;
        node.leftChild = temp;
        middle.rightChild = node;
        node.parent = middle;
        parent = parent.parent;
        node = node.parent.parent;
        //Left
        NodeRec temp2 = middle.leftChild;
        parent.rightChild = middle;
        middle.parent = parent;
        middle.leftChild = node;
        node.parent = middle;
        node.rightChild = temp2;
    }

    public static void findHeightRec(NodeRec node)
    {
        if (node == null)
            return;

        findHeightRec(node.leftChild);
        findHeightRec(node.rightChild);

        if (node.leftChild == null && node.rightChild == null)
        {
            node.height = 1;
            return;
        }
        else if (node.leftChild == null)
        {
            node.height = node.rightChild.height + 1;
            return;
        }
        else if (node.rightChild == null)
        {
            node.height = node.leftChild.height + 1;
            return;
        }
        else {
            int left = node.leftChild.height;
            int right = node.rightChild.height;
            if (left > right)
                node.height = left + 1;
            else
                node.height = right + 1;
        }
    }
}

class NodeRec {
    int value;
    NodeRec leftChild;
    NodeRec rightChild;
    NodeRec parent;
    int height;

    public NodeRec() {
        value = -12345;
        rightChild = null;
        leftChild = null;
        parent = null;
        height = 0;
    }

    public NodeRec(int value) {
        this.value = value;
        rightChild = null;
        leftChild = null;
        height = 0;
    }
}