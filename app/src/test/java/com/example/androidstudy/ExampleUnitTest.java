package com.example.androidstudy;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        String s1 = "ahkshdkahd";
        System.out.println("s1: " + s1 + " hashCode: " + s1.hashCode());
        test2(s1);
    }

    public void test2(String s2) {
        s2 = "1213313132";
        System.out.println("s2: " + s2 + " hashCode: " + s2.hashCode());
    }

    @Test
    public void testReverseList() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        ListNode node = reverseList1(node1);
        System.out.println(node);
    }

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);
        int r = 0;
        for (int i = 0; i < seats.length; i++) {
            int seat = seats[i];
            int student = students[i];
            r += Math.abs(student - seat);
        }

        return r;
    }

    public int deleteGreatestValue(int[][] grid) {
        int r = 0;
        // 先把每一行都排好序
        for (int[] ints : grid) {
            Arrays.sort(ints);
        }
        // 把每一列的最大值找出来 累加在r上
        int max = 0;
        int columnSize = grid[0].length;
        for (int i = 0; i < columnSize; i++) {
            for (int[] ints : grid) {
                max = Math.max(max, ints[i]);
            }
            r += max;
            max = 0;
        }
        return r;
    }

    public int minimumSum(int num) {
        int[] n = new int[4];
        for (int i = 0; i < 4; i++) {
            n[i] = num % 10;
            num = num / 10;
        }
        Arrays.sort(n);
        return (n[0] * 10 + n[2]) + (n[1] * 10 + n[3]);
    }

    public int[] smallerNumbersThanCurrent(int[] nums) {
        // 统计词频
        int[] cnt = new int[101];
        for (int num : nums) {
            cnt[num]++;
        }

        // 小于index为0的肯定是0个 所以从index为1的统计出现次数的前缀和
        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        int[] ret = new int[nums.length];
        // nums 中比0小的肯定是0 其他的数则找这个数在词频数组中的前一位的数
        for (int i = 0; i < nums.length; i++) {
            ret[i] = nums[i] == 0 ? 0 : cnt[nums[i] - 1];
        }
        return ret;
    }

    public int maxProductDifference(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return nums[n - 1] * nums[n - 2] - nums[0] * nums[1];
    }

    public List<Integer> targetIndices(int[] nums, int target) {
        List<Integer> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num == target) {
                res.add(i);
            }
        }
        return res;
    }

    public List<Integer> targetIndices1(int[] nums, int target) {
        int smaller = 0; // 小于target的数量
        int equal = 0; // 等于target的数量
        for (int num : nums) {
            if (num < target) {
                smaller++;
            } else if (num == target) {
                equal++;
            }
        }

        // 因为题目中数组是需要排序的 所以只要找到小于等于target的数量 在从小于的数量开始变量 那么等于的下表自然就出来了
        List<Integer> res = new ArrayList<>();
        for (int i = smaller; i < smaller + equal; i++) {
            res.add(i);
        }
        return res;
    }

    public int maxProduct(int[] nums) {
        Arrays.sort(nums);
        return (nums[nums.length - 1] - 1) * (nums[nums.length - 2] - 1);
    }

    @Test
    public void sortPeople() {
        String[] names = new String[]{"Mary", "John", "Emma"};
        int[] heights = new int[]{180, 165, 170};
        sortPeople(names, heights);
    }

    public String[] sortPeople(String[] names, int[] heights) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return heights[o2] - heights[o1];
            }
        });
        for (int i = 0; i < heights.length; i++) {
            queue.offer(i);
        }
        String[] res = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            res[i] = names[queue.poll()];
        }
        return res;
    }

    public boolean canBeEqual(int[] target, int[] arr) {
        int[] n = new int[1001];
        for (int i : target) {
            n[i]++;
        }
        for (int i : arr) {
            n[i]--;
        }
        for (int i : n) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        int[] n = new int[1001];
        for (int[] ints : items1) {
            n[ints[0]] += ints[1];
        }
        for (int[] ints : items2) {
            n[ints[0]] += ints[1];
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n.length; i++) {
            if (n[i] != 0) {
                ArrayList<Integer> integers = new ArrayList<Integer>();
                integers.add(i);
                integers.add(n[i]);
                res.add(integers);
            }
        }
        return res;
    }


    @Test
    public void frequencySort() {
        frequencySort(new int[]{1, 1, 2, 2, 2, 3});
    }

    public int[] frequencySort(int[] nums) {
        int[] n = new int[201];
        for (int num : nums) {
            n[num + 100]++;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (n[o1] == n[o2]) {
                    return o2 - o1;
                }
                return n[o1] - n[o2];
            }
        });
        for (int i = 0; i < n.length; i++) {
            if (n[i] != 0) {
                queue.offer(i);
            }
        }
        int[] res = new int[nums.length];
        int index = 0;
        while (!queue.isEmpty()) {
            Integer integer = queue.poll();
            int m = n[integer];
            for (int i = 0; i < m; i++) {
                res[index] = integer - 100;
                index++;
            }
        }
        return res;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int j : nums1) {
            map1.put(j, 1);
        }
        for (int j : nums2) {
            map2.put(j, 1);
        }
        List<Integer> res = new ArrayList<>();
        for (Integer integer : map1.keySet()) {
            if (map2.containsKey(integer)) {
                res.add(integer);
            }
        }
        int[] r = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            r[i] = res.get(i);
        }
        return r;
    }

    public int minimumOperations(int[] nums) {
        // 相当于找出数组中非0的不相同的数字个数
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num != 0) {
                set.add(num);
            }
        }
        return set.size();
    }

    public int findFinalValue(int[] nums, int original) {
        Arrays.sort(nums);
        for (int num : nums) {
            if (num == original) {
                original *= 2;
            }
        }
        return original;
    }

    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });
        int res = 0;
        int index = 0;
        while (index < boxTypes.length && truckSize != 0) {
            if (boxTypes[index][0] <= truckSize) {
                res += boxTypes[index][0] * boxTypes[index][1];
                truckSize -= boxTypes[index][0];
            } else {
                res += truckSize * boxTypes[index][1];
                truckSize = 0;
            }
            index++;
        }
        return res;
    }

    public List<Integer> minSubsequence(int[] nums) {
        int maxNum = 0;
        for (int num : nums) {
            maxNum += num;
        }
        Arrays.sort(nums);
        List<Integer> res = new ArrayList<>();
        int cur = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            cur += nums[i];
            res.add(nums[i]);
            maxNum -= nums[i];
            if (cur > maxNum) {
                break;
            }
        }
        return res;
    }

    public int[] sortByBits(int[] arr) {
        // 统计每个数的二进制有多少个1
        int[] bit = new int[10001];
        List<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
            bit[i] = getBit(i);
        }
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (bit[o1] == bit[o2]) {
                    return o1 - o2;
                }
                return bit[o1] - bit[o2];
            }
        });
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public int getBit(int num) {
        int bit = 0;
        while (num != 0) {
            if (num % 2 == 1) {
                bit++;
            }
            num /= 2;
        }
        return bit;
    }

    public String sortSentence(String s) {
        String[] strings = s.split(" ");
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1.substring(o1.length() - 1)) - Integer.parseInt(o2.substring(o2.length() - 1));
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(string.substring(0, string.length() - 1));
        }
        return sb.toString();
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> res = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length - 1; i++) {
            int curMin = Math.abs(arr[i + 1] - arr[i]);
            if (curMin < min) {
                min = curMin;
                res.clear();
                List<Integer> r = new ArrayList<>();
                r.add(arr[i]);
                r.add(arr[i + 1]);
                res.add(r);
            } else if (curMin == min) {
                List<Integer> r = new ArrayList<>();
                r.add(arr[i]);
                r.add(arr[i + 1]);
                res.add(r);
            }
        }

        return res;
    }

    public double trimMean(int[] arr) {
        int deleteNum = arr.length / 20;
        Arrays.sort(arr);
        int n = 0;
        for (int i = deleteNum; i < arr.length - deleteNum; i++) {
            n += arr[i];
        }
        return n * 1.0 / (arr.length - deleteNum * 2);
    }

    public boolean isUnique(String astr) {
        if (astr.length() == 0 || astr.length() == 1) {
            return true;
        }
        char[] chars = new char[astr.length()];
        for (int i = 0; i < astr.length(); i++) {
            chars[i] = astr.charAt(i);
        }
        Arrays.sort(chars);
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] n = new int[1001];
        for (int i : arr1) {
            n[i]++;
        }
        int index = 0;
        for (int i = 0; i < arr2.length; i++) {
            for (int i1 = 0; i1 < n[arr2[i]]; i1++) {
                arr1[index] = arr2[i];
                index++;
            }
            n[arr2[i]] = 0;
        }
        for (int i = 0; i < n.length; i++) {
            if (n[i] != 0) {
                for (int i1 = 0; i1 < n[i]; i1++) {
                    arr1[index] = i;
                    index++;
                }
                n[i] = 0;
            }
        }
        return arr1;
    }

    public void deleteNode(ListNode node) {
        // 和下一个节点交换值就行
        node.val = node.next.val;
        node.next = node.next.next;
    }

    // 回溯 没看懂 回头再做
