package adt.btree;

import java.util.ArrayList;
import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			int height = 0;

			if (!node.isLeaf()) {
				height = 1 + height(node.getChildren().get(0));
			}
			return height;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public BNode<T>[] depthLeftOrder() {
		ArrayList<BNode<T>> list = new ArrayList<>();

		this.depthLeftOrder(list, this.getRoot());

		BNode<T> array[] = new BNode[list.size()];
		return list.toArray(array);

	}

	private void depthLeftOrder(ArrayList<BNode<T>> list, BNode<T> node) {
		if (!node.isEmpty()) {
			list.add(node);
			LinkedList<BNode<T>> children = node.getChildren();

			for (BNode<T> element : children) {
				depthLeftOrder(list, element);
			}
		}
	}

	@Override
	public int size() {
		return this.size(this.getRoot());
	}

	private int size(BNode<T> node) {
		if (node.isEmpty()) {
			return 0;
		} else {
			int size = node.size();

			if (!node.isLeaf()) {
				LinkedList<BNode<T>> children = node.getChildren();
				for (BNode<T> element : children) {
					size += size(element);
				}
			}
			return size;
		}
	}

	@Override
	public BNodePosition<T> search(T element) {
		if (element != null) {
			return search(element, this.getRoot());
		}
		return null;
	}

	private BNodePosition<T> search(T element, BNode<T> node) {
		int i = 1;
		
		while(i <= node.size() && element.compareTo(node.getElementAt(i)) > 0){
			i++;
		}
		if(node.getElementAt(i).compareTo(element) == 0) {
			BNodePosition<T> ret = new BNodePosition<T>(node, i);
			return ret;
		}
		if(node.isLeaf()) {
			return null;
		}
		return search(element, node.getChildren().get(i));
	}

	@Override
	public void insert(T element) {
		if(element != null) {
			this.insert(element, this.getRoot());
		}
	}
	
	private void insert(T element, BNode<T> node) {
		if(node.isLeaf()) {
			node.addElement(element);
			
			if(node.getElements().size() > node.getMaxKeys()) {
				split(node);
			}
		}
		else {
			LinkedList<T> elements = node.getElements();
			
			int i = 0;
			while(i < elements.size() && elements.get(i).compareTo(element) < 0) {
				i++;
			}
			
			insert(element, node.getChildren().get(i));
		}
	}

	private void split(BNode<T> node) {
		node.split();
	}

	private void promote(BNode<T> node) {
		node.promote();
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
