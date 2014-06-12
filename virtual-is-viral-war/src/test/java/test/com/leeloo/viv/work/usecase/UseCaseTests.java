package test.com.leeloo.viv.work.usecase;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.leeloo.viv.work.Work;
import com.leeloo.viv.work.repository.WorkRepo;
import com.leeloo.viv.work.usecase.UseCases;

public class UseCaseTests {
	
	static LocalServiceTestHelper localService =  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	UseCases useCases = new UseCases();
	
	@BeforeClass 
	public static void setUp() {
		localService.setUp();   
    }
	
	@AfterClass 
	public static void tearDown() {
		localService.tearDown();
	}
	
	@Test
	public void UseCase_editWorkDetails_editsNameAndDescription() {
		
		String user ="me@goops.de";
		useCases.createWork(user , "Selfie", "me and my self", "123456AADD");
		
		String myWorkId = new WorkRepo().getFromUser(user).get(0).id;
		
		String newName = "new Name";		
		String newDescription = "new description";
		
		useCases.editWorkDetails(myWorkId, newName , newDescription );
		
		Work myEditedWork = new WorkRepo().getFromUser(user).get(0);
		
		assertEquals(newName, myEditedWork.name);
		assertEquals(newDescription, myEditedWork.description);
	}

}