//    public List<List<Integer>> subsets(int[] nums) {
//
//    }

    public void reverseString(char[] s) {
        int head = 0;
        int tail = s.length - 1;
        while (head < tail) {
            char temp = s[head];
            s[head] = s[tail];
            s[tail] = temp;
            head++;
            tail--;
        }
    }

    // 又是回溯
//    public List<List<Integer>> permute(int[] nums) {
//
//    }

//    public List<String> generateParenthesis(int n) {
//
//    }

    // 选择排序
    public void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 从 0 到 n-1 依次取值
        for (int i = 0; i < arr.length - 1; i++) {
            // 记录最小值的index
            int minIndex = i;
            // 从 i+1开始找最小值 把最小值跟i做交换
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // 冒泡排序
    public void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 外层遍历每一个元素
        for (int i = 0; i < arr.length; i++) {
            // 每次循环都把最大的值放到最后 所以第一次遍历最大值放在length-1 第二次则放在了length-2 以此类推
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 插入排序
    public void insertSorting(int[] arr) {
        // 思想：局部有序 -> 全局有序
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }
        }
    }

    // TODO: 2023/2/15 没写出来 需要多写几遍
    // 二分法 在一个有序数组中，找某个数是否存在
    public boolean dichotomy1(int[] arr, int target) {
        if (arr == null) {
            return false;
        }
        // 用来控制查找范围
        int left = 0;
        int right = arr.length - 1;
        int mid = 0;
        while (left < right) {
            // 位移运算符 比 算术运算符优先级低
            mid = left + ((right - left) >> 1);
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return arr[left] == target;
    }

    // TODO: 2023/2/15 没想出来
    // 在一个有序数组中，找>=某个数最左侧的位置
    public int dichotomy2(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int index = -1;
        int l = 0;
        int r = arr.length - 1;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] >= target) {
                index = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return arr[l] >= target ? l : index;
    }

    // TODO: 2023/2/15 没想出来
    // 局部最小值问题 arr无序 但是任意两个相邻的数一定不相等 index 0的值 < 1的值 0是局部最小 n-1的值 < n-2的值 n-1是局部最小 i - 1的值 < i的值 < i + 1的值 i是局部最小 只要返回一个局部最小就行
    public int dichotomy3(int[] arr) {
        if (arr == null || arr.length < 1) {
            return -1;
        }
        if (arr.length < 2) {
            return 0;
        }
        int index = -1;
        // index 0的值 < 1的值 那么0就是局部最小 直接返回
        if (arr[0] < arr[1]) {
            return 0;
        }
        // index n-1的值 < n-2的值 n-1就是局部最小 直接返回
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        // 走到这说明 第0项 和 最后一项都不是局部最小 那么 0到1是下降趋势 最后一项到倒数第二项是下降趋势 所以在0到n-1中必然存在至少一个局部最小 同时也说明数组长度至少为3
        int l = 0;
        int r = arr.length - 1;
        int m = 0;
        while (l < r) {
            m = l + ((r - l) >> 1);
            if (arr[m - 1] > arr[m] && arr[m] < arr[m + 1]) {
                return m;
            } else if (arr[m - 1] > arr[m]) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    // 异或 不同为1 相同为0 也可以理解为不进位相加

    // 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到这一个数 异或
    public void printOddTimesNum1(int[] arr) {
        // 异或满足交换律和结合律 所以可以理解为所有出现偶数次的数先自己异或 由于n ^ n = 0 0 ^ n = n 所以从头到尾异或下来就能获得出现奇数次的数
        int res = 0;
        for (int i : arr) {
            res ^= i;
        }
        System.out.println("出现奇数次的数：" + res);
    }

    // TODO: 2023/2/16 不会
    // 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到这两个数 异或
    public void printOddTimesNum2(int[] arr) {
        // 设 a 和 b 是出现了奇数次的数
        int eor = 0;
        for (int i : arr) {
            eor ^= i;
        }
        // 走到这 eor = a ^ b
        // 因为是两种数 所以 a 和 b 不相等 则 a ^ b != 0 且 a 和 b 的二进制数至少有一位是不相等的即a的某一位是1的话 b对应的位上的值必为0
        // 所以可以找到最右边第一次出现1的位 然后再次遍历数组 把数组中只有对应位是1的数进行异或 异或后的结果则是 a 或 b
        // 因为偶数次的数异或后为0 而 a 或 b只可能有一个数异或进来
        // 按位与 只有都是1 才是1
        int rightOne = eor & (~eor + 1); // 提取出最右侧的1
        int res = 0;
        for (int i : arr) {
            if ((i & rightOne) == 1) {
                res ^= i;
            }
        }
        // 走到这 res 则是 a 或 b
        // eor ^ res 就是另一个值
        // eor = a ^ b
        // eor ^ res = a ^ b ^ (a 或着 b)
        System.out.println("出现奇数次的两个数分别是：" + res + " 和" + (res ^ eor));
    }

    // TODO: 2023/2/16 不会
    // 用递归方法找一个数组中的最大值
    public int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    public int process(int[] arr, int l, int r) {
        if (l == r) {
            return arr[l];
        }
        int m = l + ((r - l) >> 1);
        int lMax = process(arr, l, m);
        int rMax = process(arr, m + 1, r);
        return Math.max(lMax, rMax);
    }

    // TODO: 2023/2/17 忘了
    // 归并排序
    public void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    public void mergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int m = l + ((r - l) >> 1);
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    public void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int head = l;
        int tail = m + 1;
        int index = 0;
        // 数组没越界时 把左右两个边最小的值依次放入help数组中
        while (head <= m && tail <= r) {
            help[index++] = arr[head] < arr[tail] ? arr[head++] : arr[tail++];
        }
        // 如果有一边越界了 说明其中一边的数都取完了 那就把另一边的数直接全复制到help中
        while (head <= m) {
            help[index++] = arr[head++];
        }
        while (tail <= r) {
            help[index++] = arr[tail++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }

    // TODO: 2023/2/17 忘了
    // 最小和问题

    /**
     * 在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组 的小和。求一个数组 的小和。
     * 例子:[1,3,4,2,5] 1左边比1小的数，没有; 3左边比3小的数，1; 4左 边比4小的数，1、3; 2左边比2小的数，1; 5左边比5小的数，1、3、4、 2; 所以小和为1+1+3+1+1+3+4+2=16
     */
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return smallSum(arr, 0, arr.length - 1);
    }

    public static int smallSum(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return smallSum(arr, l, m) + smallSum(arr, m + 1, r) + sum(arr, l, m, r);
    }

    private static int sum(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int res = 0;
        int p0 = l;
        int p1 = m + 1;
        int index = 0;
        while (p0 <= m && p1 <= r) {
            res += arr[p0] < arr[p1] ? (r - p1 + 1) * arr[p0] : 0;
            help[index++] = arr[p0] < arr[p1] ? arr[p0++] : arr[p1++];
        }
        while (p0 <= m) {
            help[index++] = arr[p0++];
        }
        while (p1 <= r) {
            help[index++] = arr[p1++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }

    // 逆序对问题 在一个数组中，左边的数如果比右边的数大，则这两个数构成一个逆序对，请打印所有逆序对
    public int reverseOrderPairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return reverseOrderPairs(arr, 0, arr.length - 1);
    }

    public int reverseOrderPairs(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return reverseOrderPairs(arr, l, m) + reverseOrderPairs(arr, m + 1, r) + mergeReverseOrder(arr, l, m, r);
    }

    public int mergeReverseOrder(int[] arr, int l, int m, int r) {
        int index = 0;
        int p1 = l;
        int p2 = m + 1;
        int res = 0;
        int[] help = new int[r - l + 1];
        while (p1 <= m && p2 <= r) {
            if (arr[p1] < arr[p2]) {
                help[index++] = arr[p1++];
            } else {
                if (arr[p1] != arr[p2]) {
                    System.out.println("逆序对：" + arr[p1] + " 和 " + arr[p2]);
                    res++;
                }
                help[index++] = arr[p2++];
            }
        }

        while (p1 <= m) {
            help[index++] = arr[p1++];
        }

        while (p2 <= r) {
            help[index++] = arr[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }

    /**
     * 荷兰国旗问题 问题一
     * 给定一个数组arr，和一个数num，请把小于等于num的数放在数 组的左边，大于num的 数放在数组的右边。要求额外空间复杂度O(1)，时间复杂度O(N)
     * 不需要有序
     */
    public void netherlandsFlag(int[] arr, int num) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int p1 = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= num) {
                int temp = arr[i];
                arr[i] = arr[p1 + 1];
                arr[p1 + 1] = temp;
                p1++;
            }
        }
    }

    // TODO: 2023/2/20 想复杂了

    /**
     * 问题二(荷兰国旗问题)
     * 给定一个数组arr，和一个数num，请把小于num的数放在数组的 左边，等于num的数放 在数组的中间，大于num的数放在数组的 右边。要求额外空间复杂度O(1)，时间复杂度 O(N)
     * 不需要有序
     */
    public void netherlandsFlag_1(int[] arr, int num) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int head = -1; // 小于区域指针
        int tail = arr.length; // 大于区域指针
        int index = 0;
        while (index != tail) {
            // 当前数和目标数相等 则下标跳下一个
            if (arr[index] == num) {
                index++;
            } else if (arr[index] < num) {
                // 当前数小于目标数 当前的数和head的一下个数做交换 head跳下一个 index跳下一个
                int temp = arr[index];
                arr[index] = arr[head + 1];
                arr[head + 1] = temp;
                index++;
                head++;
            } else {
                // 当前数大于目标数 当前数和tail的前一个做交换 tail跳前一个 index不变(因为从tail交换过来的数不知道小于、大于、还是等于目标数 所以需要再再验证一轮)
                int temp = arr[index];
                arr[index] = arr[tail - 1];
                arr[tail - 1] = temp;
                tail--;
            }
        }
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    // TODO: 2023/2/20 忘了
    // 快排
    public void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            int random = (int) (l + Math.random() * (r - l + 1));
            int temp = arr[r];
            arr[r] = arr[random];
            arr[random] = temp;
            int[] partition = partition(arr, l, r);
            quickSort(arr, l, partition[0] - 1);
            quickSort(arr, partition[1] + 1, r);
        }
    }

    public int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int tail = r;
        int index = l;
        while (index != tail) {
            // 当前数和目标数相等 则下标跳下一个
            if (arr[index] == arr[r]) {
                index++;
            } else if (arr[index] < arr[r]) {
                // 当前数小于目标数 当前的数和head的一下个数做交换 head跳下一个 index跳下一个
                int temp = arr[index];
                arr[index] = arr[less + 1];
                arr[less + 1] = temp;
                index++;
                less++;
            } else {
                // 当前数大于目标数 当前数和tail的前一个做交换 tail跳前一个 index不变(因为从tail交换过来的数不知道小于、大于、还是等于目标数 所以需要再再验证一轮)
                int temp = arr[index];
                arr[index] = arr[tail - 1];
                arr[tail - 1] = temp;
                tail--;
            }
        }
        int temp = arr[r];
        arr[r] = arr[tail];
        arr[tail] = temp;
        return new int[]{less + 1, tail};
    }

    // TODO: 2023/2/20 忘了
    // 堆排序
    public void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int heapSize = arr.length;
        // 先变成大根堆 时间复杂度是O(NlogN)
        for (int i = 1; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        // 用这个方法变成大根堆 比上边这个方法快一点点 时间复杂度是O(N)级别
//        for (int i = arr.length - 1; i >= 0; i--) {
//            heapify(arr, i, arr.length);
//        }

        // 堆顶的数和最后一个数字做交换 并且heapSize-- 然后做heapify 直到heapSize为0
        while (heapSize != 0) {
            swap(arr, 0, heapSize - 1);
            heapSize--;
            heapify(arr, 0, heapSize);
        }
    }

    // 跟父节点pk 大于父节点就交换 周而复始 直到小于等于父节点或者到根节点了 停止
    public void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // 跟左右子孩子最大的那个pk 小于就跟子孩子交换 周而复始 直到左右子孩子都比自己小 或者 没有孩子了 停止
    public void heapify(int[] arr, int index, int heapSize) {
        // 左孩子的下标
        int left = index * 2 + 1;
        // 右孩子下标肯定比左孩子大 所以如果左孩子都越界了 那一定没有孩子
        while (left < heapSize) {
            // 进这里表示有孩子 所以再和孩子进行pk
            // 先判断是否有右孩子 并且右孩子是否大于左孩子 如果是 则返回右孩子下标 否则返回左孩子下标
            int largestIndex = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 再跟自己比 看看自己大还是孩子大
            largestIndex = arr[index] > arr[largestIndex] ? index : largestIndex;
            // 如果发现自己比孩子大 则break 不需要再往下找了
            if (largestIndex == index) {
                break;
            }
            // 孩子大 和孩子交换
            swap(arr, index, largestIndex);
            index = largestIndex;
            left = index * 2 + 1;
        }
    }

    // TODO: 2023/2/21 不会
    // 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离可以不超过k，并且k相对于数组来说比较小。请选择一个合适的 排序算法针对这个数据进行排序。
    public void sortedArrDistanceLessK(int[] arr, int k) {
        // 假设 k = 6 那么在下标0 - 6的范围必存在数组的最小值 所以先遍历 0 - 6 的数存入小根堆 弹出堆顶的数放在数组下标0的位置
        // 然后接着把下一个数入小根堆 弹出堆顶放在1 以此类推 当所有数都放进堆里了 就依次弹出堆里的数据 依次放在数组中就排好序了
        PriorityQueue<Integer> heap = new PriorityQueue<>(k + 1);
        for (int i = 0; i < k + 1; i++) {
            heap.offer(arr[i]);
        }
        int index = k + 1;
        int i = 0;
        while (!heap.isEmpty()) {
            arr[i] = heap.poll();
            if (index < arr.length) {
                heap.offer(arr[index]);
                index++;
            }
            i++;
        }
        for (int i1 : arr) {
            System.out.print(i1 + " ");
        }
        System.out.println();
    }

    public static class DoubleNode {
        int val;
        DoubleNode pre;
        DoubleNode next;

        public DoubleNode(int val) {
            this.val = val;
        }

        public DoubleNode(int val, DoubleNode pre, DoubleNode next) {
            this.val = val;
            this.pre = pre;
            this.next = next;
        }
    }

    /**
     * 反转单向和双向链表
     * 【题目】 分别实现反转单向链表和反转双向链表的函数
     * 【要求】 如果链表长度为N，时间复杂度要求为O(N)，额外空间复杂度要求为 O(1)
     */
    public ListNode reverseSingleLinkedListRecursion(ListNode head) {
        // 单链表翻转 递归
        if (head.next == null) {
            return head;
        }
        ListNode last = reverseSingleLinkedListRecursion(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    public ListNode reverseSingleLinkedListIterate(ListNode head) {
        // 单链表翻转 迭代
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public DoubleNode reverseDoublyLinkedListIterate(DoubleNode head) {
        // 双链表翻转 迭代
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.pre = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 打印两个有序链表的公共部分
     * 【题目】 给定两个有序链表的头指针head1和head2，打印两个链表的公共部分。 【要求】 如果两个链表的长度之和为N，时间复杂度要求为O(N)，额外空间复 杂度要求为O(1)
     */
    public void printCommonPart(Node head1, Node head2) {
        while (head1 != null && head2 != null) {
            if (head1.value < head2.value) {
                head1 = head1.next;
            } else if (head1.value > head2.value) {
                head2 = head2.next;
            } else {
                System.out.print(head1.value + " ");
                head1 = head1.next;
                head2 = head2.next;
            }
        }
        System.out.println();
    }

    public void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 判断一个链表是否为回文结构
     * 【题目】给定一个单链表的头节点head，请判断该链表是否为回文结构。 【例子】1->2->1，返回true; 1->2->2->1，返回true;15->6->15，返回true; 1->2->3，返回false。 【例子】如果链表长度为N，时间复杂度达到O(N)，额外空间复杂度达到O(1)。
     */
    // 空间复杂度O(N)
    public boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // 空间复杂度O(N / 2)
    public boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node cur = head;
        Node right = head.next;
        // 快慢指针找中点 一半入栈 然后再从头遍历 知道栈为空 都相等则是回文 否则不是
        while (cur.next != null && cur.next.next != null) {
            cur = cur.next.next;
            right = right.next;
        }
        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // 空间复杂度O(1)
    public boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 快慢指针找到中点 中点指向空 中点右侧链表进行翻转
        // 形成两个链表指向中点 中点指向null的结构
        // 然后左右两个链表 向着中点位置一步一步走 知道有一个链表的node的next为null停止
        // 如果过程中都相等则是回文 否则不是
        // 最后再把右边的链表翻转回去
        Node cur = head;
        Node right = head;
        while (right.next != null && right.next.next != null) {
            cur = cur.next;
            right = right.next.next;
        }
        right = cur.next; // 这个是为了处理只有2个数的情况 很关键
        cur.next = null;
        Node pre = cur;
        Node last = null;
        while (right != null) {
            last = right.next;
            right.next = pre;
            pre = right;
            right = last;
        }

        cur = head;
        right = pre;

        boolean isPalindrome = true;
        while (pre != null && cur != null) {
            if (pre.value != cur.value) {
                isPalindrome = false;
                break;
            }
            pre = pre.next;
            cur = cur.next;
        }

        cur = right;
        pre = null;
        last = null;
        while (cur != null) {
            last = cur.next;
            cur.next = pre;
            pre = cur;
            cur = last;
        }

        System.out.println();
        System.out.println("打印一下 看看后半段链表有没翻转回来");
        printLinkedList(head);
        return isPalindrome;
    }

    /**
     * 将单向链表按某值划分成左边小、中间相等、右边大的形式
     * 【题目】给定一个单链表的头节点head，节点的值类型是整型，再给定一个整数pivot。实现一个调整链表的函数，将链表调整为左部分都是值小于pivot的节点，中间部分都是值等于pivot的节点，右部分都是值大于pivot的节点。
     * 【进阶】在实现原问题功能的基础上增加如下的要求
     * 【要求】调整后所有小于pivot的节点之间的相对顺序和调整前一样
     * 【要求】调整后所有等于pivot的节点之间的相对顺序和调整前一样
     * 【要求】调整后所有大于pivot的节点之间的相对顺序和调整前一样
     * 【要求】时间复杂度请达到O(N)，额外空间复杂度请达到O(1)。
     */
    public Node listPartition1(Node head, int pivot) {
        // 链表转数组 数组做荷兰国旗问题
        Node cur = head;
        int size = 0;
        while (cur != null) {
            size++;
            cur = cur.next;
        }
        cur = head;
        Node[] nodeArr = new Node[size];
        for (int i = 0; i < size; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot);
        cur = nodeArr[0];
        head = cur;
        for (int i = 1; i < nodeArr.length; i++) {
            cur.next = nodeArr[i];
            cur = cur.next;
        }
        return head;
    }

    private void arrPartition(Node[] arr, int pivot) {
        int l = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].value <= pivot) {
                Node temp = arr[i];
                arr[i] = arr[l + 1];
                arr[l + 1] = temp;
                l++;
            }
        }
    }

    // TODO: 2023/2/22 没想出来 暂时跳过
    public Node listPartition2(Node head, int pivot) {
        Node sh = null; // 小于的头节点
        Node st = null; // 小于的尾节点
        Node eh = null; // 等于的头节点
        Node et = null; // 等于的尾节点
        Node bh = null; // 大于的头节点
        Node bt = null; // 大于的尾节点
        Node next = null;

        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (sh == null && st == null) {
                    sh = head;
                    st = head;
                } else {
                    st.next = head;
                    st = head;
                }
            } else if (head.value == pivot) {
                if (eh == null && et == null) {
                    eh = head;
                    et = head;
                } else {
                    et.next = head;
                    et = head;
                }
            } else {
                if (bh == null && bt == null) {
                    bh = head;
                    bt = head;
                } else {
                    bt.next = head;
                    bt = head;
                }
            }
            head = next;
        }

        // small and equal reconnect
        if (st != null) {
            st.next = eh;
            et = et == null ? st : et;
        }
        // all reconnect
        if (et != null) {
            et.next = bh;
        }
        return sh != null ? sh : eh != null ? eh : bh;
    }

    /**
     * 复制含有随机指针节点的链表
     * 【题目】一种特殊的单链表节点类描述如下
     * class Node {
     *     int value;
     *     Node next;
     *     Node rand;
     *     Node(int val) {
     *        value = val;
     *     }
     * }
     * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节 点，也可能指向null。给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
     * 【要求】时间复杂度O(N)，额外空间复杂度O(1)
     */
    // 空间复杂度O(N)的解法
    public Node copyListWithRand1(Node head) {
        HashMap<Node, Node> hashMap = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            hashMap.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            hashMap.get(cur).next = hashMap.get(cur.next);
            hashMap.get(cur).rand = hashMap.get(cur.rand);
            cur = cur.next;
        }
        return hashMap.get(head);
    }

    public void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    // TODO: 2023/2/23 忘了
    // 空间复杂度O(1)的解法
