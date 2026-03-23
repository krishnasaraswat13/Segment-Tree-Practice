//In this ques there given an array of interegr in which we have to do qquery in range in 
//whic we select two integer i and j and check if(ar[i]%arr[j]==0) then i++ and vise versa for 
//j++;  basically we have to check if in sequence i there is a number which divide every number
 //in a range then it is removed or we call it so if i is incereas eto j-i+1 then it is removed 
 //so we have to return the remaining numbers in the range


 //so the approach for this ques is simpe we nee dto find the number whic divides every number 
 // in that range it means basically it is the gcd of that range so we will find the gcd of that
 //  range and then we will check how many numbers are divisible by that gcd in that range and
 //  then we will return the total number of elements in that range minus the count of numbers 
 // which are divisible by that gcd because those are the numbers which are removed from the range

 class SegmentTree {
    int[] tree;
    int n;
    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }
    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);
            tree[node] = gcd(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    public int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return 0; // Return 0 for out of range
        }
        if (l <= start && end <= r) {
            return tree[node];
        }
        int mid = (start + end) / 2;
        int leftGCD = query(2 * node + 1, start, mid, l, r);
        int rightGCD = query(2 * node + 2, mid + 1, end, l, r); 
        return gcd(leftGCD, rightGCD);
    }
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}   
