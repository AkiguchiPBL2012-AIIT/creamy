package creamy.browser.model;

import creamy.browser.db.Entity;

/**
 * Browserのブックマーク。Entityを継承しているため保存可能。
 * 
 * @author miyabetaiji
 */
public class Bookmark extends Entity {
    /**
     * ブックマークのタイトル
     */
    private String title;
    /**
     * ブックマークのパス
     */
    private String path;

    public Bookmark() {}
    public Bookmark(String title, String path) {
        this.title = title;
        this.path = path;
    }
    /**
     * タイトルを取得する
     * @return タイトル
     */
    public String getTitle() { return title; }
    /**
     * タイトルを設定する
     * @param title タイトル
     */
    public void setTitle(String title) { this.title = title; }
    /**
     * パスを取得する
     * @return パス
     */
    public String getPath() { return path; }
    /**
     * パスを設定する
     * @param path パス 
     */
    public void setPath(String path) { this.path = path; }
}
