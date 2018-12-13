package termproject;

import java.util.concurrent.ThreadLocalRandom;

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
           if(treeComp.isEqual(currNode.getItem(index).element(),key)){
               return currNode.getItem(index);
           }
           else {
               currNode = currNode.getChild(index);
           }
        }
        return null;
    }
      public TFNode findNode(Object key) {
        TFNode currNode = root();
        while(currNode != null){
            //index is the first greater than equal index
           int index = currNode.FFGTE(key, treeComp);
           //if the element is equal to key then return the node's item's element
           if( index < currNode.getNumItems() && treeComp.isEqual(currNode.getItem(index).key(),key)){
               return currNode;
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
        size++;
        //if the tree is empty
        if (node == null) {
            node = new TFNode();
            node.addItem(index, temp);
            setRoot(node);
        }
        else {
            //go till we find the right external node
            while(!node.isExternal()){
                index = node.FFGTE(key, treeComp);
                //check for tie case
                if(index != node.getNumItems() && 
                        treeComp.isEqual(key, node.getItem(index).key())){
                    index++;
                }
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
    }
    private void fixOverflow(TFNode node){
        //base case - if we are not in overflow
        if(node.getNumItems() <= node.getMaxItems()){
            return;
        }
        
        Item middle = node.getItem(1);
         TFNode split = new TFNode();
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
        split.addItem(0, node.getItem(0));
        //parent's children pointers
        parent.setChild(index, split);
        parent.setChild(index + 1, node);
        //move child pointers from node to split
        for(int i = 0; i < 2; i++){
            split.setChild(i, node.getChild(0));
            if(node.getChild(0) != null){
                node.getChild(0).setParent(split);
            }
            node.removeItem(0);
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
        //find the element
        TFNode node= findNode(key);
        int index= node.FFGTE(key, treeComp);
        // am I a leaf
        if(node.isExternal()){
            
            if(treeComp.isEqual(key,node.getItem(index).key())){
                Item tempItem = new Item();
                tempItem=node.removeItem(index);
                fixUnderflow(node);
                return tempItem;
                
            }
            else{
                throw new ElementNotFoundException("Element not found");
            }
            
        }
        else{
            //Item IOSItem = new Item();
            Item tempNodeItem = null;
            tempNodeItem = node.getItem(index);
            TFNode IOS = inOrderSuccessor(node, index);
            Item IOSItem=IOS.removeItem(0);
            node.replaceItem(index, IOSItem);
            fixUnderflow(IOS);
            return tempNodeItem;
        }
        // if so, ffgte and check equal
        // if not equal, throw exception
        // if equal, removeElement and check underflow
        // not leaf, find IOS
        // remove item0 and replace in foundNode above
        // check underflow
    }

    
    public TFNode inOrderSuccessor(TFNode node, int index){
        TFNode IOS = node.getChild(index+1);
        while(!IOS.isExternal()){
            IOS=IOS.getChild(0);
        }
        return IOS;
        
    }
    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);
//
//        Integer myInt1 = new Integer(47);
//        myTree.insertElement(myInt1, myInt1);
//        Integer myInt2 = new Integer(83);
//        myTree.insertElement(myInt2, myInt2);
//        Integer myInt3 = new Integer(22);
//        myTree.insertElement(myInt3, myInt3);
//
//        Integer myInt4 = new Integer(16);
//        myTree.insertElement(myInt4, myInt4);
//
//        Integer myInt5 = new Integer(49);
//        myTree.insertElement(myInt5, myInt5);
//        
//
//        
//        Integer myInt6 = new Integer(100);
//        myTree.insertElement(myInt6, myInt6);
//        
//        Integer myInt7 = new Integer(38);
//        myTree.insertElement(myInt7, myInt7);
//
//        Integer myInt8 = new Integer(3);
//        myTree.insertElement(myInt8, myInt8);
//
//        Integer myInt9 = new Integer(53);
//        myTree.insertElement(myInt9, myInt9);
//
//        Integer myInt10 = new Integer(66);
//        myTree.insertElement(myInt10, myInt10);
//        
//        Integer myInt11 = new Integer(19);
//        myTree.insertElement(myInt11, myInt11);
//
//        Integer myInt12 = new Integer(23);
//        myTree.insertElement(myInt12, myInt12);
//        
//        Integer myInt13 = new Integer(24);
//        myTree.insertElement(myInt13, myInt13);
//
//        Integer myInt14 = new Integer(88);
//        myTree.insertElement(myInt14, myInt14);
//
//        Integer myInt15 = new Integer(1);
//        myTree.insertElement(myInt15, myInt15);
//
//        Integer myInt16 = new Integer(97);
//        myTree.insertElement(myInt16, myInt16);
//
//        Integer myInt17 = new Integer(94);
//        myTree.insertElement(myInt17, myInt17);
//
//        Integer myInt18 = new Integer(35);
//        myTree.insertElement(myInt18, myInt18);
//
//        Integer myInt19 = new Integer(51);
//        myTree.insertElement(myInt19, myInt19);
//        
//        Integer myInt21 = new Integer(24);
//        myTree.insertElement(myInt21, myInt21);
//        
//        Integer myInt22 = new Integer(49);
//        myTree.insertElement(myInt22, myInt22);
//          
//          Integer myInt28 = new Integer(11);
//          myTree.insertElement(myInt28, myInt28);
//          Integer myInt24 = new Integer(10);
//          myTree.insertElement(myInt24, myInt24);
//          Integer myInt25 = new Integer(4);
//          myTree.insertElement(myInt25, myInt25);
//          Integer myInt26 = new Integer(6);
//          myTree.insertElement(myInt26, myInt26);
//          Integer myInt23 = new Integer(5);
//          myTree.insertElement(myInt23, myInt23);
//          Integer myInt27 = new Integer(8);
//          myTree.insertElement(myInt27, myInt27);
//        
//          Integer myInt29 = new Integer(49);
//          myTree.insertElement(myInt29, myInt29);
//          Integer myInt30 = new Integer(49);
//          myTree.insertElement(myInt30, myInt30);
//        System.out.println("done");
//        myTree.checkTree();
//        
//        myTree.printAllElements();

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 1000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(new Integer(i), new Integer(i));
            myTree.printAllElements();
            myTree.checkTree();
        }
        
        System.out.println("removing");
//        for (int i = 0; i < TEST_SIZE; i++) {
//            int out = (Integer) myTree.removeElement(new Integer(i));
//            if (out != i) {
//                throw new TwoFourTreeException("main: wrong element removed");
//            }
//            if (i > TEST_SIZE - 15) {
//                myTree.printAllElements();
//            }
//        }
        
        System.out.println("done");
        
    
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
        if(underNode.getNumItems()!=0){
            return;
        }
        if (underNode == this.root()) {
            treeRoot = underNode.getChild(0);
            if (treeRoot != null) {
                treeRoot.setParent(null);
            }
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
//            rightFusion(underNode);
        }
        else{
            leftFusion(underNode);
           }
    }
    //TODO
    //write a left and right sib getter
   
    
    public boolean leftTransferPossible(TFNode current){
        //get size of left sibling node
        //check to make sure it is not a leftmost child
        if(current.whatChildIsThis()-1<0) {
                return false;
            }
        TFNode sib  = current.getLeftSib();
        //there is not left sib
        if(sib == null){
                System.out.println("no sib found");
                return false;
                }
        int numberOfItems = sib.getNumItems();
        //if the size of the left sibling node is not going to underflow if we take one
        return ((numberOfItems-1)>0);
        // return true
        //else return false
    }
    public boolean rightTransferPossible(TFNode current){
        //check to see if the right sib exists and is less than 2
       if(current.whatChildIsThis()+1>current.getMaxItems()) {
                return false;
            }
        
        TFNode sib = current.getRightSib();
       int numberOfItems = sib.getNumItems();
       return ((numberOfItems-1)>0);
    }
    public boolean rightFusionPossible(TFNode current){
        int index = current.whatChildIsThis();
        return index < 4;
    }
    
    public void leftTransfer(TFNode node){
        //TODO see what pointers we do not use here
        //thought these may be useful
        TFNode leftSib = node.getLeftSib();
        TFNode parent = node.getParent();
        Item tempItem = new Item();
        //get the rightmost item of index of the left sib
        Item leftMostSibItem=new Item();
        leftMostSibItem = leftSib.removeItem(leftSib.getNumItems()-1);
        //place the parent of the the current +1 as the new temp
        Item parentHolder= new Item();
        parentHolder =  parent.getItem(node.whatChildIsThis());
        //put the leftmost item inte the wchith+1 position
        parent.replaceItem(node.whatChildIsThis(), leftMostSibItem);
        //put the parent value inside of the poor deleted node 
        node.insertItem(0, parentHolder);
        return;
        
    }
    public void rightTransfer(TFNode node){
        //TODO see what pointers we do not use here
        //thought these may be useful
        TFNode rightSib = node.getRightSib();
        TFNode parent = node.getParent();
        Item tempItem = new Item();
        //get the leftmost item of index zero of the rigth sib
        Item leftMostSibItem=new Item();
        leftMostSibItem = rightSib.removeItem(0);
        //place the parent of the the current +1 as the new temp
        Item parentHolder= new Item();
        parentHolder =  parent.getItem(node.whatChildIsThis());
        //put the leftmost item inte the wchith+1 position
        parent.replaceItem(node.whatChildIsThis(), leftMostSibItem);
        //put the parent value inside of the poor deleted node 
        node.insertItem(0, parentHolder);
        return;
    }
    public void rightFusion(TFNode node){
        //get item at parent what child is this
        int index = node.whatChildIsThis();
        TFNode parent = node.getParent();
        Item item = parent.getItem(index);
        TFNode rightSib = node.getRightSib();
        //insert into rightSib the parent item between me and right sib
        rightSib.insertItem(0, item);
        //add right sibling's item to node
        node.addItem(1, rightSib.getItem(0));
        //set child pointers
        int i = 0;
        TFNode child = node.getChild(i);
        for(index = rightSib.getNumChildren(); child != null; index++){
            rightSib.setChild(index, child);
            child = node.getChild(++i);
        }
        //delete old node
        parent.removeItem(node.whatChildIsThis());
        //call fix underflow on the parent node
        fixUnderflow(parent);
    }
    public void leftFusion(TFNode node){
        //get item at parent what child is this
        int index = node.whatChildIsThis();
        TFNode parent = node.getParent();
        Item item = parent.getItem(index - 1);
        TFNode leftSib = node.getLeftSib();
        //add parent item between me and leftSib to left sibling
        leftSib.addItem(leftSib.getNumItems(), item);
        //set child pointers
        int i = 0;
        TFNode child = node.getChild(i);
        for(index = leftSib.getNumChildren(); child != null; index++){
            leftSib.setChild(index, child);
            child = node.getChild(++i);
        }
        //delete old node
        parent.removeItem(node.whatChildIsThis());
        //call fix underflow on the parent node
        fixUnderflow(parent);
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
