package UserManagement;
import java.util.ArrayList;
import java.util.Scanner;

import ProcessManagement.Managers;


public class UserManager {
	public UserManager() {	
	}
	private Scanner scan;
	
	public void init(Scanner scan) {
		this.scan = scan;
	}
	
	public String login(String id, String pw) {
		User user = find(id);
		
		if(user == null || !user.pw.equals(pw)) {
	    	System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
	    	return null;
		}
		return user.userId;
	}
	
	public User withDrawal(User user) {
		System.out.println("탈퇴하시겠습니까? (y/n) : ");
		String ans = scan.next();
		if(!ans.equals("y")) return user;
		
		for(int i=0; i< Managers.managedList.userList.size() ;i++){
            User m = Managers.managedList.userList.get(i);
	        if(m.equals(user)) {
	        	Managers.managedList.userList.remove(i);
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
	
	public int signUp(String id, String pw) {
		User user = find(id);
		User newUser = new User();
		if(user != null) {
	    	System.out.println("동일한 아이디가 존재합니다.\n");
	    	return 0;
		}
		else if(user == null) {
			newUser.userId = id;
			newUser.pw = pw;
			Managers.managedList.userList.add(newUser);
	        System.out.printf("환영합니다 %s님\n", id);
	        return 1;
		}
		return 0;
	}
	
	public User find(String kwd) {
		for (User m: Managers.managedList.userList) {
			if(m.match(kwd))
				return m;
		}
		return null;
	}
}
