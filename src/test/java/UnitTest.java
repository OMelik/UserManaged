import java.util.List;

import com.infonal.dao.UserDAO;
import com.infonal.model.User;



public class UnitTest {
	
	public static void main(String [ ] args) {
		
		UserDAO userDAO = new UserDAO();
		
		/*User user = new User();
		user.setId("1");
		user.setName("Osman");
		user.setSurname("Oluroğulları");
		user.setPhone("05412012081");*/
		
		List<User> users = userDAO.getAllUsers();
		for (User user : users) {
			System.out.println(user.getName());
		}
		
	}
	
}
