package com.lauzy.freedom.dailypaper.model;

import java.util.List;

/**知乎首页实体类
 * Created by Lauzy on 2016/11/3.
 */

public class ZHhomePageBean {

    /**
     * date : 20161103
     * stories : [{"images":["http://pic4.zhimg.com/eed92b061bf3f0f1b783f9ce9aee381f.jpg"],"type":0,"id":8942200,"ga_prefix":"110310","title":"要想避免「江郎才尽」没钱赚，网红该怎么办？"},{"images":["http://pic4.zhimg.com/1d552b04709121d530b3c6a5ae01d25b.jpg"],"type":0,"id":8943946,"ga_prefix":"110309","title":"反正不管做没做，人都是会后悔的"},{"images":["http://pic2.zhimg.com/430d9fd7e08c75e0bc2073bd9350eb0d.jpg"],"type":0,"id":8943519,"ga_prefix":"110308","title":"我们关系这么亲密，还需要说「谢谢」吗？"},{"images":["http://pic2.zhimg.com/d2d38b488cf78c00b32295c74227c809.jpg"],"type":0,"id":8944218,"ga_prefix":"110307","title":"解释概念不复杂，可实际识别「经济泡沫」真的很难"},{"images":["http://pic2.zhimg.com/5ee5e340e00e87a37c740e4d46efbaa1.jpg"],"type":0,"id":8940275,"ga_prefix":"110307","title":"有哪些关于糖尿病的谣言？"},{"images":["http://pic3.zhimg.com/8b6f66da4b7d104562e9d09a2dd2f922.jpg"],"type":0,"id":8943243,"ga_prefix":"110307","title":"又出煤矿事故，这是业内人士眼里的煤矿危险与安全"},{"images":["http://pic2.zhimg.com/8146ea6c9de2e3aa1dd242b370ddd02d.jpg"],"type":0,"id":8944048,"ga_prefix":"110307","title":"读读日报 24 小时热门 TOP 5 · 粉了周星驰二十年"},{"images":["http://pic3.zhimg.com/a549c6ea6c85d186a04ba0053c69fce2.jpg"],"type":0,"id":8941966,"ga_prefix":"110306","title":"瞎扯 · 如何正确地吐槽"},{"images":["http://pic4.zhimg.com/4f15f1780b767638fe8155bbe543a5bb.jpg"],"type":0,"id":8942755,"ga_prefix":"110306","title":"这里是广告 · 斜杠青年的进阶，是斜杠中年还是高级玩家？"}]
     * top_stories : [{"image":"http://pic4.zhimg.com/1d6015d8fed1c13645d7915ff238273b.jpg","type":0,"id":8942200,"ga_prefix":"110310","title":"要想避免「江郎才尽」没钱赚，网红该怎么办？"},{"image":"http://pic1.zhimg.com/e7975e7d8c2635887b7f030169460c74.jpg","type":0,"id":8944048,"ga_prefix":"110307","title":"读读日报 24 小时热门 TOP 5 · 粉了周星驰二十年"},{"image":"http://pic3.zhimg.com/981daafb3eafc402a1e272e061934e7e.jpg","type":0,"id":8943243,"ga_prefix":"110307","title":"又出煤矿事故，这是业内人士眼里的煤矿危险与安全"},{"image":"http://pic1.zhimg.com/6307c391adb4ef6e7ba8c46aff706aa0.jpg","type":0,"id":8942765,"ga_prefix":"110217","title":"知乎好问题 · 怎样做正宗的水煮鱼？有哪些技巧？"},{"image":"http://pic3.zhimg.com/7766a061e3374ca2f498f6ed589e105e.jpg","type":0,"id":8942755,"ga_prefix":"110306","title":"这里是广告 · 斜杠青年的进阶，是斜杠中年还是高级玩家？"}]
     */

    private String date;
    /**
     * images : ["http://pic4.zhimg.com/eed92b061bf3f0f1b783f9ce9aee381f.jpg"]
     * type : 0
     * id : 8942200
     * ga_prefix : 110310
     * title : 要想避免「江郎才尽」没钱赚，网红该怎么办？
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic4.zhimg.com/1d6015d8fed1c13645d7915ff238273b.jpg
     * type : 0
     * id : 8942200
     * ga_prefix : 110310
     * title : 要想避免「江郎才尽」没钱赚，网红该怎么办？
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {

        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        private boolean isRead;

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
