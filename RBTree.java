public class RBTree {
	private RBTreeNode root;
	private int smallest;
	private int largest;

	public RBTree() {
		this.root = null;
		this.smallest = Integer.MIN_VALUE;
		this.largest = Integer.MAX_VALUE;
	}

	public RBTreeNode insertAsList(int[] arr, int maxHeight) {
		// TODO Auto-generated method stub
		this.root = buildTreeFromList(arr, 0, arr.length-1, maxHeight);
		root.setParent(null, -1);
		this.smallest = arr[0];
		this.largest = arr[arr.length-1];
		return null;
	}
	
	private RBTreeNode buildTreeFromList(int[] arr, int start, int end, int maxHeight) {
		int rootInd = (start + end) / 2;
		if (start >= end) {
			return null;
		}
		if (rootInd % 2 != 0) {
			rootInd++;
		}
		RBTreeNode root = new RBTreeNode(arr[rootInd], arr[rootInd + 1]);
		RBTreeNode left = buildTreeFromList(arr, start, rootInd - 1, maxHeight - 1);
		RBTreeNode right = buildTreeFromList(arr, rootInd + 1, end, maxHeight - 1);

		if (maxHeight > 1) {
			root.setColor(1);
		} else {
			root.setColor(0);
		}
		root.setLeft(left);
		setParent(left, root, 0);
		root.setRight(right);
		setParent(right, root, 1);
		return root;
	}

	public void insert(RBTreeNode node) {
		if (root == null) {
			this.root = node;
			root.setColor(1);
			// this.lastAdded = root;
			largest = root.getId();
			smallest = root.getId();
		} else {
			int colorCheck = -1;
			RBTreeNode temp = search(root, node.getId(), "insert");

			if (temp.getId() > node.getId()) {
				setLeft(temp, node);
				setParent(node, temp, 0);
				if (getColor(temp) == getColor(node))
					colorCheck = (temp.getLeftRIght()) * 10 + 0;
			} else if (temp.getId() < node.getId()) {
				setRight(temp, node);
				setParent(node, temp, 1);
				if (getColor(temp) == getColor(node))
					colorCheck = (temp.getLeftRIght()) * 10 + 1;
			}
			if (colorCheck >= 0) {
				balanceInsert(temp.getParent(), colorCheck);
			}
		}
	}

	private void balanceInsert(RBTreeNode root2, int colorCheck) {
		// TODO Auto-generated method stub
		RBTreeNode parent = root2.getParent();
		RBTreeNode sibling = getSibling(root2, parent);
		if (getColor(root2) == getColor(sibling)) {
			root2.setColor(1);
			sibling.setColor(1);
			if (!parent.isEqualTo(root)) {
				parent.setColor(0);
				if (getColor(parent.getParent()) == getColor(parent)) {
					colorCheck = (parent.getParent().getLeftRIght()) * 10
							+ parent.getLeftRIght();
					balanceInsert(parent.getParent(), colorCheck);
				}
			}
		} else {
			rotate(parent, colorCheck, "insert");
		}
	}
	
	public void delete(RBTreeNode node){
		RBTreeNode dbbParent = bstDelete(node);
		if (null != dbbParent) {
			balanceDelete(dbbParent, null);
		}	
	}

	private RBTreeNode bstDelete(RBTreeNode root2) {

		RBTreeNode parent;
		RBTreeNode child;
		if (root2.getLeft() == null || root2.getRight() == null) {
			parent = root2.getParent();
			child = root2.getLeft() == null ? root2.getRight() : root2
					.getLeft();

			parent.setChild(child, root2.getLeftRIght());
			if (child == null) {
				if (getColor(root2) == 0) {
					return null;
				}
			} else {
				if (!(getColor(root2) == 1 && getColor(child) == 1)) {
					child.setColor(1);
					return null;
				}
			}
		} else {
			RBTreeNode temp = root2.getRight();
			while (temp.getLeft() != null) {
				temp = temp.getLeft();
			}
			root2.setId(temp.getId());
			root2.setCount(temp.getCount());
			return bstDelete(temp);
		}
		return parent;
	}

	private void balanceDelete(RBTreeNode parent, RBTreeNode dbbNode) {
		// TODO Auto-generated method stub
		RBTreeNode dbbSibling = getSibling(dbbNode, parent);
		int colorCheck;
		if (getColor(dbbNode) == getColor(dbbSibling)) {
			if (getColor(dbbSibling.getLeft()) == 1
					&& getColor(dbbSibling.getRight()) == 1) {
				dbbSibling.setColor(0);
				parent.setColor(1);
				if (!parent.getParent().isEqualTo(root)) {
					balanceDelete(parent.getParent(), parent);
				}
			} else {
				colorCheck = (dbbSibling.getLeftRIght()) * 10
						+ getColor(dbbSibling.getLeft());
				rotate(dbbSibling.getParent(), colorCheck, "delete");
			}
		} else {
			int lr = dbbSibling.getLeftRIght();
			colorCheck = lr * 10 + lr;
			rotate(dbbSibling.getParent(), colorCheck, "delete");
		}
	}

	private void rotate(RBTreeNode root2, int colorCheck, String op) {
		// TODO Auto-generated method stub
		RBTreeNode newTempRoot = new RBTreeNode();
		if (colorCheck == 0) {
			RBTreeNode left = root2.getLeft();
			RBTreeNode rootParent = root2.getParent();
			setParent(left, rootParent, root2.getLeftRIght());
			if (null != rootParent) {
				rootParent.setChild(left, root2.getLeftRIght());
			}
			setLeft(root2, left.getRight());
			setParent(left.getRight(), root2, 0);
			setRight(left, root2);
			setParent(root2, left, 0);
			newTempRoot = left;
		} else if (colorCheck == 1) {
			RBTreeNode left = root2.getLeft();
			RBTreeNode leftRight = left.getRight();
			setParent(leftRight.getLeft(), left, 0);
			setRight(left, leftRight.getLeft());
			setParent(leftRight, root2, left.getLeftRIght());
			setLeft(root2, leftRight);
			setParent(left, leftRight, 0);
			setLeft(leftRight, left);
			rotate(root2, 11, "insert");
			// newTempRoot = leftRight;
		} else if (colorCheck == 10) {
			RBTreeNode right = root2.getRight();
			RBTreeNode rightLeft = right.getLeft();
			setParent(rightLeft.getRight(), right, 0);
			setLeft(right, rightLeft.getRight());
			setParent(rightLeft, root2, right.getLeftRIght());
			setRight(root2, rightLeft);
			setParent(right, rightLeft, 1);
			setRight(rightLeft, right);
			rotate(root2, 11, "insert");
			// newTempRoot = rightLeft;
		} else if (colorCheck == 11) {
			RBTreeNode right = root2.getRight();
			RBTreeNode rootParent = root2.getParent();
			setParent(right, rootParent, root2.getLeftRIght());
			if (null != rootParent) {
				rootParent.setChild(right, root2.getLeftRIght());
			}
			setRight(root2, right.getLeft());
			setParent(right.getLeft(), root2, 1);
			setLeft(right, root2);
			setParent(root2, right, 0);
			newTempRoot = right;
		}
		if (op.equals("delete")) {
			root2.setColor(1);
			newTempRoot.setColor(0);
		} else {
			root2.setColor(0);
			newTempRoot.setColor(1);
		}
		if (root2.getId() == root.getId()) {
			root = newTempRoot;
		}
	}

	private RBTreeNode search(RBTreeNode root, int i, String op) {
		if (op.equals("insert")) {
			if ((root.getLeft() == null && root.getId() > i)
					|| (root.getRight() == null && root.getId() < i)) {
				return root;
			} else {
				if (root.getId() > i) {
					return search(root.getLeft(), i, op);
				} else if (root.getId() < i) {
					return search(root.getRight(), i, op);
				} else {
					return root;
				}
			}
		} else {
			if (root == null || root.getId() == i) {
				return root;
			} else {
				if (root.getId() > i) {
					return search(root.getLeft(), i, op);
				} else {
					return search(root.getRight(), i, op);
				}
			}
		}
	}

	private RBTreeNode getSibling(RBTreeNode node, RBTreeNode parent) {
		RBTreeNode left = parent.getLeft();
		RBTreeNode right = parent.getRight();
		if (node == null) {
			return left == null ? right : left;
		} else {
			return node.isEqualTo(left) ? right : left;
		}
	}

	private int getColor(RBTreeNode node) {
		if (node == null) {
			return 1;
		} else {
			return node.getColor();
		}
	}

	private void setParent(RBTreeNode node1, RBTreeNode node2, int lr) {
		if (null != node1) {
			node1.setParent(node2, lr);
		}
	}

	private void setLeft(RBTreeNode node1, RBTreeNode node2) {
		if (null != node1) {
			node1.setLeft(node2);
		}
	}

	private void setRight(RBTreeNode node1, RBTreeNode node2) {
		if (null != node1) {
			node1.setRight(node2);
		}
	}

	public void Increase(int i, int j) {
		// TODO Auto-generated method stub
		RBTreeNode temp = search(root, i, "increase");
		int tempCount;
		if (null != temp) {
			tempCount = temp.getCount() + j;
			temp.setCount(tempCount);
			System.out.println(tempCount);
		} else {
			insert(new RBTreeNode(i, j));
			this.largest = i;
			System.out.println(j);
		}
	}

	public void Reduce(int i, int j) {
		// TODO Auto-generated method stub
		RBTreeNode temp = search(root, i, "reduce");
		int tempCount = 0;
		if (null != temp) {
			temp.setCount(temp.getCount() - j);
			tempCount = temp.getCount();
			if (temp.getCount() <= 0) {
				if (null != root) {
					delete(temp);
				}
			}
			tempCount = tempCount > 0 ? tempCount : 0;
		}
		System.out.println(tempCount);
	}

	public void Count(int i) {
		// TODO Auto-generated method stub
		System.out.println(search(root, i, "find").getCount());
	}

	public void InRange(int i, int j) {
		// TODO Auto-generated method stub
		System.out.println(getCountInRange(root, i, j));
	}

	private int getCountInRange(RBTreeNode root2, int i, int j) {
		// TODO Auto-generated method stub
		int count;
		if (root2 != null && (root2.getId() >= i && root2.getId() <= j)) {
			count = getCountInRange(root2.getLeft(), i, j)
					+ getCountInRange(root2.getRight(), i, j)
					+ root2.getCount();
			return count;
		} else if (root2 == null) {
			return 0;
		} else {
			if (root2.getId() <= i) {
				return getCountInRange(root2.getRight(), i, j);
			} else {
				return getCountInRange(root2.getLeft(), i, j);
			}
		}
	}

	public void Next(int i) {
		if (i >= largest) {
			System.out.println("0 0");
		} else {
			RBTreeNode temp = search(root, i, "insert");
			if (temp.getId() > i) {
				System.out.println(temp.getId() + " " + temp.getCount());
			} else if (temp.getId() < i) {
				while (temp.getId() < i || temp == root) {
					temp = temp.getParent();
				}
				System.out.println(temp.getId() + " " + temp.getCount());
			} else {
				if (temp.getLeftRIght() == 0) {
					RBTreeNode parent = temp.getParent();
					int diff = parent.getId() - i;
					if (temp.getRight() != null) {
						temp = temp.getRight();
						while (temp.getLeft() != null) {
							temp = temp.getLeft();
						}
					}
					if (temp.getId() - i > diff || temp.getId() == i) {
						temp = parent;
					}
				} else {
					RBTreeNode distantParent = temp.getParent();
					while (distantParent.getId() < i || distantParent == root) {
						distantParent = distantParent.getParent();
					}
					int diff = distantParent.getId() - i;
					if (temp.getRight() != null) {
						temp = temp.getRight();
						while (temp.getLeft() != null) {
							temp = temp.getLeft();
						}
					}
					if (temp.getId() - i > diff || temp.getId() == i) {
						temp = distantParent;
					}
				}
				System.out.println(temp.getId() + " " + temp.getCount());
			}
		}
	}

	public void Previous(int i) {
		if (i <= smallest) {
			System.out.println("0 0");
		} else {
			RBTreeNode temp = search(root, i, "insert");
			if (temp.getId() < i) {
				System.out.println(temp.getId() + " " + temp.getCount());
			} else if (temp.getId() > i) {
				while (temp.getId() > i || temp == root) {
					temp = temp.getParent();
				}
				System.out.println(temp.getId() + " " + temp.getCount());
			} else {
				if (temp.getLeftRIght() == 1) {
					RBTreeNode parent = temp.getParent();
					int diff = i - parent.getId();
					if (temp.getLeft() != null) {
						temp = temp.getLeft();
						while (temp.getRight() != null) {
							temp = temp.getRight();
						}
					}
					if (i - temp.getId() > diff || temp.getId() == i) {
						temp = parent;
					}
				} else {
					RBTreeNode distantParent = temp.getParent();
					while (distantParent.getId() > i || distantParent == root) {
						distantParent = distantParent.getParent();
					}
					int diff = i - distantParent.getId();
					if (temp.getLeft() != null) {
						temp = temp.getLeft();
						while (temp.getRight() != null) {
							temp = temp.getRight();
						}
					}
					if (i - temp.getId() > diff || temp.getId() == i) {
						temp = distantParent;
					}
				}
				System.out.println(temp.getId() + " " + temp.getCount());
			}
		}
	}

}
