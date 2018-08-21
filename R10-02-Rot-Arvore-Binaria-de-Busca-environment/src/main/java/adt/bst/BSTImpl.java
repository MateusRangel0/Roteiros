package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.getRoot());
	}

	private int height(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			int height;
			int right = height((BSTNode<T>) node.getRight());
			int left = height((BSTNode<T>) node.getLeft());

			if (right > left) {
				height = right;
			} else {
				height = left;
			}
			return 1 + height;
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		if (element == null || this.isEmpty()) {
			return new BSTNode<T>();
		} else {
			return search(element, this.getRoot());
		}
	}

	private BSTNode<T> search(T element, BSTNode<T> node) {
		if (node.getData().compareTo(element) == 0) {
			return node;
		} else if (!node.getLeft().isEmpty() && node.getData().compareTo(element) < 0) {
			search(element, (BSTNode<T>) node.getLeft());
		} else if (!node.getRight().isEmpty() && node.getData().compareTo(element) > 0) {
			search(element, (BSTNode<T>) node.getRight());
		}
		return new BSTNode<T>();
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insert(element, this.getRoot());
		}
	}

	private void insert(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (element.compareTo(node.getData()) != 0) {
				if (element.compareTo(node.getData()) > 0) {
					insert(element, (BSTNode<T>) node.getRight());
				} else {
					insert(element, (BSTNode<T>) node.getLeft());
				}
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		if (!this.isEmpty()) {
			return maximum(this.getRoot());
		}
		return null;
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.getRight().isEmpty()) {
			return node;
		} else {
			return maximum((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if (!this.isEmpty()) {
			return minimum(this.getRoot());
		}
		return null;
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return minimum((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);

		if (element == null || this.isEmpty() || node.isEmpty()) {
			return null;
		}

		else if (!node.getRight().isEmpty()) {
			return ((BST<T>) node.getRight()).minimum();
		}

		else {
			return sucessor(element, node);
		}
	}
	
	private BSTNode<T> sucessor(T element, BSTNode<T> node) {
		if(node.getParent() == null) {
			return null;
		}
		else if(node.getParent() != null && no)
	}
	
	@Override
	public BSTNode<T> predecessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand how
	 * it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
