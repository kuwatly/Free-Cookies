package org.weibeld.example.tabs;

public class PostModel {
    private String post_id;
    private String name;
    private String message;
    private String type;
    private String timestamp;
    private String checked;

    public PostModel(String post_id, String name, String message, String type, String timestamp, String checked) {
        this.post_id = post_id;
        this.name = name;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
        this.checked = checked;
    }

    public PostModel() {

    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "post_id='" + post_id + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", checked='" + checked + '\'' +
                '}';
    }
}
