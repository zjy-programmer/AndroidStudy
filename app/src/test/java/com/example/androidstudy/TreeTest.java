package com.example.androidstudy;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author zjy
 * @date 2023/2/23 10:38
 * @description 用于练习左神的二叉树题
 */
public class TreeTest {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    // 前序打印 递归
    public void preOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    // 中序打印 递归
    public void inOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        inOrderRecur(head.left);
        System.out.println(head.value + " ");
        inOrderRecur(head.right);
    }

    // 后序打印 递归
    public void posOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        posOrderRecur(head.left);
        posOrderRecur(head.right);
        System.out.println(head.value + " ");
    }

    // 前序打印 迭代
    public void preOrderUnRecur(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            System.out.print(pop.value + " ");
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
    }

    // TODO: 2023/2/23 需要多写写
    // 中序打印 迭代
    public void inOrderUnRecur(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head == null) {
                Node pop = stack.pop();
                System.out.println(pop.value + " ");
                head = pop.right;
            } else {
                stack.push(head);
                head = head.left;
            }

        }
    }

    // TODO: 2023/2/23 需要多写写
    // 后序打印 迭代
    public void posOrderUnRecur1(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();
        s1.push(head);
        while (!s1.isEmpty()) {
            Node pop = s1.pop();
            s2.push(pop);
            if (pop.left != null) {
                s1.push(pop.left);
            }
            if (pop.right != null) {
                s1.push(pop.right);
            }
        }
        while (!s2.isEmpty()) {
            Node pop = s2.pop();
            System.out.print(pop.value + " ");
        }
    }

    // TODO: 2023/2/23 不会
    // 只用一个栈实现 后序打印 迭代
    public void posOrderUnRecur2(Node h) {

    }

    // TODO: 2023/2/24 跟左神写的不一样 左神的貌似有问题 还需要再看下视频确认下
    // 如何完成二叉树的宽度优先遍历(常见题目:求一棵二叉树的宽度)
    public int getMaxWidth(Node head) {
        if (head == null) {
            return 0;
        }
        int maxWidth = 0;
        int curWidth = 0;
        int curLevel = 0;
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(head);
        Node node = null;
        Node left = null;
        Node right = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            left = node.left;
            right = node.right;
            if (left != null) {
                levelMap.put(left, levelMap.get(node) + 1);
                queue.add(left);
            }
            if (right != null) {
                levelMap.put(right, levelMap.get(node) + 1);
                queue.add(right);
            }
            if (levelMap.get(node) > curLevel) {
                curWidth = 1;
                curLevel = levelMap.get(node);
            } else {
                curWidth++;
            }
            maxWidth = Math.max(maxWidth, curWidth);
        }
        return maxWidth;
    }

    // TODO: 2023/2/24 不会
    // 如何判断一颗二叉树是否是二叉搜索树? 左孩子都小于根节点 右孩子都大于根节点 就是二叉搜索树
    public boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        // 二叉搜索树 中序遍历的话正好是从小到大的
        LinkedList<Node> inorderList = new LinkedList<>();
        BSTInorder(head, inorderList);
        int min = Integer.MIN_VALUE;
        for (Node node : inorderList) {
            if (node.value <= min) {
                return false;
            }
            min = node.value;
        }
        return true;
    }

    private void BSTInorder(Node head, LinkedList<Node> inorderList) {
        if (head == null) {
            return;
        }
        BSTInorder(head.left, inorderList);
        inorderList.add(head);
        BSTInorder(head.right, inorderList);
    }

    // TODO: 2023/2/24 不会
    // 如何判断一颗二叉树是完全二叉树?
    public boolean isCBT(Node head) {
        if (head == null) {
            return true;
        }
        // 有两种情况不是完全二叉树
        // 1. 某个节点没有左孩子 但是有右孩子 一定不是完全二叉树
        // 2. 当某个节点没有右节点时 那么认为剩下的节点均为叶子结点 如果有一个不是 则不是完全二叉树
        boolean isLeaf = false;
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(head);
        Node left = null;
        Node right = null;
        while (!queue.isEmpty()) {
            Node pop = queue.pop();
            left = pop.left;
            right = pop.right;
            if ((isLeaf && (left != null || right != null)) || (left == null && right != null)) {
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            } else {
                isLeaf = true;
            }
        }
        return true;
    }

    // TODO: 2023/2/24 不会
