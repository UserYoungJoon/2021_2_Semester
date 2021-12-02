package ProcessManagement;

import FileSystem.FileManager;
import ReviewManagement.ReviewManager;
import Service.ServiceManager;
import Service.SortingManager;
import UserManagement.User;
import UserManagement.UserManager;
import Service.ManagedList;
import Service.MenuManager;
import Service.RecommendManager;

//Global singleton managers
public class Managers {
	public static ServiceManager serviceManager = new ServiceManager(); 
	public static FileManager fileManager = new FileManager();	
	public static MenuManager menuManager = new MenuManager();
	public static ManagedList managedList = new ManagedList();
	public static UserManager userManager = new UserManager();
	public static SortingManager sortingManager = new SortingManager();
	public static RecommendManager recommendManager = new RecommendManager();
	public static ReviewManager reviewManager= new ReviewManager();
	public static User nowUser = new User();
}
