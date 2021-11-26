package ReviewManagement;
import java.util.Scanner;

public class Review{
	public String userId;
	public String itemName;
	public float grade;
	public String content;
	

	public void read(Scanner scan) {
		userId = scan.next();
		itemName = scan.next();
		grade = scan.nextFloat();
		content = scan.nextLine();
	}

	public void print() {
		System.out.printf("%s - %s : [%.1f] %s\n", userId, itemName, grade, content);
	}
	public boolean match(String id, String name) {
		if (userId.equals(id)) {
			if(name.equals(name)) {
				return true;
			}
		}
		return false;
	}
}