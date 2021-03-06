package qq.moni;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.methods.CloseableHttpResponse;

public class HttpTool {

	public static int ptqrtoken(String qrsig) {
		int t = qrsig.length();
		int e = 0, i=0,n=0,m=2147483647;
		for(e=0,i=0,n=t;n>i;++i)e+=(e<<5)+qrsig.charAt(i);return m&e;
	}
	
    public static String getCookie(String cookiename,CloseableHttpResponse response )
    {
        Header[] cookie=response.getAllHeaders();
        for(int i=0;i<cookie.length;i++)
        {
            HeaderElement[] he=cookie[i].getElements();
            for(int j=0;j<he.length;j++)
            {
                String name=he[j].getName();
                if(cookiename.equals(name))
                    return he[j].getValue();
            }
        }
        return "";
    }
    public static String getCookie2(String cookiename,CloseableHttpResponse response )
    {
        Header[] cookie=response.getAllHeaders();
        for(int i=0;i<cookie.length;i++)
        {
            HeaderElement[] he=cookie[i].getElements();
            for(int j=0;j<he.length;j++)
            {
                String name=he[j].getName();
                if(cookiename.equals(name))
                    return he[j].getValue();
            }
        }
        return "";
    }
    public static String getaid(String html)
    {
        int start=html.indexOf("&aid=")+5;
        html=html.substring(start);
        int end=html.indexOf("&daid");
        String aid=html.substring(0, end);
        System.out.println("aid:"+aid);
        return aid;
    }
    public static String getgetCookieurl(String html)
    {
        int start=html.indexOf("ptuiCB('0','0','");
        html=html.substring(start)+16;
        int end=html.indexOf("','");
        String str=html.substring(0, end);
        System.out.println("getCookieurl:"+str);
        return str;
    }
    public static String getgroup_code(String html)
    {
        int start=html.indexOf("\"],\"from_uin\":")+14;
        int end=html.indexOf(",\"group_code\":");
        if(end>html.length()||end==-1)
        {
            return "";
        }
        return html.substring(start,end);

    }
    public static String getmsgtext(String html)
    {
        int start=html.indexOf("]}],\"")+5;
        int end=html.indexOf("\"],\"from_uin\":");
        if(end>html.length()||end==-1)
        {
            return "";
        }
        return html.substring(start,end);
    }
    
    public static void main(String[] agrs)
    {
        String sts="{\"result\":[{\"poll_type\":\"group_message\",\"value\":{\"content\":[[\"font\",{\"color\":\"000000\",\"name\":\"微软雅黑\",\"size\":10,\"style\":[0,0,0]}],\"能不能回应啊 \"],\"from_uin\":1695980647,\"group_code\":1695980647,\"msg_id\":12284,\"msg_type\":0,\"send_uin\":359732453,\"time\":1463111215,\"to_uin\":1069478446}}],\"retcode\":0}";
        System.out.println(sts.length());
        System.out.println(HttpTool.getgroup_code(sts));
        System.out.println(HttpTool.getmsgtext(sts));
    }
}