package test.com.leeloo.viv.work.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.leeloo.viv.work.Notification;
import com.leeloo.viv.work.Work;
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.NotificationFactory;
import com.leeloo.viv.work.repository.NotificationRepo;
import com.leeloo.viv.work.repository.WorkFactory;

public class NotificationRepositoryTests {
	static LocalServiceTestHelper helper =  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	static NotificationRepo repo = new NotificationRepo();
	static NotificationFactory factory = new NotificationFactory(new IdGenerator());
	
	@BeforeClass 
	public static void setUp() {
		helper.setUp();   
    }
	
	@AfterClass 
	public static void tearDown() {
		helper.tearDown();
	}
	
	@Test
	public void NotificationRepo_getUserNotifications_getsAllUserNotifications() {
		
		String user = "gborobio@gmail.com";
		String commentingUser = "leeloo@gmail.com";
		
		Work work = new WorkFactory(new IdGenerator()).createWork(user, "TBD", "A work", "", "");
		
		Notification notification = factory.createNotification(commentingUser, work);
		repo.save(notification);
		notification = factory.createNotification(commentingUser, work);
		repo.save(notification);
		
		List<Notification> notis = repo.getUserNotifications(user);
		
		assertEquals(notis.size(), 2);
	}
	
	@Test
	public void NotificationRepo_markAsRead_marksNotificationAsRead() {
		String user = "anotherUser@gmail.com";				
		Work work = new WorkFactory(new IdGenerator()).createWork(user, "TBD", "A work", "", "");
		
		String commentingUser = "leeloo@gmail.com";
		Notification notification = factory.createNotification(commentingUser, work);
		repo.save(notification);
		
		notification.markAsRead();		
		repo.save(notification);
		
		Notification readNotification = repo.getUserNotifications(user).get(0);		
		assertTrue(readNotification.isRead());
	}

	@Test
	public void NotificationRepo_getUserUnreadNotifications_getsAllUserUnreadNotifications() {
		String user = "yetAnothe.user@gmail.com";
		Work work = new WorkFactory(new IdGenerator()).createWork(user, "TBD", "A work", "", "");
		
		String commentingUser = "leeloo@gmail.com";
		
		Notification notification = factory.createNotification(commentingUser, work);
		repo.save(notification);
		notification = factory.createNotification(commentingUser, work);
		notification.markAsRead();
		repo.save(notification);
		
		List<Notification> notis = repo.getUserUnreadNotifications(user);
		assertEquals(notis.size(), 1);
		assertEquals(notis.get(0).workId, work.id);
	}
	
}
