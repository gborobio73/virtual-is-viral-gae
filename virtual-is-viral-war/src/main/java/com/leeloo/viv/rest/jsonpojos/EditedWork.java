package com.leeloo.viv.rest.jsonpojos;

public class EditedWork {
	public String workId;
    public String title; 
    public String description; 

    public EditedWork() {
    }

    public EditedWork(String workId, String title, String description) {
        this.workId = workId;
        this.title = title;
        this.description = description;
    }
}
