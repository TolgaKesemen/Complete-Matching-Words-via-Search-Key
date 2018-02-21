package MatchingWords1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatchingWords {
	/**
	 * The MatchingWords program taking inputs; text file, number of
	 * matches to show and search key as command line arguments.
	 */

	/**
	 * This is the main method. That is reading input text and makes use
	 * of program.
	 * @author Onur Tolga KESEMEN
	 * @param args That contains text file, number of matches to show, searchkey.
	 * @return Nothing.
	 * @exception FileNotFoundException
	 */
	public static void main(String[] args) {
		ArrayList<Text> list = new ArrayList<Text>();	
		int length = 0, k = 0;
		String searchKey;
		try{
			Scanner scanner = new Scanner(args[1]);
			k = Integer.parseInt(scanner.nextLine());
			scanner = new Scanner(args[2]);
			searchKey = scanner.nextLine();
			scanner = new Scanner(new File(args[0]));
			while(scanner.hasNextLine()){
				String line =scanner.nextLine();
				String[] pair = line.split("\t");					//Splitting the line according to 'tab' character
				pair[0] = pair[0].replaceAll("\\s+", "");			//Replacing unnecessary whitespace
				if(pair.length == 1){
					length = Integer.parseInt(pair[0]);				//Taking the number of pairs in the input file
				}
				else{
					Text temp = new Text();							//Creating objects and adding them to ArrayList
					temp.setWeight(Integer.parseInt(pair[0]));
					temp.setString(pair[1]);
					list.add(temp);
				}
			}
		}
		catch (FileNotFoundException ex) {									
			System.out.println("No File Found!");
			return;
			}
		list = sorting(list, length, 0);							//Sorting the array according to string
		ArrayList<Text> query = new ArrayList<Text>();
		query = binarySearch(list, searchKey);						//Searching for matching terms
		length = query.size();
		query = sorting(query, length, 1);							//Sorting the array according to weight
		query = reverse(query, length);								//Changing order of the array form ascending to descending
		for(int i=0; i<k; i++){
			System.out.println(query.get(i).getWeight()+"\t"+query.get(i).getString());
		}
	}
	
	/**
	 * This is sorting method.
	 * This method sorts the list with Heap Sort Algorithm.
	 * @param list
	 * @param length
	 * @param precondition
	 * @return list
	 */
	public static ArrayList<Text> sorting(ArrayList<Text> list, int length, int precondition){
		int n = length-1;
		for(int i=(n/2); i>=0; i--){
			list = sink(list, i, n, precondition);
		}
		while(n>0){
			list = exch(list, 0, n);
			list = sink(list, 0, (n-1), precondition);
			n--;
		}
		return list;
	}
	
	/**
	 * This is sink method which sinks down the element in the list if it is necessary.
	 * There is a precondition for looking which compare is going to be done.
	 * @param list
	 * @param k
	 * @param length
	 * @param precondition
	 * @return list
	 */
	public static ArrayList<Text> sink(ArrayList<Text> list, int k, int length, int precondition){
		int n = length;
		while((2*k)<n){
			int j = 2*k;
			if(precondition==0){
				if((j<n) && less1(list, j, (j+1))){
					j++;
				}
				if(!less1(list, k, j)){
					break;
				}
			}
			if(precondition==1){
				if((j<n) && less2(list, j, (j+1))){
					j++;
				}
				if(!less2(list, k, j)){
					break;
				}
			}
			list = exch(list, k, j);
			k = j;
		}
		return list;
	}
	
	/**
	 * This is exch method which exchanges two elements of the parameter list.
	 * @param list
	 * @param i
	 * @param j
	 * @return list
	 */
	public static ArrayList<Text> exch(ArrayList<Text> list, int i, int j){
		Text temp = new Text();
		temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
		return list;
	}
	
	/**
	 * This less1 method which returns true if string of element at index i
	 * is less than string of element at index j.
	 * @param list
	 * @param i
	 * @param j
	 * @return
	 */
	public static boolean less1(ArrayList<Text> list, int i, int j){
		return list.get(i).compareTo1(list.get(j))<0;
	}
	
	/**
	 * This is less2 method which returns true if weight of element at index i
	 * is less than weight of element at index j.
	 * @param list
	 * @param i
	 * @param j
	 * @return
	 */
	public static boolean less2(ArrayList<Text> list, int i, int j){
		return list.get(i).compareTo2(list.get(j))<0;
	}
	
	/**
	 * This is binarySearch method which is searching matches in the parameter
	 * list for the search key. This method compares search key which contains N character 
	 * with first N character of string of elements in list.
	 * @param list
	 * @param searchKey
	 * @return
	 */
	public static ArrayList<Text> binarySearch(ArrayList<Text> list, String searchKey){
		ArrayList<Text> query = new ArrayList<Text>();
		String[] arr = searchKey.split("");
		int lo = 0, hi = list.size()-1;
		int condition = 0;
		do{
			while(lo<=hi){
				int mid = lo + (hi-lo)/2;
				String[] temp = list.get(mid).getString().split("");
				String array = "";
				for(int i=0; i<arr.length; i++){
					array += temp[i];
				}
				if(searchKey.compareTo(array)<0){
					hi = mid - 1;
					condition = 0;
				}
				else if(searchKey.compareTo(array)>0){
					lo = mid + 1;
					condition = 0;
				}
				else if(searchKey.equals(array)){
					query.add(list.get(mid));
					list.remove(mid);
					condition = 1;
				}
			}
		}while(condition==1);
		return query;
	}
	
	/**
	 * This is reverse method.
	 * This method is changing the order of an array form descending to ascending or from ascending to descending.
	 * @param list
	 * @param length
	 * @return
	 */
	public static ArrayList<Text> reverse(ArrayList<Text> list, int length){
		int i = length - 1;
		ArrayList<Text> temp = new ArrayList<Text>();
		while(i>=0){
			temp.add(list.get(i));
			i--;
		}
		return temp;
	}
}
