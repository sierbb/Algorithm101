package LinkedList;

import myObject.ListNode;

public class ReverseNodesInKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        //corner case:
        if (head == null || head.next == null || k == 1) return head;
        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;
        prev.next = head;
        ListNode cur = head;

        int count = 1;
        while (cur!= null && cur.next != null){
            cur = cur.next;
            count++;
            if (count == k){
                //start the reverse process
                ListNode nextStart = cur.next;
                //Method 1: recursive way
                //ListNode newHead = reverseLinkedList(head, cur);
                //Method 2: iterative way
                ListNode newHead = reverseLinkedListIterative(head, cur);
                prev.next = newHead;
                head.next = nextStart;
                //set cur to new start
                prev = head;
                head = head.next;
                cur = head;
                count = 1;//reset count
            }
        }

        return dummy.next;
    }

    private ListNode reverseLinkedList(ListNode head, ListNode tail){
        //reverse the linkedList from head to tail
        //base case
        if (head == tail) return head;
        ListNode newHead = reverseLinkedList(head.next, tail);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    private ListNode reverseLinkedListIterative(ListNode head, ListNode tail){
        //iterative way to reverse LinkedList from head to tail
        ListNode prev = head;
        ListNode cur = head;
        ListNode next = cur.next;
        //     <- 1  <-  2    ->  5 tail
        //          prev cur    next     N
        while (cur != tail){
            ListNode N = next.next;
            next.next = cur;
            prev = cur;
            cur = next;
            next = N;
        }
        //now cur == tail
        tail.next = prev;
        return tail;
    }

}

//Clarification: listNode length guarantee >=k >=1

//M1:
//Step1: go to the kth node each time, if not find the next session length >= k then do nothing
//Step2: once found, record head and head+k node, record nextStart, do a reverse likedList from head+k, attach head to nextStart, and attached prev to newHead.
//Step3: do until we dont find a sessio >= k

//      [ ->2, -> 1->     3,     4,   5]
//prev          head  nextStart

//linar scan to find the newHead, if not found, return
//prev.next = newHead;
//head.next=nextStart;
//prev.head, head = nextStart
//start counting reverse on nextStart

//TC:O(n)
//SC:O(k)

//M2:
//TC:O(n)
//SC:O(1) each iterarive reverse is O(1)
