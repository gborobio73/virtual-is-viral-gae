package test.com.leeloo.viv.work;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.leeloo.viv.work.Work;
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.WorkFactory;

public class WorkTests {
	WorkFactory workFactory = new WorkFactory(new IdGenerator());
	
	@Test
	public void Work_addComment_addwithIncrementalId() {
		String name = "Title 1";
		String user = "test@dom.com";		
		String description ="My masterpiece";
		String imageId = "ashdkdh7878asdjk";
		String imageUrl = "http://127.0.0.1:8080/_ah/img/UPhf0_exYUM5Ro83tC3vaw=s900";
		
		Work work = workFactory.createWork(user , name , description, imageId, imageUrl);
		
		work.addComment(user, "some text");
		work.addComment(user, "some more text");
		work.addComment(user, "yet more text");
		work.deleteComment("2", user);		
		work.addComment(user, "another text!");
		int size = work.comments.size();
		assertEquals(work.comments.get(size-1).id, "4");		
	}
}
