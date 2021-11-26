package Items;

import java.util.ArrayList;
import java.util.Scanner;

public class Entertainment extends Item {
	String channel;
	ArrayList<String> castmates = new ArrayList<String>();

	public String getType() {
		return "Entertainment";
	}

	@Override
	public void read(Scanner scan) {
		name = scan.next();
		String[] categorys = scan.next().split(",");
		for (String str : categorys) {
			category.add(stringToCategory(str));
		}
		time = scan.nextInt();
		grade = scan.nextFloat();
		rating = scan.nextInt();
		channel = scan.next();
		String[] castmate = scan.next().split(",");
		for (String str : castmate) {
			castmates.add(str);
		}
		imagePath = "./images"+scan.next();
		summary = scan.nextLine();
	}

	@Override
	public boolean match(String kwd) {
		if (name.contains(kwd)) {
			return true;
		}
		if (channel.contains(kwd)) {
			return true;
		}
		if (kwd.equals("" + time)) {
			return true;
		}
		if (kwd.equals("" + rating)) {
			return true;
		}
		for (String cast : castmates) {
			if (cast.contains(kwd)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void print() {
		super.print();
		System.out.printf(" %s |", channel);
		for (int i = 0; i < castmates.size(); i++) {
			if (i == (castmates.size() - 1)) {
				System.out.printf("%s", castmates.get(i));
			} else {
				System.out.printf("%s,", castmates.get(i));
			}
		}
		System.out.println();
	}

	@Override
	public void setData(Scanner scan) {
		System.out.println("Enter");
		name = scan.next();
		String[] categorys = scan.next().split(",");
		for (String str : categorys) {
			category.add(stringToCategory(str));
		}
		time = scan.nextInt();
		grade = scan.nextFloat();
		rating = scan.nextInt();
		channel = scan.next();
		String[] castmate = scan.next().split(",");
		for (String str : castmate) {
			castmates.add(str);
		}
		summary = scan.nextLine();
	}
}
