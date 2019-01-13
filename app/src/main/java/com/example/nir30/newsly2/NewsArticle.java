package com.example.nir30.newsly2;

public class NewsArticle {
    private String autor;
    private String title;
    private String description;
    private String url;
    private String imgUrl;
    private String publishedAt;
    private String content;

    public NewsArticle(String autor, String title, String description, String url, String imgUrl, String publishedAt, String content) {
        setAutor(autor);
        this.title = title;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
        setPublishedAt(publishedAt);
        setContent(content);
    }

    public NewsArticle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor =="null"){
            this.autor = "Website Team";
        }
        else {
            this.autor = autor;

        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt.substring(0,10);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(content.length() > 14) {
            this.content = content.substring(0, content.length() - 13);
        }
        else {
            this.content = content;
        }
    }
}