//In this ques  thhere given a hotels with a[i] number of rooms in it .So there are someguests coming which depand r no of rooms to fulfill their needs but this is confirm that they only will choose only one hotel and not like one room form this hotel and n room from another//
//it is also given that it will search for thr nearest hotel which fulfill its requirment and it is starting from left

//so it will be solve from segment tree tfor optimization and we will build a segment tree for the given array and in the segment tree we will store the maximum number of rooms available in that segment and when we will get the query then we will check if the maximum number of rooms available in that segment is greater than or equal to the required number of rooms then we will go to the left child of that segment tree node and if it is not then we will go to the right child of that segment tree node and we will keep on doing this until we reach a leaf node and if we reach a leaf node then we will check if the number of rooms available in that hotel is greater than or equal to the required number of rooms then we will return the index of that hotel otherwise we will return -1
//and then update the no of rooms left in that hotel
import java.util.*;


class Solution {
    int arr[];
    int segtree[];
    int n;
    public Solution(int n, int[] arr) {
        this.n = n;
        this.arr = arr;
        segtree = new int[4 * n];
        build(0, 0, n - 1);
    }   
    public void build(int index, int start, int end) {
        if (start == end) {
            segtree[index] = arr[start];
            return;
        }
        int mid = (start + end) / 2;
        build(2 * index + 1, start, mid);
        build(2 * index + 2, mid + 1, end);
        segtree[index] = Math.max(segtree[2 * index + 1], segtree[2 * index + 2]);
    }
    public int query(int index, int start, int end, int k) {
        if (segtree[index] < k) return -1; // No hotel in this segment can fulfill the requirement
        if (start == end){
            int ans=start; // Found the hotel
            update(0, 0, n - 1, ans, k); // Update the segment tree after booking the rooms 
            return ans; //returning index of the hotel which is booked
        }
        int mid = (start + end) / 2;
        int leftResult = query(2 * index + 1, start, mid, k);
        if (leftResult != -1) return leftResult; // Found in left child
        return query(2 * index + 2, mid + 1, end, k); // Search in right child
    }
    public void update(int index, int start, int end, int hotelIndex, int roomsBooked) {
        if (start == end) {
            segtree[index] -= roomsBooked; // Update the number of rooms left in that hotel
            return;
        }
        int mid = (start + end) / 2;
        if (hotelIndex <= mid) {
            update(2 * index + 1, start, mid, hotelIndex, roomsBooked);
        } else {
            update(2 * index + 2, mid + 1, end, hotelIndex, roomsBooked);
        }
        segtree[index] = Math.max(segtree[2 * index + 1], segtree[2 * index + 2]); // Update the current node after updating the child
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       

        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        Solution solution = new Solution(n, arr);
        for (int i = 0; i < q; i++) {
            int k = sc.nextInt();
            int result = solution.query(0, 0, n - 1, k);
            System.out.println(result + 1); // +1 for 1-based indexing
        }
        System.out.flush();
    }
}   
    