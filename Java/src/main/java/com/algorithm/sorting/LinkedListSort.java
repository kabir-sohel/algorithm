package com.algorithm.sorting;
/*
 * problem : https://leetcode.com/problems/sort-list
 */

/**
 * Definition for singly-linked list.
 */

public class LinkedListSort {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null)return head;
        // 1. Split the list in two halvesl
        ListNode mid = getMid(head);
        // 2. Sort each of them
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        // 3. Merge them
        return mergeLists(left, right);
    }
    private ListNode mergeLists(ListNode left, ListNode right){
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        while(left != null && right != null){
            if(left.val <= right.val){
                tail.next = left;
                left = left.next;
            }
            else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        // Still some items maybe left
        if(left != null)tail.next = left;
        if(right != null)tail.next = right;

        return dummy.next;
    }

    private ListNode getMid(ListNode head){
        ListNode slow = head, fast = head, prev = null;
        while(fast != null && fast.next != null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        if(prev != null) prev.next = null;
        return slow;
    }
}
