public class ConstructingTrees {
    public static void main(String[] args)
    {
        int n = 7000;
        int[] arr = Arrays.getRandomArray(n);
        //int[] arr = new int[]{9, 3, 4, 2, 7, 6, 5, 10, 1, 8};
;       //for (int element : arr)
        //    System.out.print(element + " ");
        //System.out.println("\n");

        BST BSTtree = new BST();
        NodeBST rootBST = new NodeBST();
        AVL AVLtree = new AVL();
        Node rootAVL = new Node();
        BSTIter BSTItertree= new BSTIter();
        NodeIter rootBSTIter = new NodeIter();
        /*
        for (int element : arr)
            BSTtree.insertRec(rootBST, element);
        System.out.println("BST tree is done!");
        //System.out.println();
        */
        for (int element : arr)
            BSTItertree.insert(rootBSTIter, element);
        //System.out.println("BSTIter tree is done!");
        System.out.println("NumLevels for BSTIter: " + BSTIter.numLevels);
        //System.out.println();

        for (int element : arr)
            AVLtree.insert(rootAVL, element);
        //System.out.println("AVL tree is done!");
        System.out.println("NumLevels for AVL: " + AVL.numLevels);

        System.out.println();

        int[] arr2 = Arrays.getSortedArray(n);
        AVL AVLtree2 = new AVL();
        Node rootAVL2 = new Node();
        BSTIter BSTItertree2= new BSTIter();
        NodeIter rootBSTIter2 = new NodeIter();
        for (int element : arr2)
            BSTItertree2.insert(rootBSTIter2, element);
        //System.out.println("BSTIter tree is done!");
        System.out.println("NumLevels for BSTIter sorted: " + BSTIter.numLevels);
        //System.out.println();

        for (int element : arr)
            AVLtree2.insert(rootAVL2, element);
        //System.out.println("AVL tree is done!");
        System.out.println("NumLevels for AVL sorted: " + AVL.numLevels);
    }
}
