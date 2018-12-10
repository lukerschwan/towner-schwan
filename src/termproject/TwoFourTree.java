package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
public class TwoFourTree
        implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Searches dictionary to determine if key is present
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    public Object findElement(Object key) {
        TFNode currNode = root();
        while(currNode != null){
            //index is the first greater than equal index
           int index = currNode.FFGTE(key, treeComp);
           //if the element is equal to key then return the node's item's element
           if(treeComp.isEqual(currNode.getItem(index),key)){
               return currNode.getItem(index).element();
           }
           else {
               currNode = currNode.getChild(index);
           }
        }
        return null;
    }
    
 
    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {
        
        Item temp = new Item(key, element);
        TFNode node = this.root();
        int index = 0;
        //if the tree is empty
        if (node == null) {
            node = new TFNode();
            node.addItem(index, temp);
            setRoot(node);
            size++;
        }
        else {
            //go till we find the right external node
            while(!node.isExternal()){
                index =node.FFGTE(key, treeComp);
                node = node.getChild(index);
            }
            //actually insert item into the node
            index = node.FFGTE(key, treeComp);
            if(index < node.getNumItems()){
                node.insertItem(index, temp);
            }
            else{
                node.addItem(index, temp);
            }
            //fix overflow if it exists
            fixOverflow(node);
            
        }

            //
                //perform FFGTOE
                //determine if shifting insert is needed
                //perform insert
                //Check overflow if it gets too big
                    //perform split algorithm 
                //return u good homie
                
        
    }
    private void fixOverflow(TFNode node){
        //base case - if we are not in overflow
        if(node.getNumItems() <= node.getMaxItems()){
            return;
        }
        
        Item middle = node.getItem(1);
        TFNode split = new TFNode();
        size++;
        //root overflow case
        if(node == root()){
            TFNode newRoot = new TFNode();
            newRoot.addItem(0, middle);
            setRoot(newRoot);
            
            //split and fix pointers
            fixSplit(newRoot, node, split, 0);
            return;
        }
        //general overflow case
        else{
            //insert into parent node at proper location
            int index = node.whatChildIsThis();
            node.getParent().insertItem(index, middle);
            //split node and hook up pointers
            fixSplit(node.getParent(), node, split, index);
            
        }
        //recurse with parent
        fixOverflow(node.getParent());
    }
    
    private void fixSplit(TFNode parent, TFNode node, TFNode split, int index){
        //split data
        node.removeItem(1);
        split.addItem(0, node.getItem(0));
        node.removeItem(0);
        //pointers children
        parent.setChild(index, split);
        parent.setChild(index + 1, node);
        split.setChild(0, node.getChild(0));
        for(int i = 0; i < node.getNumItems(); i++){
            node.setChild(i, node.getChild(i+1));
        }
        //parent pointers
        split.setParent(parent);
        node.setParent(parent);
        
    }

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        return null;
    }

    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);

        Integer myInt1 = new Integer(47);
        myTree.insertElement(myInt1, myInt1);
        Integer myInt2 = new Integer(83);
        myTree.insertElement(myInt2, myInt2);
        Integer myInt3 = new Integer(22);
        myTree.insertElement(myInt3, myInt3);

        Integer myInt4 = new Integer(16);
        myTree.insertElement(myInt4, myInt4);

        Integer myInt5 = new Integer(49);
        myTree.insertElement(myInt5, myInt5);
        myTree.printAllElements();
        
        Integer myInt6 = new Integer(100);
        myTree.insertElement(myInt6, myInt6);
        myTree.checkTree();
        myTree.printAllElements();
        Integer myInt7 = new Integer(38);
        myTree.insertElement(myInt7, myInt7);

        Integer myInt8 = new Integer(3);
        myTree.insertElement(myInt8, myInt8);

        Integer myInt9 = new Integer(53);
        myTree.insertElement(myInt9, myInt9);

        Integer myInt10 = new Integer(66);
        myTree.insertElement(myInt10, myInt10);
        myTree.printAllElements();
        Integer myInt11 = new Integer(19);
        myTree.insertElement(myInt11, myInt11);


        Integer myInt12 = new Integer(23);
        myTree.insertElement(myInt12, myInt12);

        Integer myInt13 = new Integer(24);
        myTree.insertElement(myInt13, myInt13);

        Integer myInt14 = new Integer(88);
        myTree.insertElement(myInt14, myInt14);

        Integer myInt15 = new Integer(1);
        myTree.insertElement(myInt15, myInt15);

        Integer myInt16 = new Integer(97);
        myTree.insertElement(myInt16, myInt16);

        Integer myInt17 = new Integer(94);
        myTree.insertElement(myInt17, myInt17);

        Integer myInt18 = new Integer(35);
        myTree.insertElement(myInt18, myInt18);

        Integer myInt19 = new Integer(51);
        myTree.insertElement(myInt19, myInt19);

        myTree.printAllElements();
        System.out.println("done");

        /*
        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(new Integer(i), new Integer(i));
            //          myTree.printAllElements();
            //         myTree.checkTree();
        }
        System.out.println("removing");
        for (int i = 0; i < TEST_SIZE; i++) {
            int out = (Integer) myTree.removeElement(new Integer(i));
            if (out != i) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 15) {
                myTree.printAllElements();
            }
        }
        
        System.out.println("done");
        */
    }

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        }
        else {
            printTree(root(), indent);
        }
    }

    public void fixUnderflow(TFNode underNode){
        //base case
        if(underNode.getNumItems()>0){
            return;
        }
        //it is a assumed that there has been an underflow at this point
        //if the root was removed from this should not be entered
        if(leftTransferPossible(underNode)){
            leftTransfer(underNode);
        }
        else if(rightTransferPossible(underNode)){
            rightTransfer(underNode);
        }
        else if(rightFusionPossible(underNode)){
            rightFusion(underNode);
        }
        else{
            leftFusion(underNode);
        }
    }
    //TODO
    //write a left and right sib getter
   
    
    public boolean leftTransferPossible(TFNode current){
        //get size of left sibling node
        //if the size of the left sibling node is less than 2
        // return true
        //else return false
        return false;
    }
    public boolean rightTransferPossible(TFNode current){
        //check to see if the right sib exists and is less than 2
       return false;
    }
    public boolean rightFusionPossible(TFNode current){
        
       return false; 
    }
    public boolean leftFusionPossible(TFNode current){
        return false;
    }
    
    public void leftTransfer(TFNode node){
        
    }
    public void rightTransfer(TFNode node){
        
    }
    public void rightFusion(TFNode node){
        
    }
    public void leftFusion(TFNode node){
        
    }
    
   
    
    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                }
                else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }

            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }

    }
}
