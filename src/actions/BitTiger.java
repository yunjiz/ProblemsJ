package actions;

import actions.util.ListNode;
import actions.util.Node;
import actions.util.RandomNode;
import actions.util.TreeNode;

import java.util.*;

public class BitTiger {

    public int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[]{map.get(nums[i]), i};
            }
            map.put(target - nums[i], i);
        }
        return ans;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int value = carry;
            if (l1 != null) {
                value += l1.val;
            }
            if (l2 != null) {
                value += l2.val;
            }
            carry = value / 10; //BUG: / and %
            value = value % 10;
            ListNode newNode = new ListNode(value);
            prev.next = newNode;
            prev = newNode;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry != 0) {
            prev.next = new ListNode(carry);
        }
        return dummy.next;
    }

    public int lengthOfLongestSubstring(String s) {
        int fast = 0;
        int slow = 0;
        int result = 0;
        HashSet<Character> hset = new HashSet<>();
        while (fast < s.length()) {
            if (hset.contains(s.charAt(fast))) {
                while (s.charAt(slow) != s.charAt(fast)) {
                    hset.remove(s.charAt(slow));
                    slow++;
                }
                hset.remove(s.charAt(slow));
                slow++;
            }
            hset.add(s.charAt(fast));
            fast++;
            result = Math.max(result, fast - slow);
        }
        return result;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m + n % 2 == 0) {
            return (double) (getKth(nums1, nums2, (m + n) / 2 + 1) + getKth(nums1, nums2, (m + n) / 2)) * 0.5;
        } else {
            return (double) getKth(nums1, nums2, (m + n) / 2) * 1.0;
        }
    }

    private int getKth(int[] A, int[] B, int k) {
        int m = A.length;
        int n = B.length;

        if (m > n) {
            return getKth(B, A, k);
        }
        if (m == 0) {
            return B[k - 1];
        }
        if (k == 1) {
            return Math.min(A[0], B[0]);
        }

        int pa = Math.min(k / 2, m);
        int pb = k - pa;
        if (A[pa - 1] <= B[pb - 1]) {
            return getKth(Arrays.copyOfRange(A, pa, m), B, pb);
        } else {
            return getKth(A, Arrays.copyOfRange(B, pb, n), pa);
        }
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAtCenter(s, i, i);
            int len2 = expandAtCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }

        }
        return s.substring(start, end + 1);
    }

    private int expandAtCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length()) {
            if (s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }
        }
        return right - left - 1;
    }

    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int result = 0;
        while (left < right) {
            if (height[left] <= height[right]) {
                result = Math.max(result, height[left] * (right - left));
                left++;
            } else {
                result = Math.max(result, height[right] * (right - left));
                right--;
            }
        }
        return result;
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums.length < 3) {
            return results;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int left = i + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right];
                    if (left != i + 1 && nums[left] == nums[left - 1]) {
                        left++;
                    } else if (right != nums.length - 1 && nums[right] == nums[right + 1]) {
                        right--;
                    } else if (sum < 0) {
                        left++;
                    } else if (sum > 0) {
                        right--;
                    } else {
                        List<Integer> res = new ArrayList<>();
                        res.add(nums[i]);
                        res.add(nums[left]);
                        res.add(nums[right]);
                        results.add(res);
                        left++;
                        right--;
                    }
                }
            }
        }
        return results;
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        if (digits == null || digits.length() == 0) {
            return result;
        }

        HashMap<Character, String[]> phone = new HashMap<>();
        phone.put('2', new String[]{"a", "b", "c"});
        phone.put('3', new String[]{"d", "e", "f"});
        phone.put('4', new String[]{"g", "h", "i"});
        phone.put('5', new String[]{"j", "k", "l"});
        phone.put('6', new String[]{"m", "n", "o"});
        phone.put('7', new String[]{"p", "q", "r", "s"});
        phone.put('8', new String[]{"t", "u", "v"});
        phone.put('9', new String[]{"w", "x", "y", "z"});

        letterCombinationsBacktrack(result, phone, digits, 0, "");
        return result;
    }

    private void letterCombinationsBacktrack(List<String> result, HashMap<Character, String[]> phone, String digits, int pos, String tmp) {
        if (pos == digits.length()) {
            result.add(tmp);
            return;
        }

        for (String s : phone.get(digits.charAt(pos))) {
            tmp += s;
            letterCombinationsBacktrack(result, phone, digits, pos + 1, tmp);
            tmp = tmp.substring(0, tmp.length() - 1);
        }
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else if (stack.empty() || c != stack.peek()) {
                return false;
            } else {
                stack.pop();
            }
        }
        return stack.empty();
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                prev = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                prev = l2;
                l2 = l2.next;
            }
        }
        if (l1 == null) {
            prev.next = l2;
        } else {
            prev.next = l1;
        }
        return dummy.next;
    }

    public List<String> generateParenthesis(int n) {
        List<String> results = new ArrayList<>();
        generateParenthesisBacktrack(results, n, n, n, "");
        return results;
    }

    private void generateParenthesisBacktrack(List<String> results, int left, int right, int n, String temp) {
        if (left == n && right == n) {
            results.add(temp);
            return;
        }

        if (right > left || left > n) {
            return;
        }
        generateParenthesisBacktrack(results, left + 1, right, n, temp + "(");
        generateParenthesisBacktrack(results, left, right + 1, n, temp + ")");
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return mergeKListsDC(lists, 0, lists.length - 1);
    }

    private ListNode mergeKListsDC(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }
        int mid = (left + right) / 2;
        ListNode leftList = mergeKListsDC(lists, left, mid);
        ListNode rightList = mergeKListsDC(lists, mid + 1, right);
        return mergeTwoLists(leftList, rightList);
    }

    public int divide(int dividend, int divisor) {
        return 0;
    }

    private long divideRecursive(long dividend, long divisor) {
        if (dividend < divisor) {
            return 0;
        }
        long sum = divisor;
        long multiplier = 1;
        while (sum <= dividend) {
            sum += sum;
            multiplier += multiplier;
        }
        return multiplier / 2 + divideRecursive(dividend - sum / 2, divisor);
    }

    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        reverse(nums, i + 1, nums.length - 1);
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int k = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                k = i;
            }
        }
        int left = k;
        int right = k + nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int realMid = mid;
            if (mid >= nums.length) {
                realMid = mid - nums.length; //+ 1;
            }
            if (nums[realMid] == target) {
                return realMid;
            } else if (nums[realMid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public String countAndSay(int n) {
        String s = "1";
        while (n > 1) {
            int num = 0;
            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i == 0 || s.charAt(i) == s.charAt(i - 1)) {
                    num++;
                } else {
                    ans.append(num).append(s.charAt(i - 1));
                    num = 1;
                }
            }
            ans.append(num).append(s.charAt(s.length() - 1));
            s = ans.toString();
            n--;
        }
        return s;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumBacktrack(candidates, 0, results, new ArrayList<>(), 0, target);
        return results;
    }

    private void combinationSumBacktrack(int[] candidates, int index, List<List<Integer>> results, List<Integer> temp, int sum, int target) {
        if (sum == target) {
            results.add(new ArrayList<>(temp));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (sum + candidates[i] > target) {
                return;
            }
            temp.add(candidates[i]);
            combinationSumBacktrack(candidates, i, results, temp, sum + candidates[i], target);
            temp.remove(temp.size() - 1);
        }
    }

    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] - 1 != i && nums[i] >= 1 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1;
    }

    private void swap(int[] nums, int left, int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }

    public String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2];

                pos[p1] += sum / 10;
                pos[p2] = (sum) % 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            if (!(sb.length() == 0 && p == 0)) {
                sb.append(p);
            }
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }

    public int maxSubArray(int[] nums) {
        int result = Integer.MIN_VALUE;
        int sum = 0;
        for (int n : nums) {
            sum += n;
            result = Math.max(result, sum);
            if (sum < 0) {
                sum = 0;
            }
        }
        return result;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List ans = new ArrayList();
        if (matrix == null || matrix.length == 0) {
            return ans;
        }
        int R = matrix.length, C = matrix[0].length;
        boolean[][] seen = new boolean[R][C];
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        int r = 0, c = 0, di = 0;
        for (int i = 0; i < R * C; i++) {
            ans.add(matrix[r][c]);
            seen[r][c] = true;
            int cr = r + dr[di];
            int cc = c + dc[di];
            if (0 <= cr && cr < R && 0 <= cc && cc < C && !seen[cr][cc]) {
                r = cr;
                c = cc;
            } else {
                di = (di + 1) % 4;
                r += dr[di];
                c += dc[di];
            }
        }
        return ans;
    }

    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int furthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > furthest) {
                return false;
            }
            furthest = Math.max(furthest, i + nums[i]);
            if (furthest >= nums.length - 1) {
                return true;
            }
        }
        return true;
    }

    public int[][] merge(int[][] intervals) {
        Collections.sort(Arrays.asList(intervals), new IntervalComparator());

        LinkedList<int[]> merged = new LinkedList<>();
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    private class IntervalComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            return a[0] < b[0] ? -1 : a[0] == b[0] ? 0 : 1;
        }
    }

    public int uniquePathsTopDown(int m, int n) {
        if (m == 0 || n == 0) {
            return 1;
        }
        int[][] dp = new int[m][n];
        return uniquePathsTopDownBacktrack(dp, m, n, 0, 0);
    }

    private int uniquePathsTopDownBacktrack(int[][] dp, int m, int n, int r, int c) {
        if (r == m - 1 && c == n - 1) {
            return 1;
        }
        if (r == m || c == n) {
            return 0;
        }
        if (dp[r][c] != 0) {
            return dp[r][c];
        }
        dp[r][c] = uniquePathsTopDownBacktrack(dp, m, n, r + 1, c) + uniquePathsTopDownBacktrack(dp, m, n, r, c + 1);
        return dp[r][c];
    }

    public int uniquePathsBottomUp(int m, int n) {
        if (m == 0 || n == 0) {
            return 1;
        }
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][n - 1] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[m - 1][j] = 1;
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
            }
        }

        return dp[0][0];
    }

    public int climbStairs(int n) {
        if (n == 0) {
            return 1;
        }
        int[] dp = new int[n];
        return climbStairsBacktrack(dp, n, 0);
    }

    private int climbStairsBacktrack(int[] dp, int n, int cur) {
        if (cur == n) {
            return 1;
        }
        if (cur > n) {
            return 0;
        }
        if (dp[cur] != 0) {
            return dp[cur];
        }
        dp[cur] = climbStairsBacktrack(dp, n, cur + 1) + climbStairsBacktrack(dp, n, cur + 2);
        return dp[cur];
    }

    public int climbStairsBottomUp(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[n] = 1;
        dp[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = dp[i + 1] + dp[i + 2];
        }
        return dp[0];
    }

    public String minWindowNoDuplicate(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0) {
            return "";
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, 0);
        }
        int count = 0, fast = 0, slow = 0;
        for (fast = 0; fast < s.length(); fast++) {
            char key = s.charAt(fast);
            if (map.containsKey(key)) {
                int value = map.get(key);
                if (value == 0) {
                    count++;
                }
                map.put(key, value + 1);
                if (count == t.length()) {
                    break;
                }
            }
        }
        if (count < t.length()) {
            return "";
        }

        int resFast = fast, resSlow = 0, resLen = fast;
        boolean shrink = true;
        while (fast < s.length()) {
            if (shrink) {
                char key = s.charAt(slow);
                if (map.containsKey(key)) {
                    int value = map.get(key);
                    value--;
                    if (value == 0) {
                        shrink = false;
                        if (fast - slow < resLen) {
                            resLen = fast - slow;
                            resFast = fast;
                            resSlow = slow;
                        }
                    }
                    map.put(key, value);
                }
                slow++;
            } else {
                fast++;
                if (fast < s.length()) {
                    char key = s.charAt(fast);
                    if (map.containsKey(key)) {
                        int value = map.get(key);
                        value++;
                        map.put(key, value);
                        if (value == 1) {
                            shrink = true;
                        }
                    }
                }
            }
        }
        return s.substring(resSlow, resFast + 1);

    }

    public String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        Map<Character, Integer> dictT = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        int required = dictT.size();
        int l = 0, r = 0;
        int formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<>();
        int[] ans = {-1, 0, 0};
        while (r < s.length()) {
            char c = s.charAt(r);
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            while (l <= r && formed == required) {
                c = s.charAt(l);
                if (ans[0] == -1 || r - l + 1 < ans[0]) {
                    ans[0] = r - l + 1;
                    ans[1] = l;
                    ans[2] = r;
                }
                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }

    public int minDistance(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        }
        if (word1 == "") {
            return word2.length();
        }
        if (word2 == "") {
            return word1.length();
        }

        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
                } else {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }

    public int largestRectangleAreaMess(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        PriorityQueue<ValueIndexPair> pqLeft = new PriorityQueue<>(new LargestRectangleAreaComparator());
        PriorityQueue<ValueIndexPair> pqRight = new PriorityQueue<>(new LargestRectangleAreaComparator());
        int[] leftSize = new int[heights.length];
        int[] rightSize = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            pqLeft.add(new ValueIndexPair(heights[i], i));
            int leftEdge = binarySearchValueIndexPairs(pqLeft.toArray(), heights[i], false, heights.length - 1);
            leftSize[i] = leftEdge < 0 ? 0 : heights[i] * (i - leftEdge);
        }
        for (int i = heights.length - 1; i >= 0; i--) {
            pqRight.add(new ValueIndexPair(heights[i], i));
            int rightEdge = binarySearchValueIndexPairs(pqRight.toArray(), heights[i], true, heights.length - 1);
            rightSize[i] = rightEdge < 0 ? 0 : heights[i] * (rightEdge - i);
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < heights.length; i++) {
            result = Math.max(result, leftSize[i] + rightSize[i] + 1);
        }
        return result;
    }

    private int binarySearchValueIndexPairs(Object[] objPairs, int target, boolean reverse, int lastIndex) {
        ValueIndexPair[] pairs = new ValueIndexPair[objPairs.length];
        for (int i = 0; i < objPairs.length; i++) {
            pairs[i] = (ValueIndexPair) objPairs[i];
        }
        int left = 0;
        int right = pairs.length - 1;
        int mid = 0;
        while (left < right - 1) {
            mid = left + (right - left) / 2;
            if (pairs[mid].value >= target) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }

        if (pairs[mid].value >= target) {
            while (mid >= 0 && pairs[mid].value >= target) {
                mid--;
            }
        }

        if (mid < 0) {
            if (reverse) {
                return lastIndex;
            }
            return 0;
        }

        if (!reverse) {
            while (mid < pairs.length - 1 && pairs[mid + 1].value == pairs[mid].value) {
                mid++;
            }
        } else {
            while (mid > 0 && pairs[mid - 1].value == pairs[mid].value) {
                mid--;
            }
        }
        return pairs[mid].index;
    }

    private class ValueIndexPair {
        public int value;
        public int index;

        public ValueIndexPair(int v, int i) {
            value = v;
            index = i;
        }
    }

    private class LargestRectangleAreaComparator implements Comparator<ValueIndexPair> {
        @Override
        public int compare(ValueIndexPair o1, ValueIndexPair o2) {
            if (o1.value < o2.value) {
                return -1;
            }
            if (o1.value == o2.value) {
                return o1.index < o2.index ? -1 : o1.index == o2.index ? 0 : 1;
            }

            return 1;
        }
    }

    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int[] leftMin = new int[heights.length];
        int[] rightMin = new int[heights.length];
        leftMin[0] = -1;
        rightMin[heights.length - 1] = heights.length;

        for (int i = 1; i < heights.length; i++) {
            int p = i - 1;
            while (p >= 0 && heights[p] >= heights[i]) {
                p = leftMin[p];
            }
            leftMin[i] = p;
        }
        for (int i = heights.length - 2; i >= 0; i--) {
            int p = i + 1;
            while (p < heights.length && heights[p] >= heights[i]) {
                p = rightMin[p];
            }
            rightMin[i] = p;
        }

        int result = 0;
        for (int i = 0; i < heights.length; i++) {
            result = Math.max(result, heights[i] * (rightMin[i] - leftMin[i] - 1));
        }
        return result;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m - 1;
        int i2 = n - 1;
        int p = m + n - 1;
        while (i1 >= 0 || i2 >= 0) {
            if (i1 < 0) {
                nums1[p] = nums2[i2];
                i2--;
            } else if (i2 < 0) {
                nums1[p] = nums1[i1];
                i1--;
            } else {
                if (nums1[i1] >= nums2[i2]) {
                    nums1[p] = nums1[i1];
                    i1--;
                } else {
                    nums1[p] = nums2[i2];
                    i2--;
                }
            }
            p--;
        }
    }

    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        HashSet<Character> set = new HashSet<>(Arrays.asList(new Character[]{'7', '8', '9'}));
        int[] dp = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i] = -1;
        }
        return numDecodingsTopdown(s, 0, dp, set);
    }

    private int numDecodingsTopdown(String s, int index, int[] dp, HashSet<Character> set) {
        if (index < s.length() && s.charAt(index) == '0') {
            return 0;
        }
        if (index < s.length() - 1) {
            if (s.charAt(index) != '1' && s.charAt(index) != '2' && s.charAt(index + 1) == '0') {
                return 0;
            }
        }
        if (index >= s.length() - 1) {
            return 1;
        }

        if (dp[index] != -1) {
            return dp[index];
        }

        if (index < s.length()) {
            if (s.charAt(index + 1) == '0') {
                dp[index] = numDecodingsTopdown(s, index + 2, dp, set);
            } else if (s.charAt(index) == '1' || (s.charAt(index) == '2' && !set.contains(s.charAt(index + 1)))) {
                dp[index] = numDecodingsTopdown(s, index + 1, dp, set) + numDecodingsTopdown(s, index + 2, dp, set);
            } else {
                dp[index] = numDecodingsTopdown(s, index + 1, dp, set);
            }
        }
        return dp[index];
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversalRecur(root, result);
        return result;
    }

    private void inorderTraversalRecur(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        inorderTraversalRecur(node.left, result);
        result.add(node.val);
        inorderTraversalRecur(node.right, result);
    }

    public List<Integer> inorderTraversalIter(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        HashSet<TreeNode> visited = new HashSet<>();

        stack.push(root);

        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (visited.contains(node)) {
                result.add(node.val);
                continue;
            }
            visited.add(node);
            if (node.right != null) {
                stack.push(node.right);
            }
            stack.push(node);
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return result;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        if (root == null) {
            return result;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelNumber = queue.size();
            List<Integer> subList = new ArrayList<>();
            for (int i = 0; i < levelNumber; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                subList.add(node.val);
            }
            result.add(subList);
        }
        return result;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) {
            return null;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTreePreIn(preorder, 0, preorder.length, 0, map);
    }

    private TreeNode buildTreePreIn(int[] preorder, int preIndex, int preEnd, int inLeft, HashMap<Integer, Integer> map) {
        if (preIndex == preEnd) {
            return null;
        }

        TreeNode node = new TreeNode(preorder[preIndex]);
        int i = map.get(preorder[preIndex]);
        int leftCount = i - inLeft;
        node.left = buildTreePreIn(preorder, preIndex + 1, preIndex + leftCount + 1, inLeft, map);
        node.right = buildTreePreIn(preorder, preIndex + leftCount + 1, preEnd, i + 1, map);

        return node;
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return sortedArrayToBSTRecur(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTRecur(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = sortedArrayToBSTRecur(nums, left, mid - 1);
        node.right = sortedArrayToBSTRecur(nums, mid + 1, right);
        return node;
    }

    public void flatten(TreeNode root) {
        flattenRecur(root);
    }

    private TreeNode flattenRecur(TreeNode node) {
        if (node == null) {
            return null;
        }

        TreeNode leftNode = node.left;
        TreeNode rightNode = node.right;
        node.right = flattenRecur(leftNode);
        TreeNode cur = node;
        while (cur.right != null) {
            cur = cur.right;
        }
        cur.right = flattenRecur(rightNode);
        node.left = null;
        return node;
    }

    public int maxProfit(int[] prices) {
        int result = 0;
        int curMin = Integer.MAX_VALUE;

        for (int val : prices) {
            if (val <= curMin) {
                curMin = val;
            } else {
                result = Math.max(result, val - curMin);
            }
        }

        return result;
    }

    public int maxProfit2(int[] prices) {
        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                result += prices[i] - prices[i - 1];
            }
        }
        return result;
    }

    public int maxPathSum(TreeNode root) {
        maxPathSumSingle(root);
        return maxPathSumResult;
    }

    private int maxPathSumResult = Integer.MIN_VALUE;

    private int maxPathSumSingle(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftMax = maxPathSumSingle(node.left);
        int rightMax = maxPathSumSingle(node.right);

        int curSum = node.val;
        int pathResult = node.val;
        if (leftMax > 0) {
            curSum += leftMax;
            pathResult += leftMax;
        }
        if (rightMax > 0) {
            curSum += rightMax;
            pathResult = Math.max(pathResult, node.val + rightMax);
        }
        maxPathSumResult = Math.max(maxPathSumResult, curSum);

        return pathResult;
    }

    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int n : nums) {
            set.add(n);
        }

        int result = 0;
        for (int n : nums) {
            if (set.contains(n - 1)) {
                continue;
            }
            int count = 0;
            while (set.contains(n)) {
                count++;
                n++;
            }
            result = Math.max(result, count);
        }

        return result;
    }

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();
        return cloneGraphRecur(node, map);
    }

    private Node cloneGraphRecur(Node node, HashMap<Node, Node> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node cloneNode = new Node(node.val);
        map.put(node, cloneNode);

        for (Node neighbor : node.neighbors) {
            Node cloneNeighbor = cloneGraphRecur(neighbor, map);
            cloneNode.neighbors.add(cloneNeighbor);
        }
        return cloneNode;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0;
        int end = 0;
        int fuel = gas[start] - cost[start];

        do {
            if (fuel >= 0) {
                end++;
                if (end == gas.length) {
                    end = 0;
                }
                if (end == start) {
                    return start;
                }
                fuel = fuel + gas[end] - cost[end];
            } else {
                start--;
                if (start == -1) {
                    start = gas.length - 1;
                }
                if (end == start) {
                    return -1;
                }
                fuel = fuel + gas[start] - cost[start];
            }

        } while (start != end);

        return -1;
    }

    public RandomNode copyRandomList(RandomNode head) {
        HashMap<RandomNode, RandomNode> map = new HashMap<>();
        return copyRandomListRecur(head, map);
    }

    private RandomNode copyRandomListRecur(RandomNode node, HashMap<RandomNode, RandomNode> map) {
        if (node == null) {
            return null;
        }
        if (map.containsKey(node)) {
            return map.get(node);
        }

        RandomNode curNode = new RandomNode(node.val);
        map.put(node, curNode);

        curNode.next = copyRandomListRecur(node.next, map);
        curNode.random = copyRandomListRecur(node.random, map);

        return curNode;
    }

    public boolean hasCycle(ListNode head) {
        ListNode faster = head;
        ListNode slower = head;

        do {
            if (faster == null || faster.next == null) {
                return false;
            }
            faster = faster.next.next;
            slower = slower.next;
            if (faster == slower) {
                return true;
            }
        } while (true);
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        HashSet<TreeNode> visited = new HashSet<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (visited.contains(node)) {
                result.add(node.val);
                continue;
            } else {
                visited.add(node);
                stack.push(node);
                if (node.right != null)
                    stack.push(node.right);
                if (node.left != null)
                    stack.push(node.left);
            }
        }
        return result;
    }

    public static class LRUCache {
        class DoubleLinkedListNode {
            int key;
            int val;
            DoubleLinkedListNode next;
            DoubleLinkedListNode prev;

            public DoubleLinkedListNode(int _key, int _val) {
                key = _key;
                val = _val;
            }
        }

        HashMap<Integer, DoubleLinkedListNode> map;
        DoubleLinkedListNode head;
        DoubleLinkedListNode end;
        int capacity;
        int count;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            count = 0;
            head = new DoubleLinkedListNode(-1, -1);
            end = head;
            map = new HashMap<>();
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                DoubleLinkedListNode node = map.get(key);
                if (node != end) {
                    DoubleLinkedListNode prev = node.prev;
                    DoubleLinkedListNode next = node.next;
                    prev.next = next;
                    if (next != null)
                        next.prev = prev;
                    end.next = node;
                    node.prev = end;
                    end = node;
                }
                return node.val;
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (map.containsKey(key)) {
                get(key);
                DoubleLinkedListNode node = map.get(key);
                node.val = value;
            } else if (count < capacity) {
                DoubleLinkedListNode newNode = new DoubleLinkedListNode(key, value);
                map.put(key, newNode);
                end.next = newNode;
                newNode.prev = end;
                end = newNode;
                count++;
            } else {
                DoubleLinkedListNode first = head.next;
                head.next = first.next;
                if (head.next != null) {
                    head.next.prev = head;
                }
                map.remove(first.key);
                first.next = null;
                first.prev = null;
                count--;
                if (end == first) {
                    end = head;
                }
                put(key, value);
            }
        }
    }

    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        int result = 1;

        for (int i = 0; i < points.length - 1; i++) {
            int overlap = 0;
            HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                    overlap++;
                    continue;
                }
                int x = points[i][0] - points[j][0];
                int y = points[i][1] - points[j][1];
                int k = GCD(x, y);
                x /= k;
                y /= k;
                if (!map.containsKey(x)) {
                    HashMap<Integer, Integer> counts = new HashMap<>();
                    counts.put(y, 1);
                    map.put(x, counts);
                } else {
                    HashMap<Integer, Integer> counts = map.get(x);
                    if (!counts.containsKey(y)) {
                        counts.put(y, 1);
                    } else {
                        int curCount = counts.get(y);
                        counts.put(y, curCount + 1);
                    }
                }
            }
            int self = overlap + 1;
            result = Math.max(result, self);
            for (HashMap<Integer, Integer> m : map.values()) {
                for (int value : m.values()) {
                    result = Math.max(result, self + value);
                }
            }
        }

        return result;
    }

    private int GCD(int x, int y) {
        if (y == 0) {
            return x;
        }
        return GCD(y, x % y);
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        HashSet<String> set = new HashSet<>(Arrays.asList(new String[]{"+", "-", "*", "/"}));

        int result = 0;
        for (String t : tokens) {
            if (set.contains(t)) {
                int second = stack.pop();
                int first = stack.pop();
                int ans = 0;
                switch (t) {
                    case "+":
                        ans = first + second;
                        break;
                    case "-":
                        ans = first - second;
                        break;
                    case "*":
                        ans = first * second;
                        break;
                    case "/":
                        ans = first / second;
                }
                stack.push(ans);
            } else {
                stack.push(Integer.valueOf(t));
            }
        }
        return stack.pop();
    }

    public int maxProduct(int[] nums) {
        int min = nums[0];
        int max = nums[0];
        int result = max;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                int tmp = min;
                min = max;
                max = tmp;
            }
            max = Math.max(nums[i], nums[i] * max);
            min = Math.min(nums[i], nums[i] * min);
            result = Math.max(result, max);
        }

        return result;
    }
}
