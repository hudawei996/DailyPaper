package com.lauzy.freedom.dailypaper.model;

import java.util.List;

/**
 * Created by Lauzy on 2016/11/2.
 */

public class ZHThemeItemDetail {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">


     <h1 class="headline-title onlyheading">更多日常心理学，都在读读日报里</h1>




     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title"></h2>

     <div class="answer">

     <div class="content">
     <div dir="ltr">知乎日报中的内容已经全部转移到了读读日报当中，所以在知乎日报中的日常心理学就不再更新惹。</div>
     <div dir="ltr">&nbsp;</div>
     <div dir="ltr">在读读日报中，<wbr />你可以看到更多更丰富的心理学日报，比如&mdash;&mdash;</div>
     <p><a href="http://dudu.zhihu.com/circle/119430">心理日报 有意思的心理学</a><br /><a href="http://dudu.zhihu.com/circle/131716" target="_blank">每日心理学推荐</a><br /><a href="http://dudu.zhihu.com/circle/124813" target="_blank">心理解放报</a><br /><a href="http://dudu.zhihu.com/circle/199714" target="_blank">心理分享小窝</a><br /><a href="http://dudu.zhihu.com/circle/143543" target="_blank">心理学泛读报</a><br /><a href="http://dudu.zhihu.com/circle/108584" target="_blank">心理学与精神分析</a><br /><br /></p>
     <p>读读日报里包含知乎日报里的所有内容，和小李。</p>
     <p>并新增了：</p>
     <p>- 搜索<br />- 知乎登录<br />- 微信登录<br />- 导入知乎日报收藏<br />- 丰富的各种日报<br />- 边缘右滑返回</p>
     <p>夜间模式也已经有了~</p>
     <p>随后也会无图模式，在读读日报里你们~</p>
     <div class="gmail_extra">
     <div>
     <div>
     <div dir="ltr">
     <p><a href="http://dudu.zhihu.com/" target="_blank">下载读读日报</a></p>
     <p>爱你的知乎日报 ~&nbsp;(✪&omega;✪)</p>
     </div>
     </div>
     </div>
     </div>
     </div>
     </div>


     </div>


     </div>
     </div>
     * title : 更多日常心理学，都在读读日报里
     * share_url : http://daily.zhihu.com/story/7483384
     * js : []
     * theme : {"thumbnail":"http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg","id":13,"name":"日常心理学"}
     * ga_prefix : 111823
     * type : 0
     * id : 7483384
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String title;
    private String share_url;
    /**
     * thumbnail : http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg
     * id : 13
     * name : 日常心理学
     */

    private ThemeBean theme;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ThemeBean getTheme() {
        return theme;
    }

    public void setTheme(ThemeBean theme) {
        this.theme = theme;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
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

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public static class ThemeBean {
        private String thumbnail;
        private int id;
        private String name;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
