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
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.NotificationFactory;
import com.leeloo.viv.work.repository.NotificationRepo;

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
		String toWhom = "gborobio@gmail.com";
		String workId = "213drw423";
		String text = "Leeloo commented on your work.";
		String anotherWorkId = "9877wsddsi98779";
		String anotherText = "Leeloo commented on your work.";
		
		Notification notification = factory.createNotification(toWhom, workId , text);
		repo.save(notification);
		notification = factory.createNotification(toWhom, anotherWorkId , anotherText);
		repo.save(notification);
		
		List<Notification> notis = repo.getUserNotifications(toWhom);
		
		assertEquals(notis.size(), 2);
	}
	
	@Test
	public void NotificationRepo_markAsRead_marksNotificationAsRead() {
		String toWhom = "anotherUser@gmail.com";
		String workId = "6767wqhkakas987";
		String text = "Leeloo commented on your work.";
		
		Notification notification = factory.createNotification(toWhom, workId , text);
		repo.save(notification);
		
		notification.markAsRead();		
		repo.save(notification);
		
		Notification readNotification = repo.getUserNotifications(toWhom).get(0);		
		assertTrue(readNotification.isRead());
	}

	@Test
	public void NotificationRepo_getUserUnreadNotifications_getsAllUserUnreadNotifications() {
		String toWhom = "yetAnothe.user@gmail.com";
		String workId = "213drw423";
		String text = "Leeloo commented on your work.";
		String anotherWorkId = "9877wsddsi98779";
		String anotherText = "Leeloo commented on your work.";
		
		Notification notification = factory.createNotification(toWhom, workId , text);
		repo.save(notification);
		notification = factory.createNotification(toWhom, anotherWorkId , anotherText);
		notification.markAsRead();
		repo.save(notification);
		
		List<Notification> notis = repo.getUserUnreadNotifications(toWhom);
		assertEquals(notis.size(), 1);
		assertEquals(notis.get(0).workId, workId);
	}
	
}
