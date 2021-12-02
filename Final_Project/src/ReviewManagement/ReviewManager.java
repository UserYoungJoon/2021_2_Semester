package ReviewManagement;
import java.util.ArrayList;
import java.util.Scanner;

import Items.Item;
import ProcessManagement.Managers;



public class ReviewManager {

	public ReviewManager() {
	}
	private ArrayList<Review> reviewList;
	private Scanner scan;
		
	public void init(Scanner scan) {
		this.scan = scan;
	}
	
	public Review find(String id, String name) {
		for (Review m: reviewList) {
			if(m.match(id, name))
				return m;
		}
		return null;
	}
	
	public ArrayList<Review> findByName(String itemName) {
		ArrayList<Review> findRevList = new ArrayList<>();
		for(Review rev : Managers.managedList.reviewList) {
			if(rev.matchName(itemName))
				findRevList.add(rev);
		}
		return findRevList;
	}
	
	public void writeRev(String id, String name, float grade, String review) {
		Review exist = find(id, name);
		if(exist != null) {
	    	System.out.println("이미 리뷰를 남기셨습니다.");
		}
		else if(exist == null) {
			Review newReview = new Review();
			newReview.userId = id;
			newReview.itemName = name;
			newReview.grade = grade;
			newReview.content = review;
	        reviewList.add(newReview);
	        
	        for(Item i1 : Managers.managedList.itemList) { // 12/2 김정효
	        	if(i1.match(name)) {
	        		i1.setGrade(grade);
	        		break;
	        	}
	        }
		}
	}
}