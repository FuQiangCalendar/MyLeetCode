package org.example.structure.started;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Solution {
    public static void main(String[] args) {
        /*char[][] charArr = new char[][]{
                {'.','2','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','5','.','1'},{'.','.','.','.','.','.','8','1','3'},{'4','.','9','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.','.'},{'.','.','2','.','.','.','.','.','.'},{'7','.','6','.','.','.','.','.','.'},{'9','.','.','.','.','4','.','.','.'},{'.','.','.','.','.','.','.','.','.'}
        };
        System.out.println(canConstruct("aa", "aba"));*/

        /*List<Integer> basList = Lists.newArrayList(1,2,3,4,5,6,7);
        Iterator<Integer> iterator = basList.iterator();
        int index = 1;
        Node root = new Node();
        for (int i = 0; i < basList.size(); i++) {
            root = new Node(basList.get(i));
            extracted(basList, index, root);
        }*/

        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5), null), new TreeNode(3, new TreeNode(6), new TreeNode(7), null), null);
        /*connect(root);
        System.out.println(root);*/
        /*System.out.println(serialize(root));
        TreeNode deserialize = deserialize("[4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2]");
        System.out.println(deserialize);
        System.out.println(serialize(deserialize));*/
        /*int[] nums = {0,1,2,2,2,2,2,3,4,4,4};
        System.out.println(removeDuplicates(nums));
        Set<Integer> numsList = new HashSet(Arrays.asList(nums));
        List<int[]> intList = new ArrayList<>();
         System.out.println(findMedianSortedArrays(new int[]{1,2}, new int[]{3,4}));
        ListNode listNode = new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(1, null))));
        listNode = sortList(listNode);
        listNode = sortList(listNode);
        System.out.println(listNode);*/
        boolean b = possibleBipartition(3, new int[][]{{1, 2}, {1, 3}, {2, 3}});
        System.out.println(b);
    }

    public static boolean possibleBipartition(int n, int[][] dislikes) {
        List<int[]> intList = new ArrayList<>();
        loop1:for (int i = 0; i < dislikes.length/2; i++) {
            int[] dislike = dislikes[i];
            loop2:for (int k = 0; k < dislike.length; k++) {
                Set<Integer> dislikeList = new HashSet();
                Arrays.stream(dislike).forEach(e -> dislikeList.add(e));
                int dis = dislike[k];
                loop3:for (int j = i+1; j < dislikes.length; j++) {
                    int[] comp = dislikes[j];
                    if (Arrays.stream(comp).anyMatch(e -> e == dis)) {
                        Arrays.stream(comp).forEach(e -> dislikeList.add(e));
                        continue loop3;
                    }
                    loop4:for (int s = 0; s < comp.length; s++) {
                        if(!dislikeList.contains(comp[s])) {
                            intList.add(new int[]{dis, comp[s]});
                            if(intList.size() >= (n/2 + n%2)) {
                                break loop1;
                            }
                        }
                    }
                }
            }
        }
        return intList.size() == (n/2 + n%2);
    }

    public static ListNode sortList(ListNode head) {
        return swap(head);
    }

    public static ListNode swap (ListNode node) {
        if (node != null) {
            if (node.next != null) {
                if (node.val > node.next.val) {
                    int temp = node.val;
                    node.val = node.next.val;
                    node.next.val = temp;
                }
                node.next = swap(node.next);

                if (node.val > node.next.val) {
                    int temp = node.val;
                    node.val = node.next.val;
                    node.next.val = temp;
                }
            }
        }
        return node;
    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> bls = new ArrayList();
        Map<Integer, Integer> candiesMap = new HashMap();
        int maxCandy = Integer.MIN_VALUE;
        for (int i = 0; i < candies.length; i++) {
            candiesMap.put(i, candies[i]);
            if (maxCandy < candies[i]) {
                maxCandy = candies[i];
            }
        }

        int finalMaxCandy = maxCandy;
        candiesMap.entrySet().stream().forEach(entry -> {
            Integer value = entry.getValue();
            if ((extraCandies + value) >= finalMaxCandy) {
                bls.add(true);
            }else{
                bls.add(false);
            }
        });

        return bls;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] arr = new int[nums1.length + nums2.length];
        int i=0, j=0;
        loop1 : for (; i < nums1.length;) {
            if (j >= nums2.length) {
                arr[i+j] = nums1[i];
                i++;
                continue loop1;
            }
            loop2 : for (; j < nums2.length;) {
                if (nums1[i] <= nums2[j] ){
                    arr[i+j] = nums1[i];
                    i++;
                    continue loop1;
                }else{
                    arr[i+j] = nums2[j];
                    j++;
                    continue loop2;
                }
            }
        }

        if (j < nums2.length) {
            for (; j < nums2.length; j++) {
                arr[i+j] = nums2[j];
            }
        }

        int midIndex= arr.length/2;
        double minVal = 0.0D;
        if (arr.length % 2 != 0) {
            minVal = arr[midIndex];
        }else {
            minVal = (arr[midIndex - 1] + arr[midIndex]) / 2.0D;
        }
        return minVal;
    }

    public static int removeDuplicates(int[] nums) {
        int changLen = 0;
        if (null == nums) {
            return 0;
        }else if(nums.length <= 2) {
            return nums.length;
        }
        int compInt = nums[0];
        int compareIndex = 0;
        int matchCount = 0;
        for (int i = 1 ; i < nums.length; i++) {
            if (nums[i] == compInt) {
                compInt = nums[i];
                if (matchCount < 1) {
                    compareIndex++;
                }
                if (i == nums.length -1) {
                    nums[compareIndex] = nums[i];
                }
                matchCount++;
            }else {
                if (nums[i-1] != nums[i]) {
                    nums[compareIndex] = nums[i-1];
                }
                compInt = nums[i];
                nums[++compareIndex] = compInt;
                matchCount = 0;
            }
        }
        changLen = compareIndex + 1;
        return changLen;
    }

    public static TreeNode deserialize(String data) {
        String eStr = data.substring(1, data.length() -1);
        String[] split = eStr.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        List<TreeNode> roots = new ArrayList();
        roots.add(root);
        List<TreeNode> temp = new ArrayList();
        int index = 1;
        int index2 = 0;
        int size = roots.size();
        while (index < split.length && index2 < size) {
            TreeNode currentNode = roots.get(index2++);
            if (null != currentNode) {
                currentNode.left = "null".equals(split[index]) ? null : new TreeNode(Integer.parseInt(split[index]));
                if (++index >= split.length) {
                    break;
                }
                currentNode.right = "null".equals(split[index]) ? null : new TreeNode(Integer.parseInt(split[index]));
                ++index;

                temp.add(currentNode.left);
                temp.add(currentNode.right);
            }

            if (index2 >= size) {
                roots = temp;
                temp = new ArrayList();
                index2 = 0;
                size = roots.size();
            }
        }
        return root;
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        List<List<Integer>> lists = new ArrayList();
        List<TreeNode> srcs = new ArrayList();
        srcs.add(root);
        while (null != root && srcs.size() > 0) {
            List<Integer> list = new ArrayList();
            List<TreeNode> next = new ArrayList();
            for (TreeNode src : srcs) {
                if (null == src) {
                    list.add(null);
                    continue;
                }
                list.add(src.val);
                // if (src.left != null) {
                next.add(src.left);
                // }
                // if (src.right != null) {
                next.add(src.right);
                // }
            }
            boolean hasNotNull = list.stream().anyMatch(e -> e != null);
            if (hasNotNull) {
                lists.add(list);
            }
            srcs = next;
        }

        StringBuilder str =  new StringBuilder();
        str.append("[");
        for (List<Integer> list : lists) {
            for (Integer l : list) {
                str.append(String.valueOf(l));
                str.append(",");
            }
        }
        str = str.delete(str.length() - 1, str.length());
        str.append("]");
        return str.toString();
    }


    private static void extracted(List<Integer> basList, int index, TreeNode root) {
        while (index < basList.size()){
            root.left = new TreeNode(basList.get(index++));
            root.right = new TreeNode(basList.get(index++));
        }
    }

    public static TreeNode connect(TreeNode root) {
        if (root == null) {
            return root;
        }
        List<TreeNode> roots = new ArrayList();
        roots.add(root);
        List<TreeNode> temp = new ArrayList();
        int index = 0;
        int size = roots.size();
        while (index < size) {
            TreeNode currentroot = roots.get(index);

            if (null != currentroot.left) {
                temp.add(currentroot.left);
            }

            if (null != currentroot.right) {
                temp.add(currentroot.right);
            }

            index++;
            if (index < size) {
                currentroot.next = roots.get(index);
            }else {
                roots = temp;
                temp = new ArrayList();
                index = 0;
                size = roots.size();
            }
        }
        return root;
    }

    public static boolean canConstruct(String ransomNote, String magazine) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        char[] chars = magazine.toCharArray();
        for (char cr : chars) {
            map.merge("" + cr, 1, (oldvalue, newvalue) -> ++oldvalue);
        }

        char[] chars1 = ransomNote.toCharArray();
        for (char cr : chars1) {
            if (!map.containsKey("" + cr)) {
                return false;
            }

            if (map.getOrDefault("" + cr, 0) == 0) {
                return false;
            }else{
                map.merge("" + cr, 1, (oldvalue, newvalue) -> --oldvalue);
            }
        }

        return true;
    }

    public static int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (s.indexOf(chars[i]) == i && s.indexOf(chars[i], i+1) == -1) {
                return i;
            }
        }
        return -1;
    }

    public static int arrayPairSum(int[] nums) {
        if (null == nums) {
            return 0;
        }else if (nums.length == 2){
            return nums[0] < nums[1] ? nums[0] : nums[1];
        }

        int i=0, j = nums.length - 1;
        while (i < j) {
            // 首尾指针交换值
            while(nums[i] > nums[j]) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
            // 与前一个交换
            for (int k = i; k > 0; k--) {
                if (nums[k] < nums[k-1]) {
                    int temp = nums[k];
                    nums[k] = nums[k-1];
                    nums[k-1] = temp;
                }
            }

            // 与后一个交换
            for (int n = j; n < nums.length - 1; n++){
                if (nums[n] > nums[n+1]) {
                    int temp = nums[n];
                    nums[n] = nums[n+1];
                    nums[n+1] = temp;
                }
            }
            i++;
            j--;
        }

        while (nums[j] > nums[j+1]) {
            int leftCurr = i;
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            while (leftCurr < nums.length - 1 && nums[leftCurr] > nums[leftCurr+1]) {
                temp = nums[leftCurr];
                nums[leftCurr] = nums[leftCurr+1];
                nums[leftCurr+1] = temp;
                leftCurr++;
            }

            while (leftCurr > 0) {
                if(nums[leftCurr] < nums[leftCurr-1]) {
                    temp = nums[leftCurr];
                    nums[leftCurr] = nums[leftCurr-1];
                    nums[leftCurr-1] = temp;
                }
                leftCurr--;
            }
        }

        int result = 0;
        for (int k = 0; k < nums.length; k += 2) {
            result += nums[k];
        }

        return result;
    }

    public static boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            char[] xArr = board[i];
            int yindex = 0;
            while (yindex < xArr.length) {
                if ('.' == xArr[yindex]) {
                    yindex++;
                    continue;
                }
                // 行比较
                for (int k = i+1; k < board.length; k++) {
                    if(board[k][yindex] == xArr[yindex]){
                        return false;
                    }
                }

                //3X3内重复比较
                for (int n = i + 1; n < (i/3 + 1)*3; n++) {
                    if (yindex%3 == 0) {
                        if (board[n][yindex+1] == xArr[yindex] || board[n][yindex+2]==xArr[yindex]) {
                            return false;
                        }
                    }else if (yindex%3 == 1){
                        if (board[n][yindex-1]==xArr[yindex] || board[n][yindex+1]==xArr[yindex]) {
                            return false;
                        }
                    }else if (yindex%3 == 2) {
                        if (board[n][yindex-1]==xArr[yindex] || board[n][yindex-2]==xArr[yindex]) {
                            return false;
                        }
                    }
                }

                for (int j = yindex + 1; j < xArr.length; j++) {
                    //列比较
                    if (xArr[yindex]== xArr[j]) {
                        return false;
                    }
                }
                yindex++;
            }


        }
        return true;
    }

    public static List<List<Integer>> generate2(int numRows) {
        List<List<Integer>> results = new ArrayList<>();
        if (numRows <= 0) {
            return results;
        }

        List<Integer> before = new ArrayList<>();
        List<Integer> result;
        for (int i = 0; i < numRows; i++) {
            result = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    result.add(1);
                }else {
                    result.add(before.get(j-1) + before.get(j));
                }
            }
            before = result;
            results.add(result);
        }
        return results;
    }

    /**
     * @Description 杨辉三角暴力解法
     * @Param numRows
     * @Return java.util.List<java.util.List<java.lang.Integer>>
     * @Author qfu1
     * @Date 2023-01-31 14:13
     **/
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> result = new ArrayList<>();
            if (i == 0){
                result.add(1);
                results.add(result);
                continue;
            }
            List<Integer> before = results.get(i-1);
            for (int j = 0; j <= i; j++) {
                int leftIndex = j-1;
                int rightIndex = leftIndex + 1;
                int leftVal = 0;
                int rightVal = 0;
                if (leftIndex >= 0) {
                    leftVal = before.get(leftIndex);
                }

                if (rightIndex < before.size()) {
                    rightVal = before.get(rightIndex);
                }

                result.add(leftVal + rightVal);
            }

            results.add(result);
        }

        return results;
    }

    public static int[][] matrixReshape(int[][] mat, int r, int c) {
        if (r * c != mat.length * mat[0].length) {
            c = mat.length * mat[0].length/r;
        }
        int[][] target = new int[r][c];
        int tagX = 0;
        int tagY = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                target[tagX][tagY] = mat[i][j];
                tagY++;
                if (tagY >= c) {
                    tagX++;
                    tagY = 0;
                }
            }
        }
        return target;
    }

    public static String reverseWords(String s) {
        String str = "";
        String[] split = s.trim().split("[ ]+");
        for (int i = split.length - 1; i >= 0; i--) {
            str += split[i];
            if (i > 0) {
                str += " ";
            }
        }
        return str;
    }

    public static int maxProfit(int[] prices) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }

    public static String longestPalindrome(String s) {
        if (null == s || s.length() == 0) {
            return "";
        }

        char[] chars = s.toCharArray();
        int maxLen = 0;
        int startStr = 0;
        int endStr = 0;
        for (int i = 0; i < chars.length; i++) {
            int start = i;
            int end = i;
            while(s.indexOf(chars[i], start + 1) != -1) {
                end = s.indexOf(chars[i], start + 1);
                if ((end - start +1) > maxLen) {
                    maxLen = end - start + 1;
                    startStr = start;
                    endStr = end;
                }
                start = end;
            }
        }

        maxLen = endStr - startStr + 1;
        return maxLen>0 ? s.substring(startStr, endStr+1) : "";
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }
        String first = strs[0];
        int firstLen = first.length();
        if (firstLen == 0) {
            return "";
        }


        for (int i = 1; i < strs.length; i++) {
            while (first.length() > 0){
                if (strs[i].indexOf(first) == 0) {
                    break;
                }
                first = first.substring(0, first.length() - 1);

            }
        }
        return  first;
    }

    public static int[] findDiagonalOrder(int[][] mat) {
        int xLen = mat.length;
        int yLen = mat[0].length;
        int[] result = new int[xLen*yLen];
        int x = 0;
        int y = 0;
        boolean upFlag = true;
        int index = 0;
        do{
            result[index] = mat[x][y];
            index++;
            if (upFlag) {
                y++;
                if (y >= yLen) {
                    x++;
                    y--;
                    upFlag = upFlag ? false : true;
                }else if (y > 0){
                    if (x > 0){
                        x--;
                    }else {
                        upFlag = upFlag ? false : true;
                    }

                }
            }else{
                x++;
                if (x >= xLen) {
                    x--;
                    y++;
                    upFlag = upFlag ? false : true;
                }else if (x > 0) {
                    if (y > 0){
                        y--;
                    }else {
                        upFlag = upFlag ? false : true;
                    }
                }
            }
        }while(index < xLen*yLen);
        return result;
    }

    public static void setZeroes(int[][] matrix) {
        Map<String, int[]> map = new HashMap();
        for (int i = 0, xLen = matrix.length; i < xLen; i++) {
            int[] mat = matrix[i];
            for (int j = 0, yLen = mat.length; j < yLen; j++) {
                if (mat[j] == 0) {
                    String key = i + "-" +j;
                    if (!map.containsKey(key)) {
                        map.put(key, new int[]{i,j});
                    }
                }
            }
        }

        Set<Map.Entry<String, int[]>> entries = map.entrySet();

        for (Map.Entry<String, int[]> entry : entries) {
            int[] value = entry.getValue();
            int x = value[1];
            int y = value[0];
            for (int i = 0, xLen = matrix.length; i < xLen; i++) {
                int[] ma = matrix[i];
                int yyLen = ma.length;
                if (x == i) {
                    for (int j = 0; j < yyLen; j++) {
                        matrix[i][j] = 0;
                    }
                }else{
                    for (int j = 0; j < yyLen; j++) {
                        if (j == y) {
                            matrix[i][j] = 0;
                        }
                    }
                }
            }
        }
    }

    public static void rotate(int[][] matrix) {
        for (int i=0, matrixLen = matrix.length, maxI = (matrixLen/2); i < maxI; i++) {
            int[] matrix1 = matrix[i];
            for (int j = 0, matrix1Len = matrix1.length, maxY = (matrix1Len/2 + (matrix1Len%2 > 0 ? 1 : 0)); j < maxY; j++) {
                int tagX = i;
                int tagY = j;
                int temp = matrix[i][j];
                int changCount = 1;
                do{
                    if (changCount % 4 == 1 ) {
                        tagX = j;
                        tagY = matrixLen - i - 1;
                    }else if (changCount % 4 == 2 ) {
                        int tempY = tagY;
                        tagY = matrix1Len - tagX - 1;
                        tagX = tempY;
                    }else if (changCount % 4 == 3 ) {
                        int tempY = tagY;
                        tagY = matrix1Len - tagX - 1;
                        tagX = tempY;
                    }else if (changCount % 4 == 0 ) {
                        tagY = j;
                        tagX = i;
                    }
                    int targetTemp = matrix[tagX][tagY];
                    matrix[tagX][tagY] = temp;
                    temp = targetTemp;
                    changCount++;
                }while(!(tagX == i && tagY == j));

            }
        }
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int sumLen = m+n;
        loop1: for (int i = 0; i< n; i++) {
            int fixNum = nums2[i];
            loop2: for (int maxIndex = 0; maxIndex < m; maxIndex++) {
                if(nums1[maxIndex] > fixNum){
                    if(maxIndex < m - 1){
                        int temp = nums1[maxIndex+1];
                        nums1[maxIndex+1] = nums1[maxIndex];
                        nums1[maxIndex] = fixNum;
                        fixNum = temp;
                        if (sumLen > m) {
                            m++;
                        }
                    }else{
                        nums1[maxIndex] = fixNum;
                    }

                    if (maxIndex+1 == m) {
                        continue loop1;
                    }
                    // if(m < (m+n)) {
                    //     m++;
                    // }
                }else if(maxIndex+1 < m && nums1[maxIndex] > nums1[maxIndex+1]){
                    int temp = nums1[maxIndex];
                    nums1[maxIndex] = nums1[maxIndex+1];
                    nums1[maxIndex+1] = temp;
                }
            }
            nums1[m-1] = nums2[i];
            m++;
        }
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i=0; i< nums.length; i++) {
            int mathNum = target - nums[i];
            for (int j = nums.length -1; j > i; j--){
                if (nums[j] == mathNum){
                    return new int[]{i, j};
                }
            }

        }
        return null;
    }

    public static int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        list.add(intervals[0]);
        Iterator<int[]> iterator = list.iterator();
        int index = 1;
        while (index <= intervals.length -1){
            int[] emp = intervals[index];
            while (iterator.hasNext()) {
                int[] next = iterator.next();
                if ((emp[0] <= next[1] && emp[0] >= next[0])
                        || (emp[1] >= next[0] && emp[1] <= next[1])
                        || (emp[0] <= next[0] && emp[1] >= next[1])
                        || (next[0] <= emp[0] && next[1] >= emp[1])) {
                    emp = new int[]{emp[0] < next[0] ? emp[0] : next[0], emp[1] > next[1] ? emp[1] : next[1]};
                    iterator.remove();
                }
            }
            list.add(emp);
            iterator = list.iterator();
            index++;
        }

        return  list.toArray(new int[list.size()][2]);
    }

    public static int searchInsert(int[] nums, int target) {
        if(target < nums[0]) {
            //nums[nums.length] = target;
            return 0;
        }

        if(target > nums[nums.length-1]) {
            //nums[nums.length] = target;
            int[] ints = Arrays.copyOf(nums, nums.length + 1);
            ints[ints.length-1] = target;
            return nums.length;
        }
        int targetIndex = -1;
        boolean createFlag=false;
        for (int i = 0; i < nums.length; i++) {
            int[] inserts = new int[nums.length+1];
            if(i == 0 && nums[i] < target){
                int[] ints = new int[nums.length+1];
                ints[0] = target;
                 for (int j=0; j < nums.length-1; j++) {
                     ints[j+1] = nums[j];
                 }
                 nums=ints;
                targetIndex = 0;
                 break;
            }else if(nums[i] == target) {
                return i;
            }else if(nums[i] > target) {
                targetIndex = i;
                 for (int j=i; j < nums.length-1; j++) {
                     int tmp = nums[j];
                     inserts[j] = target;
                     target = inserts[j+1];
                     inserts[j+1] = tmp;
                 }
                break;

            }else{
                inserts[i] = nums[i];
            }
        }
        return targetIndex;
    }

    public static int maxSubArray(int[] nums) {
//        Queue<Integer> queue = new LinkedList<>();
        int res = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum > 0)
                sum += num;
            else
                sum = num;
            res = Math.max(res, sum);
        }
        return res;
    }

    public boolean containsDuplicate(int[] nums) {
        if(nums.length <= 1) {
            return false;
        }
        selectSort(nums);
        int current = 0;
        for (int i=0; i < nums.length; i++){
            if (i==0) {
                current = nums[i];
            }else{
                if(current == nums[i]){
                    return true;
                }else{
                    current = nums[i];
                }
            }
        }
        return false;
    }

    /**
     * @title selectSort
     * @description 选择排序
     * 0~N-1中找到最小值，和0 位进行交换
     * 1~N-1 中找到最小值，和1 位进行交换
     * ...
     * N-2~N-1 中找到最小值，和N-2 位进行交换
     * 时间复杂分析：
     * 分析最小动作，即 看+比， 一次换
     * 常数级别操作
     * N*(看+比) + 一次交换
     * --------------------
     * （N-1)*(2) + 1
     * ...
     * (N-2)*2 +1
     * 从上可看，是一个等差数列，可化简为 2*(N+ N-1 + ... +1) +N
     * 即可写为 aN^2 + bN +c (a,b,c都是常数)
     * 根据时间复杂度，不看低阶项和高阶项系数，可得O(N^2)
     *
     * 确定算法流程的总操作数量与样本数量之间的表达式关系
     * 1、想象该算法流程所处理的数据状况，要按照最差情况来。
     * 2、把整个流程彻底拆分为一个个基本动作，保证每个动作都是常数时间的操作。
     * 3、如果数据为N，看看基本动作的数量和N是什么关系。
     *
     * 如何确定算法流程的时间复杂度？
     * 当完成了表达式的建立，只要把最高阶项留下即可。低阶项都去掉，
     * 高阶项的系数也去掉。记为：O(忽略掉系数的高阶项)
     *
     *
     * @author FuQiangCalendar
     * @param: arr
     * @updateTime 2021/9/30 9:46
     * @throws
     */
    public static void selectSort (int[] arr) {
        if (null == arr || arr.length < 2) {
            return;
        }

        for (int i=0, n = arr.length; i < n; i++) {
            int minValueIndex = i;
            for (int j = i + 1; j < n; j++) {
                minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
            }
            swap(arr, i, minValueIndex);
        }

    }

    /**
     * @title swap
     * @description 数组中两个位置的值进行交换
     * @author FuQiangCalendar
     * @param: arr
     * @param: i
     * @param: j
     * @updateTime 2021/9/30 9:53
     * @throws
     */
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }
}

class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode next;

    public TreeNode() {}

    public TreeNode(int _val) {
        val = _val;
    }

    public TreeNode(int _val, TreeNode _left, TreeNode _right, TreeNode _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}