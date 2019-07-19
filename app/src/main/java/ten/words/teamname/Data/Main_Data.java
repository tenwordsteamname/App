package ten.words.teamname.Data;

public class Main_Data {
    String title,date,location;
    int agree, disagree;
    boolean like;
    String img,content;

    public Main_Data(String title, String date, String location, int agree, int disagree,boolean like,String img,String content) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.agree = agree;
        this.disagree = disagree;
        this.like = like;
        this.img=img;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Main_Data(boolean like) {
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getDisagree() {
        return disagree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }
}
