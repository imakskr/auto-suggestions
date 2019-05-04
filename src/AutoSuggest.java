package javaAll;

import java.util.Scanner;

public class AutoSuggest {
	
	static class Node {
		char a;
		Node sub;
		Node next;
		Boolean isLastKey;
		Node(char a){
			this.a=a;
			this.sub=null;
			this.next=null;
			this.isLastKey=false;
		}
	}
	private static Node root=null;
	private static Node currentIteratingNode=root;	

	private static void displaySuggestions(Node pointer, String curPath) {
		Node temp=pointer;		
		if(temp==null) {
			return;
		}
		curPath=curPath+temp.a;
		if(temp.isLastKey==true) {
			System.out.println(curPath);
		}
		displaySuggestions(temp.sub, curPath);		
		displaySuggestions(temp.next, curPath.substring(0, curPath.length()-1));
		
	}
	
	private static Boolean isNodePresent(char letter) {
		Boolean status=false;
		Node temp=currentIteratingNode;
		while(temp!=null) {
			currentIteratingNode=temp;
			if(temp.a==letter) {
				status=true;
				break;
			}
			temp=temp.next;
		}		
		return status;
	}

	private static Node createCompleteWord(String word) {
		int length=word.length();
		Node list=null;
		Node currentPointer=list;
		for(int i=0; i<length; i++) {
			Node node=new Node(word.charAt(i));
			if(list==null) {
				list=node;				
			}else {
				currentPointer.sub=node;
			}
			currentPointer=node;
		}
		currentPointer.isLastKey=true;
		return list;
	}
	
	private static void insertWord(String word) {
		int length=word.length();
		currentIteratingNode=root;
		if(root==null) {
			root=createCompleteWord(word);
		}else {
			for(int i=0; i<length; i++) {
				char letter=word.charAt(i);
				if(isNodePresent(letter)) {
					if(currentIteratingNode.sub==null && i<length-1) {
						currentIteratingNode.sub=createCompleteWord(word.substring(i+1, length));
						break;
					}else {
						if(i==length-1) {
							currentIteratingNode.isLastKey=true;
						}
						currentIteratingNode=currentIteratingNode.sub;
					}					
				}else {
					currentIteratingNode.next=createCompleteWord(word.substring(i,length));
					break;			
				}				
			}
		}
	}
	
	public static void main(String a[]) {
		Scanner s=new Scanner(System.in);
		int choice;
		do {
			System.out.println("1. Insert\n2. Display\n3.Exit\nEnter your choice: ");
			choice=s.nextInt();
			s.nextLine();
			switch(choice) {
				case 1: {
					System.out.println("Enter a word: ");
					String word=s.nextLine();
					insertWord(word);
					break;
				}
				case 2: {
					displaySuggestions(root,""); 
					break;
				}
				default: choice=0;
			}
		}while(choice!=0);
		s.close();		
	}
}
