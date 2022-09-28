package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V>  {

    public class AVLNode extends BSTNode{
        private int height;

        public AVLNode(K key, V value){
            super(key, value);
            this.height = 0;
        }
    }


    public AVLTree(){
        super();
    }
    public V insert (K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldVal = find(key);
        AVLNode newNode = new AVLNode(key, value);
        root = avlTree((AVLNode) root, newNode);
        if(oldVal == null){
            size++;
        }
        return oldVal;
    }

    private AVLNode avlTree(AVLNode root, AVLNode newNode){
        if(root == null){
            return newNode;
        }
        if(newNode.key.compareTo(root.key) < 0){
            root.children[0] = avlTree((AVLNode) root.children[0], newNode);
        }else if(newNode.key.compareTo(root.key) > 0){
            root.children[1] = avlTree((AVLNode) root.children[1], newNode);
        }else {
            root.value = newNode.value;
            return root;
        }
        root.height = 1 + Math.max(height((AVLNode)root.children[0]), height((AVLNode)root.children[1]));
        return decideRotation(root);
    }

    private int height(AVLNode root){
        if(root == null){
            return -1;
        }
        return root.height;
    }

    private AVLNode decideRotation(AVLNode root){
        if(height((AVLNode) root.children[1]) - height((AVLNode) root.children[0]) < - 1) {
            if(height((AVLNode) root.children[0].children[0]) > height((AVLNode) root.children[0].children[1])){
                root = singleLRotation(root);
            }else{
                root = doubleLRRotation(root);
            }
        }else if(height((AVLNode) root.children[1]) - height((AVLNode) root.children[0]) > 1){
            if(height((AVLNode) root.children[1].children[1]) > height((AVLNode) root.children[1].children[0])){
                root = singleRRotation(root);
            }else{
                root = doubleRLRotation(root);
            }
        }
        return root;
    }

    private AVLNode singleLRotation(AVLNode parentL){
        AVLNode tempL = (AVLNode) parentL.children[0];
        parentL.children[0] = tempL.children[1];
        tempL.children[1] = parentL;
        parentL.height = 1 + Math.max(height((AVLNode)parentL.children[0]), height((AVLNode)parentL.children[1]));
        tempL.height = 1 + Math.max(height((AVLNode)tempL.children[0]), height((AVLNode)tempL.children[1]));
        return tempL;
    }

    private AVLNode singleRRotation(AVLNode parentR){
        AVLNode tempR = (AVLNode) parentR.children[1];
        parentR.children[1] = tempR.children[0];
        tempR.children[0] = parentR;
        parentR.height = 1 + Math.max(height((AVLNode)parentR.children[0]), height((AVLNode)parentR.children[1]));
        tempR.height = 1 + Math.max(height((AVLNode)tempR.children[0]), height((AVLNode)tempR.children[1]));
        return tempR;
    }

    private AVLNode doubleLRRotation(AVLNode parent){
        parent.children[0] = singleRRotation((AVLNode) parent.children[0]);
        return singleLRotation(parent);
    }

    private AVLNode doubleRLRotation(AVLNode parent){
        parent.children[1] = singleLRotation((AVLNode) parent.children[1]);
        return singleRRotation(parent);
    }
}