//    public Node copyListWithRand2(Node head) {
//
//    }

    // TODO: 2023/2/23 不会
    /**
     * 两个单链表相交的一系列问题
     * 【题目】给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
     * 【要求】如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)。
     */
//    public Node getIntersectNode(Node head1, Node head2) {
//
//    }


    @Test
    // 对数器
    public void logarithmic() {
        int size = 20;
        int minRange = 0;
        int maxRange = 100;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * (maxRange - minRange)) + minRange;
        }

//        Node head = null;
//        Node res1 = null;
//        Node res2 = null;
//        printRandLinkedList(head);
//        res1 = copyListWithRand1(head);
//        printRandLinkedList(res1);
////        res2 = copyListWithRand2(head);
//        printRandLinkedList(res2);
//        printRandLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        head.next.next.next = new Node(4);
//        head.next.next.next.next = new Node(5);
//        head.next.next.next.next.next = new Node(6);
//
//        head.rand = head.next.next.next.next.next; // 1 -> 6
//        head.next.rand = head.next.next.next.next.next; // 2 -> 6
//        head.next.next.rand = head.next.next.next.next; // 3 -> 5
//        head.next.next.next.rand = head.next.next; // 4 -> 3
//        head.next.next.next.next.rand = null; // 5 -> null
//        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4
//
//        printRandLinkedList(head);
//        res1 = copyListWithRand1(head);
//        printRandLinkedList(res1);
////        res2 = copyListWithRand2(head);
//        printRandLinkedList(res2);
//        printRandLinkedList(head);
//        System.out.println("=========================");

