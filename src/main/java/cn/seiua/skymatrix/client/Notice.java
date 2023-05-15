package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.utils.AnimationTransition;

public class Notice {

    private String title;

    private NoticeType type;

    private String message;

    private AnimationTransition transition;

    public Notice(String title, NoticeType type, String message) {
        this.transition = new AnimationTransition();
        this.title = title;
        this.type = type;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NoticeType getType() {
        return type;
    }

    public void setType(NoticeType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.transition = new AnimationTransition();
        this.message = message;
    }
}
