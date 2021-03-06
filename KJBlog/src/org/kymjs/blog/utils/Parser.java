package org.kymjs.blog.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.blog.domain.Blog;
import org.kymjs.blog.domain.BlogAuthor;
import org.kymjs.blog.domain.EverydayMessage;

import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 解析工具类
 * 
 * @author kymjs
 * 
 */
public class Parser {

    public static <T> T xmlToBean(Class<T> type, String xml) {
        T data = null;
        try {
            XStream xStream = new XStream(new DomDriver("UTF-8"));
            xStream.processAnnotations(type);
            data = (T) xStream.fromXML(xml);
        } catch (Exception e) {
            try {
                data = type.newInstance();
            } catch (Exception ee) {
            }
        }
        return data;
    }

    /**
     * 大神博客列表
     * 
     * @param json
     * @return
     */
    public static List<BlogAuthor> getBlogAuthor(String json) {
        List<BlogAuthor> datas = new ArrayList<BlogAuthor>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                BlogAuthor data = new BlogAuthor();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setHead(obj.optString("image", ""));
                data.setId(obj.optInt("id", 863548));
                data.setName(obj.optString("name", "张涛"));
                data.setDescription(obj.optString("description", "暂无简介"));
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("kymjs", "getBlogAuthor()解析异常");
        }

        return datas;
    }

    /**
     * 每日资讯数据
     * 
     * @param json
     * @return
     */
    public static List<EverydayMessage> getEveryDayMsg(String json) {
        List<EverydayMessage> datas = new ArrayList<EverydayMessage>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                EverydayMessage data = new EverydayMessage();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setId(obj.optString("id", "-1"));
                data.setDescription(obj.optString("description", "暂无简介"));
                data.setTitle(obj.optString("title", "暂无标题"));
                data.setImgUrl(obj.optString("imgurl", ""));
                data.setUrl(obj.optString("url", "http://blog.kymjs.com/"));
                data.setHasItem(obj.optBoolean("hasitem", false));

                List<String> imgUrls = new ArrayList<String>(5);
                JSONArray imgArray = obj.optJSONArray("imageurllist");
                for (int j = 0; j < imgArray.length(); j++) {
                    imgUrls.add(imgArray.getString(j));
                }
                data.setImageUrlList(imgUrls);

                List<String> urlList = new ArrayList<String>(5);
                JSONArray urlArray = obj.optJSONArray("urllist");
                for (int j = 0; j < urlArray.length(); j++) {
                    urlList.add(urlArray.getString(j));
                }
                data.setUrlList(urlList);

                List<String> titleList = new ArrayList<String>(5);
                JSONArray titleArray = obj.optJSONArray("titlelist");
                for (int j = 0; j < titleArray.length(); j++) {
                    titleList.add(titleArray.getString(j));
                }
                data.setTitleList(titleList);

                data.setTime(obj.optString("time", "未知时间"));
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("kymjs", "getEveryDayMsg()解析异常");
        }
        return datas;
    }

    /**
     * 首页博客列表
     * 
     * @param json
     * @return
     */
    public static List<Blog> getBlogList(String json) {
        List<Blog> datas = new ArrayList<Blog>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Blog data = new Blog();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setId(obj.optString("id", "-1"));
                data.setTitle(obj.optString("title", "无题"));
                data.setUrl(obj.optString("url", "http://blog.kymjs.com/"));
                data.setImageUrl(obj.optString("imageUrl", ""));
                data.setDate(obj.optString("date", "未知时间"));
                data.setIsRecommend(obj.optInt("isRecommend", 0));
                data.setAuthor(obj.optString("author", "佚名"));
                data.setIsAuthor(obj.optInt("isAuthor", 0));
                data.setDescription(obj.optString("description", "暂无简介"));
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("kymjs", "getBlogList()解析异常");
        }
        return datas;
    }
}
