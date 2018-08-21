package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) this.getRoot());
	}

	private int blackHeight(RBNode<T> node) {
		int height = 0;
		// altura de um node vazio
		if (node.isEmpty()) {
			return 0;
		} else {

			int rightHeight = blackHeight((RBNode<T>) node.getRight());
			int leftHeight = blackHeight((RBNode<T>) node.getLeft());

			// pegando a maior altura possivel
			height = Math.max(rightHeight, leftHeight);
		}

		// se for um node BLACK, soma mais um na altura
		if (node.getColour().equals(Colour.BLACK)) {
			height++;
		}
		return height;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed by
	 * the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
		// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must be
	 * BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) this.root);
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		if (node != null && !node.isEmpty() && !node.getColour().equals(Colour.BLACK)) {
			return ((RBNode<T>) node.getLeft()).getColour().equals(Colour.BLACK)
					&& ((RBNode<T>) node.getRight()).getColour().equals(Colour.BLACK);
		}
		if ((!node.getColour().equals(Colour.RED)) && !node.isEmpty()) {
			return verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
		}
		return true;
	}

	private boolean verifyChildrenOfNode(RBNode<T> node) {
		boolean ret = true;
		// se o node nao for uma folha e ele for RED
		if (!node.isLeaf() && node.getColour().equals(Colour.RED)) {
			RBNode<T> leftNode = (RBNode<T>) node.getLeft();
			RBNode<T> rightNode = (RBNode<T>) node.getRight();

			// se um dos filhos nao forem BLACK, a variavel de retorno se torna false
			if (!leftNode.getColour().equals(Colour.BLACK)) {
				ret = false;
			}
			if (!rightNode.getColour().equals(Colour.BLACK)) {
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		return verifyBlackHeight((RBNode<T>) this.getRoot());
	}

	private boolean verifyBlackHeight(RBNode<T> node) {
		if (node.isEmpty()) {
			return true;
		} else {
			int leftHeight = blackHeight((RBNode<T>) node.getLeft());
			int rightHeight = blackHeight((RBNode<T>) node.getRight());

			if (leftHeight != rightHeight) {
				return false;
			} else {
				return verifyBlackHeight((RBNode<T>) node.getLeft()) && verifyBlackHeight((RBNode<T>) node.getRight());
			}
		}
	}

	@Override
	public void insert(T value) {
		if (value != null) {
			insert(value, (RBNode<T>) this.getRoot());
		}
	}

	private void insert(T element, RBNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new RBNode<T>());
			node.setRight(new RBNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
			node.setColour(Colour.RED);
			fixUpCase1(node);
		} else {
			if (element.compareTo(node.getData()) != 0) {
				if (element.compareTo(node.getData()) > 0) {
					if (node.getRight() instanceof RBNode<?>) {
						insert(element, (RBNode<T>) node.getRight());
					}
				} else {
					if (node.getLeft() instanceof RBNode<?>) {
						insert(element, (RBNode<T>) node.getLeft());
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RBNode<T>[] rbPreOrder() {
		RBNode<T>[] ret = new RBNode[this.size()];
		preOrder((RBNode<T>) this.getRoot(), ret);
		return ret;
	}

	private void preOrder(RBNode<T> node, RBNode<T>[] ret) {
		if (!node.isEmpty()) {
			this.addElement(ret, node);
			this.preOrder((RBNode<T>) node.getLeft(), ret);
			this.preOrder((RBNode<T>) node.getRight(), ret);

		}
	}

	private void addElement(RBNode<T>[] ret, RBNode<T> node) {
		int i = 0;
		while (ret[i] != null) {
			i++;
		}
		ret[i] = node;
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(this.root)) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!((RBNode<T>) node.getParent()).getColour().equals(Colour.BLACK)) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> uncle = getUncle(node);
		if (uncle.getColour().equals(Colour.RED)) {
			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);
			fixUpCase1((RBNode<T>) node.getParent().getParent());
		} else {
			fixUpCase4(node);
		}
	}

	private RBNode<T> getUncle(RBNode<T> node) {
		RBNode<T> ret = null;
		// se o parent do node for RED
		if (!node.isEmpty()) {
			// Se o parent do node for o left do avo, retorna o right (tio do node)
			if (node.getParent().getParent().getLeft().equals(node.getParent())) {
				ret = ((RBNode<T>) node.getParent().getParent().getRight());
			}
			// se o parent do node for o right do avo, retorna o left (tio do node)
			else if (node.getParent().getParent().getRight().equals(node.getParent())) {
				ret = ((RBNode<T>) node.getParent().getParent().getLeft());
			}
		}
		return ret;
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		// se o node eh filho direito e o parent do node eh filho esquerdo
		if (node.getParent().getRight().equals(node)
				&& node.getParent().getParent().getLeft().equals(node.getParent())) {
			Util.leftRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
		}
		// se o node e filho esquerdo e o parent do node eh filho direito
		else if ((node.getParent().getLeft().equals(node))
				&& node.getParent().getParent().getRight().equals(node.getParent())) {
			Util.rightRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}
		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
		((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);
		// se o node for filho esquerdo
		if (node.getParent().getLeft().equals(node)) {
			Util.rightRotation((RBNode<T>) node.getParent().getParent());
		} else {
			Util.leftRotation((RBNode<T>) node.getParent().getParent());
		}
	}
}
