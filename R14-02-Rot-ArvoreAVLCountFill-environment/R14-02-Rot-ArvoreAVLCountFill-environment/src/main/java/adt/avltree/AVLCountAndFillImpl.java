package adt.avltree;

import java.util.ArrayList;
import java.util.Arrays;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {

	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	protected void rebalanceLeft(BSTNode<T> node) {
		int balanceLeft = this.calculateBalance((BSTNode<T>) node.getLeft());

		if (balanceLeft > 0) {
			Util.rightRotation(node);
			LLcounter++;
		} else if (balanceLeft < 0) {
			Util.leftRotation((BSTNode<T>) node.getLeft());
			LRcounter++;
			Util.rightRotation(node);

		}
		if (this.getRoot().equals(node)) {
			this.root = (BSTNode<T>) node.getParent();
		}
	}

	@Override
	protected void rebalanceRight(BSTNode<T> node) {
		int balanceRight = this.calculateBalance((BSTNode<T>) node.getRight());

		if (balanceRight < 0) {
			Util.leftRotation(node);
			RRcounter++;
		} else if (balanceRight > 0) {
			Util.rightRotation((BSTNode<T>) node.getRight());
			RLcounter++;
			Util.leftRotation(node);
		}
		if (this.getRoot().equals(node)) {
			this.root = (BSTNode<T>) node.getParent();
		}
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		BSTNode<T> aux = this.root;
		for(T element : array) {
			insertRecursivo(element, aux);
		}
	}
	
	private void insertRecursivo(T element, BSTNode<T> node) {
		if(node.getData() == null) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.setRight(new BSTNode<T>());
			node.getRight().setParent(node);
		}
		else {
			if(node.getData().compareTo(element) > 0) {
				insertRecursivo(element, (BSTNode<T>) node.getLeft());
			}
			else if(node.getData().compareTo(element) < 0) {
				insertRecursivo(element, (BSTNode<T>) node.getRight());
			}
		}
	}

}
