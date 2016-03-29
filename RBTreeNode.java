public class RBTreeNode {

	private int id;
	private int color;
	private int count;
	private RBTreeNode left;
	private RBTreeNode right;
	private RBTreeNode parent;
	private int leftRight;

	public RBTreeNode(int id, int count) {
		this.id = id;
		this.count = count;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.color = 0;
		this.leftRight = -1;
	}

	public RBTreeNode() {
		// TODO Auto-generated constructor stub
		this.id = 0;
		this.count = 0;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.color = 0;
		this.leftRight = -1;
	}

	public RBTreeNode getLeft() {
		return left;
	}

	public void setLeft(RBTreeNode left) {
		this.left = left;
	}

	public RBTreeNode getRight() {
		return right;
	}

	public void setRight(RBTreeNode right) {
		this.right = right;
	}

	public RBTreeNode getParent() {
		return parent;
	}

	public void setParent(RBTreeNode parent, int lr) {
		this.parent = parent;
		this.leftRight = lr;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setChild(RBTreeNode node, int i) {
		if (i == 0) {
			setLeft(node);
		} else {
			setRight(node);
		}
	}

	public int getLeftRIght() {
		return leftRight;
	}

	public void setLeftRight(int lr) {
		this.leftRight = lr;
	}

	public boolean isEqualTo(RBTreeNode test) {
		if (test != null && test.getId() == this.id) {
			return true;
		}
		return false;
	}

}
