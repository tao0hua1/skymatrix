package cn.seiua.skymatrix.client;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Notice {

    private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private String title;

    private NoticeType type;

    private String message;

    private long startTime;

    private String date;

    private boolean render;

    public Notice(String title, String message, NoticeType type) {
        startTime = System.currentTimeMillis();
        this.title = title;
        this.type = type;
        this.message = message;
        render = true;
        date = dateformat.format(new Date(startTime));
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean canRender() {
        return render;
    }

    public float getPercentage(long currentTime) {

        long t = 1800;
        //400 1200 400

        long c = currentTime - startTime;

        if (c <= 300) {
            return c * 1.0f / 300;
        }
        if (c > 300 && c < 1600) {
            return 1.0f;
        }
        if (c >= 1600 && c <= t) {
            return 1 - ((c - 1600) * 1.0f / 200);
        }
        render = false;

        return 99999999;
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
        this.message = message;
    }
}
