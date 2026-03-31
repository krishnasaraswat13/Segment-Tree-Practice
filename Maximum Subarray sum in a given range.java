import java.io.*;
import java.util.*;

/* Node of the segment tree consisting of:
1. Maximum Prefix Sum,
2. Maximum Suffix Sum,
3. Total Sum,
4. Maximum Sub-Array Sum */
class Node {
  int maxPrefixSum;
  int maxSuffixSum;
  int totalSum;
  int maxSubarraySum;

  Node()
  {
    maxPrefixSum = maxSuffixSum = maxSubarraySum
      = Integer.MIN_VALUE;
    totalSum = Integer.MIN_VALUE;
  }
}

class GFG {

  static final int inf = 0x3f3f;

  // Returns Parent Node after merging its left and right
  // child
  static Node merge(Node leftChild, Node rightChild)
  {
    Node parentNode = new Node();
    parentNode.maxPrefixSum = Math.max(
      leftChild.maxPrefixSum,
      leftChild.totalSum + rightChild.maxPrefixSum);
    parentNode.maxSuffixSum = Math.max(
      rightChild.maxSuffixSum,
      rightChild.totalSum + leftChild.maxSuffixSum);
    parentNode.totalSum
      = leftChild.totalSum + rightChild.totalSum;
    parentNode.maxSubarraySum
      = Math.max(Math.max(leftChild.maxSubarraySum,
                          rightChild.maxSubarraySum),
                 leftChild.maxSuffixSum
                 + rightChild.maxPrefixSum);
    return parentNode;
  }

  // Builds the Segment tree recursively
  static void constructTreeUtil(Node[] tree, int[] arr,
                                int start, int end,
                                int index)
  {
    /* Leaf Node */
    if (start == end) {
      // single element is covered under this range
      tree[index].totalSum = arr[start];
      tree[index].maxSuffixSum = arr[start];
      tree[index].maxPrefixSum = arr[start];
      tree[index].maxSubarraySum = arr[start];
      return;
    }

    // Recursively Build left and right children
    int mid = (start + end) / 2;
    constructTreeUtil(tree, arr, start, mid, 2 * index);
    constructTreeUtil(tree, arr, mid + 1, end,
                      2 * index + 1);

    // Merge left and right child into the Parent Node
    tree[index]
      = merge(tree[2 * index], tree[2 * index + 1]);
  }

  /* Function to construct segment tree from given array.
     * This function allocates memory for segment tree and
     * calls constructTreeUtil() to fill the allocated
     * memory */
  static Node[] constructTree(int[] arr, int n)
  {
    // Allocate memory for segment tree
    int x = (int)(Math.ceil(
      Math.log(n)
      / Math.log(2))); // Height of the tree

    // Maximum size of segment tree
    int max_size = 2 * (int)Math.pow(2, x) - 1;
    Node[] tree = new Node[max_size];
    for (int i = 0; i < max_size; i++)
      tree[i] = new Node();

    // Fill the allocated memory tree
    constructTreeUtil(tree, arr, 0, n - 1, 1);

    // Return the constructed segment tree
    return tree;
  }

  


// Segment Tree Merge Logic: Max Subarray Sum
// This logic is used to solve the Maximum Subarray Sum problem (Kadane’s) within a Segment Tree, allowing for both range queries and point updates in O(logn) time.

// 1. Node Properties
// Each node in the tree must store four specific values to represent its range:

// totalSum: The sum of all elements in the range.

// maxPrefixSum: The highest sum starting from the leftmost element.

// maxSuffixSum: The highest sum ending at the rightmost element.

// maxSubarraySum: The absolute best subarray sum found anywhere within the range.

// 2. Merge Equations
// To combine a leftChild and rightChild into a parentNode, use the following logic:

// Total Sum

// The sum of the whole range is simply the sum of both halves.

// parent.totalSum = left.totalSum + right.totalSum

// Max Prefix Sum

// Either the best prefix of the left side, OR the entire left side plus the best prefix of the right.

// parent.maxPrefixSum = max(left.maxPrefixSum, left.totalSum + right.maxPrefixSum)

// Max Suffix Sum

// Either the best suffix of the right side, OR the entire right side plus the best suffix of the left.

// parent.maxSuffixSum = max(right.maxSuffixSum, right.totalSum + left.maxSuffixSum)

// Max Subarray Sum

// The best subarray is either entirely in the left, entirely in the right, or straddles the middle (left's suffix + right's prefix).

// parent.maxSubarraySum = max(left.maxSubarraySum, right.maxSubarraySum, left.maxSuffixSum + right.maxPrefixSum)

