package com.leeloo.viv.work.usecase;

import com.leeloo.viv.work.Notification;
import com.leeloo.viv.work.Work;
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.NotificationFactory;
import com.leeloo.viv.work.repository.NotificationRepo;
import com.leeloo.viv.work.repository.WorkFactory;
import com.leeloo.viv.work.repository.WorkRepo;

public class UseCases{
	private WorkRepo repo = new WorkRepo();
	private NotificationRepo notiRepo = new NotificationRepo();

	public void createWork(String user, String title, String description, String imageId, String imageUrl){
		Work work = new WorkFactory(new IdGenerator()).createWork(user, title, description, imageId, imageUrl);
    	repo.save(work);
	}
	
	public void addCommentToWork(String workId, String commentText, String user){	  
	  Work work = repo.get(workId);
	  
	  work.addComment(user, commentText);	  
	  repo.save(work);
	  
	  String notification = user + " has commented your work " + work.name;
	  Notification noti = new NotificationFactory(new IdGenerator()).createNotification(work.user, workId, notification);	  
	  notiRepo.save(noti);
	  
	}

	public void deleteCommentFromWork(String workId, String commentId,String userWhosDeleting){
		Work work = repo.get(workId);
		
		work.deleteComment(commentId, userWhosDeleting);
		
		repo.save(work);
	}
	
	public void editWorkDetails(String workId, String name, String description){
		Work work = repo.get(workId);
		
		work.setDetails(name, description);
		
		repo.save(work);
	}
}
