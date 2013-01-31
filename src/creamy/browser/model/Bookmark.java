package creamy.browser.model;

import creamy.browser.db.Entity;

public class Bookmark extends Entity {
    private String title;
    private String path;

    public Bookmark() {}
    public Bookmark(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
