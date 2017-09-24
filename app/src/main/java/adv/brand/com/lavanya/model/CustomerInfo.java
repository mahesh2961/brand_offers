package adv.brand.com.lavanya.model;

import java.util.List;

/**
 * Created by Mahesh on 24-09-2017.
 */

public class CustomerInfo {

    List<String> images;
    String about;
    String phn;

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
