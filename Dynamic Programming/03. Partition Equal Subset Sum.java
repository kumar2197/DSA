/*
Company Tags:  Accolite Adobe Amazon Drishti-Soft OYO Rooms
Practice Portal:
Geeksforgeeks: https://practice.geeksforgeeks.org/problems/subset-sum-problem2014/1
LeetCode: https://leetcode.com/problems/partition-equal-subset-sum/
*/

// PARENT PROBLEM: Subset Sum Problem & 0-1 Knapsack

/************************************************** Recursive Solution ************************************************/
// In recursion, at every index, we have 2 choices, either to pick the current VAL or leave the current VAL

class Solution{
    static int equalPartition(int n, int arr[]) {
        int sum = 0;
        for (int i=0; i<n; i++) {
            sum = sum + arr[i];
        }

        //If sum ODD, arr can't be devided in 2 Equal Sum Partition
        if (sum%2 != 0)
            return 0;

        int halfSum = sum/2;

        return subSetSum(arr, halfSum, n);
    }

    //Recursive Solution
    static int subSetSum(int[] arr, int sum, int n) {
        if (sum == 0)
            return 1;
        if (n <= 0)
            return 0;

        if (sum >= arr[n-1]) {
            return Math.max(subSetSum(arr, sum, n-1),
                    subSetSum(arr, sum - arr[n-1], n-1));
        } else {
            return subSetSum(arr, sum, n-1);
        }
    }
}

/*
Time Complexity: O(2^n) In the worst case, this solution tries two possibilities (whether to include or exclude) for every element.
*/

/****************************************** Memoization Solution (Top-Down) *******************************************/
/*
Recursive Solution can be converted into Memoization Solution
1. We pass a Memoization matrix with -1 filled.
2. Recursive Solution remain exactly same, just added one if() to check if we have alredy solved this subproblem,
if TRUE return its value directly,
if FALSE, then after getting ans save in the Memoization matrix.
*/

class Solution{
    static int equalPartition(int n, int arr[]) {
        int sum = 0;
        for (int i=0; i<n; i++) {
            sum = sum + arr[i];
        }

        //If sum ODD, arr can't be devided in 2 Equal Sum Partition
        if (sum%2 != 0)
            return 0;

        int halfSum = sum/2;

        int[][] dp = new int[n + 1][sum + 1];

        for (int i=0; i<n+1; i++) {
            for (int j=0; j<halfSum+1; j++) {
                dp[i][j] = -1;
            }
        }

        return subSetSum(arr, halfSum, n, dp);
    }

    //Memoization Solution (Top-Down) : Recursive Call + DP Matrix
    static int subSetSum(int[] arr, int sum, int n, int[][] dp) {
        if (sum == 0)
            return 1;
        if (n <= 0)
            return 0;

        if (dp[n][sum] != -1)
            return dp[n][sum];

        if (sum >= arr[n-1]) {
            dp[n][sum] = Math.max(subSetSum(arr, sum, n-1, dp),
                    subSetSum(arr, sum - arr[n-1], n-1, dp));
        } else {
            dp[n][sum] = subSetSum(arr, sum, n-1, dp);
        }

        return dp[n][sum];
    }
}

/*
Time Complexity: O(sum * n)
Auxiliary Space: O(sum * n)
*/

/************************************************** Bottom-up Solution ************************************************/
/*
Memoization Solution (Top-Down) can be converted into Bottom-up Solution
ROW are always n/size i.e. Size of the array.
1. We create a Bottom-up DP matrix.
2. Bottom-up DP matrix inicialization is done based on Base condition for recursion
3. From Memoization Solution
    3.1. n -> i
    3.2. W -> j
*/

class Solution{
    static int equalPartition(int n, int arr[]) {
        int sum = 0;
        for (int i=0; i<n; i++) {
            sum = sum + arr[i];
        }

        //If sum ODD, arr can't be devided in 2 Equal Sum Partition
        if (sum%2 != 0)
            return 0;

        int halfSum = sum/2;

        return subSetSum(arr, halfSum, n);
    }

    //Recursive Solution
    static int subSetSum(int[] arr, int sum, int n) {
        int size = arr.length;
        int[][] dp = new int[size + 1][sum + 1];

        for (int i=0; i<size+1; i++) {
            dp[i][0] = 1;
        }
        for (int j=1; j<sum+1; j++) {
            dp[0][j] = 0;
        }

        for (int i=1; i<size+1; i++) {
            for (int j=1; j<sum+1; j++) {

                if (j >= arr[i-1]) {
                    dp[i][j] = Math.max(dp[i-1][j],
                            dp[i-1][j-arr[i-1]]);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[size][sum];
    }
}

/*
Time Complexity: O(sum * n)
Auxiliary Space: O(sum * n)
*/