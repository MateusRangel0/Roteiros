package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	@Override
	public void remove(T element) {
		if (!this.isEmpty()) {
			BSTNode<T> node = this.search(element);
			if (!node.isEmpty()) {
				if (node.isLeaf()) {
					node.setData(null);
					node.setLeft(null);
					node.setRight(null);
					this.rebalanceUp((BSTNode<T>) node.getParent());
				} else if (numberOfChildren(node) == 1) {
					if (!node.equals(this.getRoot())) {
						if (node.getParent().getLeft().equals(node)) {
							if (!node.getLeft().isEmpty()) {
								node.getParent().setLeft(node.getLeft());
								node.getLeft().setParent(node.getParent());
							} else {
								node.getParent().setLeft(node.getRight());
								node.getRight().setParent(node.getParent());
							}
						} else if (node.getParent().getRight().equals(node)) {
							if (!node.getLeft().isEmpty()) {
								node.getParent().setRight(node.getLeft());
								node.getLeft().setParent(node.getParent());
							} else {
								node.getParent().setRight(node.getRight());
								node.getRight().setParent(node.getParent());
							}
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							this.root = (BSTNode<T>) node.getLeft();
						} else {
							this.root = (BSTNode<T>) node.getRight();
						}
					}
					this.rebalanceUp(node);
				} else {
					T data = sucessor(node.getData()).getData();
					remove(data);
					node.setData(data);
				}
			}
		}
	}


	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(this.getRoot(), element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			BSTNode<T> left = new BSTNode<T>();
			BSTNode<T> right = new BSTNode<T>();
			left.setParent(node);
			right.setParent(node);
			node.setLeft(left);
			node.setRight(right); 
		}
		else {
			if(node.getData().compareTo(element) > 0) {
				this.insert((BSTNode<T>) node.getLeft(), element);
			}
			else if(node.getData().compareTo(element) < 0) {
				this.insert((BSTNode<T>) node.getRight(), element);
			}
		}
		this.rebalanceUp(node);
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int toReturn = 0;
		if(node != null && !node.isEmpty()) {
			toReturn = height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return toReturn;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);
		if (balance > 1) {
			rebalanceLeft(node);
		} else if (balance < -1) {
			rebalanceRight(node);
		}
	}

	protected void rebalanceLeft(BSTNode<T> node) {
		int balanceLeft = this.calculateBalance((BSTNode<T>) node.getLeft());

		if (balanceLeft > 0) {
			Util.rightRotation(node);
		} else if (balanceLeft < 0) {
			Util.leftRotation((BSTNode<T>) node.getLeft());
			Util.rightRotation(node);
		}
		if (this.getRoot().equals(node)) {
			this.root = (BSTNode<T>) node.getParent();
		}
	}

	protected void rebalanceRight(BSTNode<T> node) {
		int balanceRight = this.calculateBalance((BSTNode<T>) node.getRight());

		if (balanceRight < 0) {
			Util.leftRotation(node);
		} else if (balanceRight > 0) {
			Util.rightRotation((BSTNode<T>) node.getRight()); 
			Util.leftRotation(node);
		}
		if (this.getRoot().equals(node)) {
			this.root = (BSTNode<T>) node.getParent();
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if(node != null && !node.isEmpty()) {
			while(node != null && !node.isEmpty()) {
				rebalance(node);
				node = (BSTNode<T>) node.getParent();
			}
		}
	}
}
