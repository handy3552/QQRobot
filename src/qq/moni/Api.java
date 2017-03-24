package qq.moni;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Api {
	private static String URL = "http://route.showapi.com/60-27";
	private static String APPID = "****"; 
	private static String SECRET = "********************";
	
	public static String getMsg(String msg) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
	    System.out.println("获取二维码：Executing request " + httppost.getURI());//开始
	    List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
	    formparams.add(new BasicNameValuePair("showapi_appid", APPID));
	    formparams.add(new BasicNameValuePair("showapi_sign", SECRET));
        formparams.add(new BasicNameValuePair("info", msg));
        formparams.add(new BasicNameValuePair("userid", "testid"));
        
        UrlEncodedFormEntity uefEntity;
        String html = null;
        try {
           uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
           httppost.setEntity(uefEntity);  
           System.out.println("executing request " + httppost.getURI());  
            HttpResponse response =  httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();  
            html=Api.parseJson(EntityUtils.toString(entity, "utf-8"));   
            System.out.println(html);
        } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    return html;
	}
	

	private static String parseJson(String jsonStr) {
		System.out.println("--parseJson-->"+jsonStr);
		JSONObject jsonObj = JSONObject.parseObject(jsonStr);
		int showapi_res_code = jsonObj.getInteger("showapi_res_code");
		if(0 == showapi_res_code) {
			JSONObject bodyObj = jsonObj.getJSONObject("showapi_res_body");
			int ret_code = bodyObj.getIntValue("ret_code");
			if(0 == ret_code) {
				return bodyObj.getString("text");
			} else {
				return "Robot Error!!!";
			}
		} else {
			return jsonObj.getString("showapi_res_error");
		}
		 
		/*{
		    showapi_res_code : 0,
		    showapi_res_error : "",
		    showapi_res_body :{
			    text :"你好呀， 希望你今天过的快乐耶～ ",
			    ret_code : 0,
			    code :100000
		    }
		}*/
	}
	
	public static void main(String[] args) {
		System.out.println(Api.getMsg("你好"));
	}
}
