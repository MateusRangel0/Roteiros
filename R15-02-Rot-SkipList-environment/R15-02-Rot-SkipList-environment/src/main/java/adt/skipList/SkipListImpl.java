package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve conectar
	 * todos os forward. Senao o ROOT eh inicializado com level=1 e o metodo deve
	 * conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(int key, T newValue, int height) {
		if (newValue != null && height > 0 && height <= maxHeight) {

			SkipListNode<T> current = this.root;
			SkipListNode<T>[] update = new SkipListNode[maxHeight];

			for (int i = maxHeight - 1; i >= 0; i--) {
				while (current.getForward(i) != null && current.getForward(i).getKey() < key) {
					current = current.getForward(i);
				}
				update[i] = current;
			}
			current = current.getForward(0);

			if (current.getKey() == key) {
				current.setValue(newValue);
			} else {
				SkipListNode<T> node = new SkipListNode<T>(key, height, newValue);
				for (int i = 0; i < height; i++) {
					node.forward[i] = update[i].getForward(i);
					update[i].forward[i] = node;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> current = root;

		for (int i = height() - 1; i >= 0; i--) {
			while (current.getForward(i).getKey() < key) {
				current = current.getForward(i);
			}
			update[i] = current;
		}
		current = current.getForward(0);
		if (current.getKey() == key) {
			for (int i = 0; i < height(); i++) {
				if (!update[i].getForward(i).equals(current)) {
					return;
				}
				update[i].forward[i] = current.getForward(i);
			}
		}
	}

	@Override
	public int height() {
		int height = maxHeight - 1;

		while (height > -1
				&& (root.getForward(height) == null || root.getForward(height).getKey() == Integer.MAX_VALUE)) {
			height--;
		}
		return height + 1;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> current = this.root;
		SkipListNode<T> ret = null;
		for (int i = height() - 1; i >= 0; i--) {
			while (current.getForward(i).getKey() < key) {
				current = current.getForward(i);
			}
		}
		current = current.getForward(0);
		if(current.getKey() == key) {
			ret = current;
		}
		return ret;
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(SkipListNode<T> node) {
		if (node.getForward(0).getKey() == Integer.MAX_VALUE) {
			return 0;
		} else {
			return 1 + size(node.getForward(0));
		}
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[size() + 2];
		SkipListNode<T> node = root;
		int index = 0;
		while (index < size() + 2) {
			array[index++] = node;
			node = node.forward[0];
		}
		return array;
	}

}
