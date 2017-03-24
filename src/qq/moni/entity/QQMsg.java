package qq.moni.entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import qq.moni.Api;
import qq.moni.QQConstants;
import qq.moni.Robot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QQMsg {

	private String poll_type;
	private String msg_id;
	private String msg_type;
	private String from_uin;
	private String to_uin;
	private String time;
	//QQ群用得到
	private String send_uin;
	private String group_code;
	private String msg;
	
	boolean pollFlag = true;//true正在循环，false已经停止获取消息
	
	
	
	/**
	 * 消息轮训
     * 获得消息 需要对Header进行设置！
     * @param httpclient
     */
    public boolean getmsg(CloseableHttpClient httpclient, Params params) {

            HttpPost httppost = new HttpPost("http://d1.web2.qq.com/channel/poll2");
            //URI.create(URL_GET_STRANGER_INFO)
            String html="";
            httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
            httppost.setHeader("charset","UTF-8");
            httppost.setHeader("Origin","http://d1.web2.qq.com");
            httppost.setHeader("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
            //httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
            formparams.add(new BasicNameValuePair("r","{\"ptwebqq\":\""+params.getPtwebqq()+"\",\"clientid\":53999199,\"psessionid\":\""+params.getPsessionid()+"\",\"key\":\"\"}"));
            UrlEncodedFormEntity uefEntity;
            try {
                uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httppost.setEntity(uefEntity);  
                System.out.println("executing request " + httppost.getURI());
                HttpResponse response =  null;
                for(int i=0;;i++) {
                	
                	response =  httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();  
                    html=EntityUtils.toString(entity, "utf-8");   
                    System.out.println("----->"+html+"\t"+html.length());
                    
                    JSONObject jsonObj = (JSONObject) JSONObject.parse(html);
                    
                    int retcode = 0;
                    if(176 > html.length()) {
                    	retcode = jsonObj.getIntValue("retcode");
                    	if(103 == retcode) {
                        	pollFlag = false;
                        	System.out.println("error!!!---130");
                        	break;
                        }
                    } else if(176 == html.length()) {
                    	System.out.println("error!!!---176");
                    	
                    }else if(html.length() > 176) {
                    	//{"errmsg":"error!!!","retcode":103} 需要重新登录，如果一直返回103，则需要用改QQ登录w.qq.com，然后点击退出登录，在重新扫码即可
                    	retcode = jsonObj.getIntValue("retcode");
                    	if(0 == retcode) {
                        	//
                        	JSONArray resultArray = (JSONArray) jsonObj.get("result");
                        	JSONObject resultJson = (JSONObject) resultArray.get(0);
                        	
                        	String poll_type = resultJson.getString("poll_type");
                        	setPoll_type(poll_type);
                        	JSONObject valueObj = (JSONObject) resultJson.get("value");
                        	setFrom_uin(valueObj.getString("from_uin"));
                        	setMsg_id(valueObj.getString("msg_type"));
                        	setMsg_id(valueObj.getString("msg_id"));
                        	if("group_message".equals(poll_type)) {
                        		setSend_uin(valueObj.getString("send_uin"));
                        		setGroup_code(valueObj.getString("group_code"));
                        	}
                        	JSONArray jsonArray = (JSONArray) valueObj.get("content");
                        	setMsg(Api.getMsg(jsonArray.getString(1)));
                        	sendmsg(httpclient, params);
                        }
                        
                     /*   String group_code=HttpTool.getgroup_code(html);//判断群
                        System.out.println("group_code:"+group_code);
                        String msg=HttpTool.getmsgtext(html);
                        String sendsms=Robot.postsms(msg);
                        sendmsg(httpclient,sendsms,group_code);//小黄鸡鸡，不想接入删除即可
                        */

                    }
                }
                
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }  
            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pollFlag;
    }
    
    /**
     * 发送消息
     * @param httpclient
     * @param msg_type
     * @param msg_id
     * @param from_uin
     * @param sms
     */
  //sendmsg(httpclient, msg_type, msg_id, from_uin, msg);//小黄鸡鸡，不想接入删除即可
    public void sendmsg(CloseableHttpClient httpclient, Params params)
    {
        HttpPost httppost = new HttpPost();
        httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
        httppost.setHeader("Origin","http://d1.web2.qq.com");
        httppost.setHeader("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
    //  httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
        String html;
         List<NameValuePair> formparams = new ArrayList<NameValuePair>();
         if("message".equals(getPoll_type())) {//好友消息
        	 httppost.setURI(URI.create(QQConstants.URL_SEND_FRIEND));
        	 System.out.println("-----getFrom_uin:"+getFrom_uin());
        	 formparams.add(new BasicNameValuePair("r","{\"to\":"+getFrom_uin()+",\"content\":\"[\\\""+getMsg()+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":165,\"clientid\":53999199,\"msg_id\":"+msg_id+",\"psessionid\":\""+params.getPsessionid()+"\"}"));
         } else if("group_message".equals(getPoll_type())) {//群消息
        	 httppost.setURI(URI.create(QQConstants.URL_SEND_GROUP));
        	 formparams.add(new BasicNameValuePair("r","{\"group_uin\":"+getFrom_uin()+",\"content\":\"[\\\""+getMsg()+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":165,\"clientid\":53999199,\"msg_id\":"+msg_id+",\"psessionid\":\""+params.getPsessionid()+"\"}"));
         } else if("discu_message".equals(getPoll_type())) {//讨论组消息
        	 httppost.setURI(URI.create(QQConstants.URL_SEND_DISCU));
        	 formparams.add(new BasicNameValuePair("r","{\"did\":"+getFrom_uin()+",\"content\":\"[\\\""+getMsg()+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":165,\"clientid\":53999199,\"msg_id\":"+msg_id+",\"psessionid\":\""+params.getPsessionid()+"\"}"));
         }
         
         System.out.println("发送的参数r："+formparams.get(0).getValue());
         UrlEncodedFormEntity uefEntity;
         try {
        	 uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
             httppost.setEntity(uefEntity);  
             System.out.println("executing request " + httppost.getURI());  
             HttpResponse response =  httpclient.execute(httppost);
             HttpEntity entity = response.getEntity();  
             html=EntityUtils.toString(entity, "utf-8");   
             System.out.println(html);
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  
         catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	//setter getter
	public String getPoll_type() {
		return poll_type;
	}
	public void setPoll_type(String poll_type) {
		this.poll_type = poll_type;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getFrom_uin() {
		return from_uin;
	}
	public void setFrom_uin(String from_uin) {
		this.from_uin = from_uin;
	}
	public String getTo_uin() {
		return to_uin;
	}
	public void setTo_uin(String to_uin) {
		this.to_uin = to_uin;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSend_uin() {
		return send_uin;
	}
	public void setSend_uin(String send_uin) {
		this.send_uin = send_uin;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
