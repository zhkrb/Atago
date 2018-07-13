package com.zhkrb.atago.jsoup;

import com.zhkrb.atago.bean.DmhyListBean;
import com.zhkrb.atago.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupUtils {

    private static final String HOST_DMHY = "https://share.dmhy.org/topics/list/page/" +
            "%1$s?keyword=%2$s&sort_id=%3$s&team_id=%4$s&order=%5$s";
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";


    private String team_id="0";
    private String sort_id="0";
    private String order="date-desc";

    private static JsoupUtils sInstance;


    public void SearchDmhy(String name, String p, final JoupCallback callback){
        final String Url = String.format(HOST_DMHY, p,name,sort_id,team_id,order);
        L.e(Url);
        final List<DmhyListBean> searchList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(Url).header("User-Agent",UA)//.cookies(cookies)
                            .timeout(30000).get();
                    Elements body = doc.getElementsByClass("tablesorter").select("tbody").select("tr");
                    if (body==null){
                        callback.onError("Empty");
                        return;
                    }
                    if (body.size()==0){
                        callback.onError("Empty");
                        return;
                    }
                    for (Element item:body){
                        Elements td = item.select("td");
                        String time;
                        if (td.get(0).ownText()!=null){
                            time= td.get(0).ownText();
                        }else {
                            time = td.get(0).select("span").text();
                        }
                        String sort = td.get(1).select("a").attr("class");
                        String title = td.get(2).select("a[target=_blank]").text();
                        String team_id;
                        if (td.get(2).select("span").hasClass("tag")){
                            team_id = getTeamid(td.get(2).select("span[class=tag]").select("a").attr("href"));
                        }else {
                            team_id = "";
                        }
                        String size = td.get(4).text();
                        String magnet = td.get(3).select("a").attr("href");
                        DmhyListBean bean = new DmhyListBean(magnet,title,time,sort,team_id,size);
                        searchList.add(bean);
                    }
                    if (searchList.size()>0){
                        callback.onResult(searchList);
                        return;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onError("Error");
                }

                callback.onResult(null);
            }
        }).start();

    }

    private String getTeamid(String i) {
        String [] temp;
        temp = i.split("/");
        if (temp.length>1){
            return temp[temp.length-1];
        }else {
            return "";
        }
    }

    public JsoupUtils setOption(String team_id,String sort_id,String order){
        this.team_id = team_id;
        this.sort_id = sort_id;
        this.order = order;
        return sInstance;
    }

    public static JsoupUtils getsInstance() {
        if (sInstance==null){
            sInstance = new JsoupUtils();
        }

        return sInstance;
    }

    public interface JoupCallback{
        void onResult(List<DmhyListBean> list);
        void onError(String msg);
    }
}
