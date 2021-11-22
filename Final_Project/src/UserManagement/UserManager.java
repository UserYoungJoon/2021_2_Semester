package UserManagement;
import java.util.ArrayList;
import java.util.Scanner;


public class UserManager {
	public UserManager(ArrayList<User> userList, Scanner scan) {
		this.userList = userList;
		this.scan = scan;
	}
	private ArrayList<User> userList;
	private Scanner scan;
	
	public User login() {
		System.out.print("user id: ");
		String id = scan.next();
		System.out.print("password: ");
		String pw = scan.next();
		User user = find(id);
		if(user == null || !user.pw.equals(pw)) {
	    	System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
	    	return null;
		}
		return user;
	}
	
	public User withDrawal(User user) {
		System.out.println("탈퇴하시겠습니까?");
		String ans = scan.next();
		if(!ans.equals("y")) return user;
		
		for(int i=0; i< userList.size() ;i++){
            User m = userList.get(i);
	        if(m.equals(user)) {
	            userList.remove(i);
	            return null;
	        }
        }
		return user;
	}

	public void editUsr(User user) {
		System.out.printf("기존 암호: ");
		String temp = scan.next();
		if (!user.pw.equals(temp)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

		System.out.printf("새암호(변경없음 엔터):");
		temp = scan.next();
		if (temp.length() > 0)
			user.pw = temp;		
	}
	
	public void signUp() {
		System.out.print("user id: ");
		String id = scan.next();
		System.out.print("password: ");
		String pw = scan.next();
		User user = find(id);
		User newUser = new User();
		if(user != null) {
	    	System.out.println("동일한 아이디가 존재합니다.\n");
		}
		else if(pw == null) {
			newUser.userId = id;
			newUser.pw = pw;
	        userList.add(newUser);
	        System.out.printf("환영합니다 %s님\n", id);
		}
	}
	
	public User find(String kwd) {
		for (User m: userList) {
			if(m.match(kwd))
				return m;
		}
		return null;
	}
}
