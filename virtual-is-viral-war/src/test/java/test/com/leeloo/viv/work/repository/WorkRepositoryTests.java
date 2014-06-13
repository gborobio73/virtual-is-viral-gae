package test.com.leeloo.viv.work.repository;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.leeloo.viv.work.Work;
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.WorkFactory;
import com.leeloo.viv.work.repository.WorkRepo;

public class WorkRepositoryTests {

	static LocalServiceTestHelper helper =  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	WorkFactory workFactory = new WorkFactory(new IdGenerator());
	
	String name = "Title 1";
	String user = "test@dom.com";		
	String description ="My masterpiece";
	String imageId = "ashdkdh7878asdjk";
	String imageUrl = "http://127.0.0.1:8080/_ah/img/UPhf0_exYUM5Ro83tC3vaw=s900";
	
	@BeforeClass 
	public static void setUp() {
		helper.setUp();   
    }
	
	@AfterClass 
	public static void tearDown() {
		helper.tearDown();
	}
	@Test
	public void WorkRepo_save_savesWork() {
		WorkRepo repo = new WorkRepo();
		Work work = workFactory.createWork(user , name , description, imageId, imageUrl);
		
		repo.save(work);
		
		Work savedWork = repo.get(work.id);
		
		assertEquals(work.id, savedWork.id);
		assertEquals(work.user, savedWork.user);
		assertEquals(work.description, savedWork.description);
		assertEquals(work.imageId, savedWork.imageId);
		assertEquals(work.imageUrl, savedWork.imageUrl);	
		
	}
	
	@Test
	public void WorkRepo_save_savesWorkWithCommentsRespectingOrder() {
		WorkRepo repo = new WorkRepo();
		
		Work work = workFactory.createWork(user , name , description, imageId, imageUrl);
		String commentUser = "user@g.de";
		String commentText = "Hey, that's an awesome stuff";
		
		work.addComment("a@user.com", "I'm the first one commenting");
		work.addComment(commentUser, commentText);
		work.addComment("another@user.com", "I'm next!");		
		
		repo.save(work);
		
		Work savedWork = repo.get(work.id);
		
		assertEquals(work.comments.size(), savedWork.comments.size());
		assertEquals(work.comments.get(1).user, commentUser);
		assertEquals(work.comments.get(1).comment, commentText);
	}

	@Test
	public void WorkRepo_save_savesWorkWithDeletedComment() {
		WorkRepo repo = new WorkRepo();
		
		Work work = workFactory.createWork(user , name , description, imageId, imageUrl);
		String workId = work.id;
		
		String commentUser = "user@g.de";
		String commentText = "Hey, that's an awesome stuff";
		
		work.addComment("a@user.com", "I'm the first one commenting");
		work.addComment(commentUser, commentText);
		work.addComment("another@user.com", "I'm next!");		
		
		repo.save(work);
		
		Work workWithComments = repo.get(workId);
		
		String commentIdToDelete = "2";
		workWithComments.deleteComment(commentIdToDelete, commentUser);
		
		repo.save(workWithComments);
		
		Work workWithDeletedComments = repo.get(workId);
		
		assertEquals(workWithDeletedComments.comments.size(), 2);
		assertEquals(workWithDeletedComments.comments.get(1).user, "another@user.com");
		assertEquals(workWithDeletedComments.comments.get(1).comment, "I'm next!");
	}
	
	@Test
	public void WorkRepo_save_savesWorkCreatedDate() throws InterruptedException {
		WorkRepo repo = new WorkRepo();
		
		Work work = workFactory.createWork(user , name , description, imageId, imageUrl);
		String workId = work.id;
		
		repo.save(work);
		
		int oneSecond = 1000;
		Thread.sleep(oneSecond);
		
		Work savedWork = repo.get(workId);
		assertEquals(savedWork.created, work.created);		
	}
	
}