//        Node head1 = new Node(7);
//        head1.next = new Node(9);
//        head1.next.next = new Node(1);
//        head1.next.next.next = new Node(8);
//        head1.next.next.next.next = new Node(5);
//        head1.next.next.next.next.next = new Node(2);
//        head1.next.next.next.next.next.next = new Node(5);
//        printLinkedList(head1);
////         head1 = listPartition1(head1, 4);
//        head1 = listPartition2(head1, 5);
//        printLinkedList(head1);

//        //region 验证是否是回文
//        Node head = null;
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        head.next.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(2);
//        head.next.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        head.next.next.next = new Node(2);
//        head.next.next.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//        //endregion

//        Node node1 = new Node(2);
//        node1.next = new Node(3);
//        node1.next.next = new Node(5);
//        node1.next.next.next = new Node(6);
//
//        Node node2 = new Node(1);
//        node2.next = new Node(2);
//        node2.next.next = new Node(5);
//        node2.next.next.next = new Node(7);
//        node2.next.next.next.next = new Node(8);
//
//        printLinkedList(node1);
//        printLinkedList(node2);
//        printCommonPart(node1, node2);

//        DoubleNode head2 = new DoubleNode(1);
//        head2.next = new DoubleNode(2);
//        head2.next.pre = head2;
//        head2.next.next = new DoubleNode(3);
//        head2.next.next.pre = head2.next;
//        head2.next.next.next = new DoubleNode(4);
//        head2.next.next.next.pre = head2.next.next;
//
//        printDoubleLinkedList(head2);
//        printDoubleLinkedList(reverseDoublyLinkedListIterate(head2));

