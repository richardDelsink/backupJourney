package domain;

public class Link {
    private String link;
    private String rel;
    private String type;

    public Link() {
    }

    public Link(String link, String rel, String type) {
        this.rel = rel;
        this.link = link;
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
