package Items;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Item {//Our Manageable
	public abstract void read(Scanner scan);
	public abstract void setData(Scanner scan);
	public abstract boolean match(String kwd);
	public abstract String getType();
	protected String name;
	protected int time;
	protected float grade;
	protected int rating;//관람등급
	protected float real_grade; //12/2 김정효
	protected int grade_num; //12/2 김정효
	protected ArrayList<Category> category = new ArrayList<Category>();
	protected String imagePath;
	protected String summary;
	
	public void modify(Scanner scan) {
		System.out.println("Enter what you want to modify.");
		System.out.printf("Name: %s->", name);
		scan.nextLine();
		String temp = scan.nextLine();
		if(temp.length()>0)
			name = temp;
		
		System.out.printf("Time: %d->", time);
		temp = scan.next();
		if(temp.length()>0)
			time = Integer.parseInt(temp);
		
		while(true) {
			System.out.printf("Grade: %.1f->", grade);
			temp = scan.next();
			if(temp.length()>0) {
			
				if(Float.parseFloat(temp)>5.0){
				System.out.println("Enter again.");
					continue;
				}
				else {
					grade = Float.parseFloat(temp);
						break;
						}
				}
		}
		
		System.out.printf("Rating: %d->", rating);
		temp = scan.next();
		if(temp.length()>0)
			rating = Integer.parseInt(temp);
		
		System.out.printf("Summary: %s->", summary);
		scan.nextLine();
		temp = scan.nextLine();
		if(temp.length()>0)
			summary = temp;
	}
	
	public void print() {
		System.out.printf("%-20s	| %d | %.1f | %d | ", name,time,grade,rating);
		for(int i=0;i<category.size();i++){
			if(i==(category.size()-1)) {
				System.out.printf("%s",category.get(i).name());	
			}
			else {
				System.out.printf("%s,",category.get(i).name());	
			}
		}
		System.out.printf("| %s |",summary);	
	}
	
	public enum Category {
		THRILLER, ACTION, ROMANCE, SF, COMEDY, HORROR, FANTASY, DOCUMENTARY, LIFESTYLE,VARIETY_SHOW, TRAVEL
	}
	public Category stringToCategory(String kwd) {
		
		return Category.valueOf(kwd);
	}

	public boolean hasCategory(Category type) {
		for(Category c: category) {
			if(c.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Category> getCategory(){
		return category;
	}
	
	public String getName() {
		return name;	
	}
	public int getTime() {
		return time;	
	}
	public float getGrade() {
		return grade;	
	}	 
	public int getRating(){ 
		return rating;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public String getSummary() {
		return summary;
	}
	public String getCategories(){
		String res="";
		for(int i=0;i<category.size();i++) {
			if(i==(category.size()-1))
				res += category.get(i).name();
			else
				res += category.get(i).name()+",";
		}		
		return res; 
	}
	public void setGrade(float grade) { // 12/2 김정효
		if(grade_num>=1) grade_num++;
		else grade_num=1;
		
		if(grade_num == 1) real_grade = grade;
		else real_grade = (real_grade*(grade_num-1) + grade)/grade_num;
	}
}
