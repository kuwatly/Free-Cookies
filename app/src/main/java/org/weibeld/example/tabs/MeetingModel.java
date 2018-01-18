package org.weibeld.example.tabs;

import java.util.List;

public class MeetingModel {
    private String meeting_id;
    private List<PostModel> posts;

    public MeetingModel(String meeting_id, List<PostModel> posts) {
        this.meeting_id = meeting_id;
        this.posts = posts;
    }

    public MeetingModel() {

    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "MeetingModel{" +
                "meeting_id='" + meeting_id + '\'' +
                ", posts=" + posts +
                '}';
    }
}