//        ListNode head1 = new ListNode(1);
//        head1.next = new ListNode(2);
//        head1.next.next = new ListNode(3);

//        head1 = reverseSingleLinkedListIterate(head1);
//        while (head1 != null) {
//            System.out.println(head1.val + " ");
//            head1 = head1.next;
//        }

//        head1 = reverseSingleLinkedListRecursion(head1);
//        while (head1 != null) {
//            System.out.println(head1.val + " ");
//            head1 = head1.next;
//        }

//        sortedArrDistanceLessK(new int[]{4, 2, 1, 3, 5, 9, 6, 7}, 6);
//        heapSort(arr);
//        quickSort(arr);
//        System.out.println("num: " + arr[arr.length >> 1]);
//        netherlandsFlag_1(arr, arr[arr.length >> 1]);
//        System.out.println("num: " + arr[arr.length >> 1]);
//        netherlandsFlag(arr, arr[arr.length >> 1]);
//        System.out.println("总共有：" + reverseOrderPairs(arr) + "对逆序对");
//        System.out.println(smallSum(new int[]{1, 3, 4, 2, 5}));
//        mergeSort(arr);
//        System.out.println(getMax(arr));
//        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
//        printOddTimesNum2(arr2);
//        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
//        printOddTimesNum1(arr1);
//        System.out.println(dichotomy3(arr) + "");
//        insertSorting(arr);
//        System.out.println(dichotomy2(arr, 30) + "");
//        insertSorting(arr);
//        System.out.println(dichotomy1(arr, arr[arr.length - 1]) ? "存在" : "不存在");
//        insertSorting(arr);
//        bubbleSort(arr);
//        selectionSort(arr);

//        for (int i : arr) {
//            System.out.print(i + "  ");
//        }
//        System.out.println();
    }

    public void printDoubleLinkedList(DoubleNode head) {
        System.out.print("Double Linked List: ");
        DoubleNode end = null;
        while (head != null) {
            System.out.print(head.val + " ");
            end = head;
            head = head.next;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.val + " ");
            end = end.pre;
        }
        System.out.println();
    }
}