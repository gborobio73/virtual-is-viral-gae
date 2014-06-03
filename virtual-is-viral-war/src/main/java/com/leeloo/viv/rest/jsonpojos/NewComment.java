package com.leeloo.viv.rest.jsonpojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewComment {
    public String workId;
    public String commentText;    

    public NewComment() {
    }

    public NewComment(String workId, String commentText) {
        this.workId = workId;
        this.commentText = commentText;
    }

    public String getWorkId() {
        return workId;
    }

    public String getCommentText() {
        return commentText;
    }

}