package test.com.leeloo.viv.work.usecase;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.leeloo.viv.work.Work;
import com.leeloo.viv.work.repository.NotificationRepo;
import com.leeloo.viv.work.repository.WorkRepo;
import com.leeloo.viv.work.usecase.UseCases;

public class UseCaseTests {
	
	static LocalServiceTestHelper localService =  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	UseCases useCases = new UseCases();
	
	@Before
	public void setUp() {
		localService.setUp();   
    }
	
	@After 
	public void tearDown() {
		localService.tearDown();
	}
	
	@Test
	public void UseCase_editWorkDetails_editsNameAndDescription() {
		
		String user ="me@goops.de";
		String imageUrl = "http://127.0.0.1:8080/_ah/img/UPhf0_exYUM5Ro83tC3vaw=s900";
		useCases.createWork(user , "Selfie", "me and my self", "123456AADD", imageUrl);
		
		String myWorkId = new WorkRepo().getFromUser(user).get(0).id;
		
		String newName = "new Name";		
		String newDescription = "new description";
		
		useCases.editWorkDetails(myWorkId, newName , newDescription );
		
		Work myEditedWork = new WorkRepo().getFromUser(user).get(0);
		
		assertEquals(newName, myEditedWork.name);
		assertEquals(newDescription, myEditedWork.description);
	}
	
	@Test
	public void UseCase_addCommentToWork_createsUnreadNotifications() {
		
		String user ="me@goops.de";
		useCases.createWork(user , "Selfie", "me and my self", "123456AADD", "http://127.0.0.1:8080/_ah/img/123456AADD");
		useCases.createWork(user , "Wall", "oh, it is a wall", "123456AADE", "http://127.0.0.1:8080/_ah/img/123456AADE");
		
		List<Work> works = new WorkRepo().getAll();		
		useCases.addCommentToWork(works.get(0).id, "Looks nice!", "i_am_leeloo@mail.com");
		useCases.addCommentToWork(works.get(1).id, "Awesome stuff!", "i_am_leeloo@mail.com");
		
		int nonReadNotifications = new NotificationRepo().getUserUnreadNotifications(user).size();
		
		assertEquals(2, nonReadNotifications);		
	}
	
	@Test
	public void UseCase_addCommentToWork_doesNotCreateNotificationIfItsOwnWork() {
		
		String user ="me@goops.de";
		useCases.createWork(user , "Selfie", "me and my self", "123456AADD", "http://127.0.0.1:8080/_ah/img/123456AADD");
		useCases.createWork(user , "Wall", "oh, it is a wall", "123456AADE", "http://127.0.0.1:8080/_ah/img/123456AADE");
		
		List<Work> works = new WorkRepo().getAll();		
		useCases.addCommentToWork(works.get(0).id, "I am commenting on my own work!", user);
		
		int nonReadNotifications = new NotificationRepo().getUserUnreadNotifications(user).size();
		
		assertEquals(0, nonReadNotifications);		
	}
	
	@Test
	public void UseCase_markAllNotificationsAsRead_marksThemAll() {
		
		String user ="me@goops.de";
		useCases.createWork(user , "Selfie", "me and my self", "123456AADD", "http://127.0.0.1:8080/_ah/img/123456AADD");
		useCases.createWork(user , "Wall", "oh, it is a wall", "123456AADE", "http://127.0.0.1:8080/_ah/img/123456AADE");
		
		List<Work> works = new WorkRepo().getAll();		
		useCases.addCommentToWork(works.get(0).id, "Looks nice!", "i_am_leeloo@mail.com");
		useCases.addCommentToWork(works.get(1).id, "Awesome stuff!", "i_am_leeloo@mail.com");
		
		useCases.markAllNotificationsAsRead(user);
		
		int nonReadNotifications = new NotificationRepo().getUserUnreadNotifications(user).size();
		
		assertEquals(0, nonReadNotifications);
		
	}

}