//    public boolean isBalanced(Node head) {
//
//    }

    // TODO: 2023/2/24 不会
    // 给定两个二叉树的节点node1和node2，找到他们的最低公共祖先节点
//    public Node lowestAncestor(Node head, Node o1, Node o2) {
//
//    }

    // TODO: 2023/2/24 没有左神写的简洁 而且还需要更多数据测一下对不对
    /**
     * 在二叉树中找到一个节点的后继节点
     * KMP算法扩展题目二
     * 【题目】 现在有一种新的二叉树节点类型如下:
     * public class Node {
     *     public int value;
     *     public Node left;
     *     public Node right;
     *     public Node parent;
     *     public Node(int val) {
     * value = val; }
     * }
     * 该结构比普通二叉树节点结构多了一个指向父节点的parent指针。 假设有一棵Node类型的节点组成的二叉树，树中每个节点的parent指针都正确地指向自己的父节点，头节 点的parent指向null。
     * 只给一个在二叉树中的某个节点node，请实现返回node的后继节点的函数。 在二叉树的中序遍历的序列中， node的下一个节点叫作node的后继节点。
     */
    public Node getSuccessorNode(Node node) {
        // 如果有右节点 那后继节点就是右节点的一直往左走到头的节点
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        // 来到这就是没有右节点的了

        // 如果是根节点 那么后继是null
        if (node.parent == null) {
            return null;
        }
        // 如果是在父节点的左边 左孩子 则父节点就是后继
        if (node.parent.left == node) {
            return node.parent;
        }
        // 如果在父节点的右边 右孩子 则一路向上找 直到找到第一个是左孩子的节点 那么这个节点的父节点就是后继节点
        Node parent = node.parent;
        if (parent.parent == null) {
            return null;
        }
        while (parent.parent.right == parent) {
            parent = parent.parent;
            if (parent == null || parent.parent == null) {
                return null;
            }
        }
        return parent.parent;
    }

    // TODO: 2023/2/24 先跳过
    // 二叉树的序列化和反序列化


    // TODO: 2023/2/27 忘了解题技巧了
    /**
     * 折纸问题
     * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后 展开。
     * 此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从 上到下依次是下折痕、下折痕和上折痕。 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。 请从上到下打印所有折痕的方向。
     * 例如:N=1时，打印: down N=2时，打印: down down up
     */



    @Test
    public void test() {
        {
            Node head = new Node(6);
            head.parent = null;
            head.left = new Node(3);
            head.left.parent = head;
            head.left.left = new Node(1);
            head.left.left.parent = head.left;
            head.left.left.right = new Node(2);
            head.left.left.right.parent = head.left.left;
            head.left.right = new Node(4);
            head.left.right.parent = head.left;
            head.left.right.right = new Node(5);
            head.left.right.right.parent = head.left.right;
            head.right = new Node(9);
            head.right.parent = head;
            head.right.left = new Node(8);
            head.right.left.parent = head.right;
            head.right.left.left = new Node(7);
            head.right.left.left.parent = head.right.left;
            head.right.right = new Node(10);
            head.right.right.parent = head.right;

            Node test = head.left.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left.left.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left.right.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right.left.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right.right; // 10's next is null
            System.out.println(test.value + " next: " + getSuccessorNode(test));
        }

//        Node head = new Node(5);
//        head.left = new Node(3);
//        head.right = new Node(8);
//        head.left.left = new Node(2);
//        head.left.right = new Node(4);
//        head.left.left.left = new Node(1);
//        head.left.left.right = new Node(15);
//        head.right.left = new Node(7);
//        head.right.left.left = new Node(6);
//        head.right.right = new Node(10);
//        head.right.right.left = new Node(9);
//        head.right.right.right = new Node(11);
//
//        System.out.println("最大宽度是：" + getMaxWidth(head));

        // recursive
//        System.out.println("==============recursive==============");
//        System.out.print("pre-order: ");
//        preOrderRecur(head);
//        System.out.println();
//        System.out.print("in-order: ");
////        inOrderRecur(head);
//        System.out.println();
//        System.out.print("pos-order: ");
////        posOrderRecur(head);
//        System.out.println();

        // unrecursive
//        System.out.println("============unrecursive=============");
//        preOrderUnRecur(head);
//        inOrderUnRecur(head);
//        posOrderUnRecur1(head);
//        posOrderUnRecur2(head);

    }
}
