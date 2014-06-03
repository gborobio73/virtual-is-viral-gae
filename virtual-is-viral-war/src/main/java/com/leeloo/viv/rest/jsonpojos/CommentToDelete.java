package com.leeloo.viv.rest.jsonpojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommentToDelete {
    public String workId;
    public String commentId;    

    public CommentToDelete() {
    }

    public CommentToDelete(String workId, String commentId) {
        this.workId = workId;
        this.commentId = commentId;
    }

    public String getWorkId() {
        return workId;
    }

    public String getCommentText() {
        return commentId;
    }

}