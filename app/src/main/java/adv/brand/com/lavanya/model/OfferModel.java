package adv.brand.com.lavanya.model;

/**
 * Created by maheshb on 22/9/17.
 */

public class OfferModel {
    String title;
    String desc;
    String redirect;
    String imgUrl;
    String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
