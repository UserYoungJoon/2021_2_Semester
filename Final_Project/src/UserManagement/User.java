package UserManagement;

import java.util.ArrayList;
import java.util.Scanner;

import Items.Item;
import ProcessManagement.Managers;

public class User {
	public String userId;
	public String pw;
	public ArrayList<String> interestedItems = new ArrayList<String>();

	public void read(Scanner scan) {
		userId = scan.next();
		pw = scan.next();
		String tmp = scan.next();
		String[] items = tmp.split(",");
		if (items[0].equals("0")) {
			return;
		}
		for (String str : items) {

			interestedItems.add(str);
		}
	}

	public void print() {
		System.out.printf("[%s] (%s)\n", userId, pw);
	}

	public boolean match(String kwd) {
		if (userId.equals(kwd)) {
			return true;
		}
		return false;
	}
}