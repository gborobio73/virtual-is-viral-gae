package com.leeloo.viv.rest;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.leeloo.viv.repository.CommentFactory;
import com.leeloo.viv.repository.WorkFactory;
import com.leeloo.viv.repository.WorkRepo;

public class WorkRepositoryTests {

	static LocalServiceTestHelper helper =  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
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
		
		String name = "Title 1";
		String user = "test@dom.com";		
		String description ="My masterpiece";
		String imageId = "ashdkdh7878asdjk";
		
		Work work = new WorkFactory().createWork(user , name , description, imageId);
		
		repo.save(work);
		
		Work savedWork = repo.get(work.id);
		
		assertEquals(work.id, savedWork.id);
		assertEquals(work.user, savedWork.user);
		assertEquals(work.description, savedWork.description);
		assertEquals(work.imageId, savedWork.imageId);		
		
	}
	
	@Test
	public void WorkRepo_save_savesWorkWithCommentsRespectingOrder() {
		WorkRepo repo = new WorkRepo();
		
		String name = "Title 1";
		String user = "test@dom.com";		
		String description ="My masterpiece";
		String imageId = "ashdkdh7878asdjk";
		
		Work work = new WorkFactory().createWork(user , name , description, imageId);
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
		
		String name = "Title 1";
		String user = "test@dom.com";		
		String description ="My masterpiece";
		String imageId = "ashdkdh7878asdjk";
		
		Work work = new WorkFactory().createWork(user , name , description, imageId);
		String workId = work.id;
		
		String commentUser = "user@g.de";
		String commentText = "Hey, that's an awesome stuff";
		
		work.addComment("a@user.com", "I'm the first one commenting");
		work.addComment(commentUser, commentText);
		work.addComment("another@user.com", "I'm next!");		
		
		repo.save(work);
		
		Work workWithComments = repo.get(workId);
		
		String commentIdToDelete = "2";
		workWithComments.deleteComment(commentIdToDelete);
		
		repo.save(workWithComments);
		
		Work workWithDeletedComments = repo.get(workId);
		
		assertEquals(workWithDeletedComments.comments.size(), 2);
		assertEquals(workWithDeletedComments.comments.get(1).user, "another@user.com");
		assertEquals(workWithDeletedComments.comments.get(1).comment, "I'm next!");
	}
	
}
