package com.gingbear.githubtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomJson {

	public String getJson(){
    String parsedText = "";
    String json = getData("http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo");
    try {
        // オブジェクトの生成
        JSONObject rootObject = new JSONObject(json);
        
        // JSON 形式データ文字列にインデントを加えた形に成形
        parsedText = rootObject.toString(4);
//        Iterator<?> it = rootObject.keys();
//        while(it.hasNext()){
//        	String key = (String)it.next();
//        	  JSONObject jCoupon = rootObject.getJSONObject(key);
//        	  if(jCoupon.keys().hasNext()){
//                  JSONArray  itemArray = jCoupon.getJSONArray(key);
//        	  } else {
//        	  }
////        	  ResponseDataCoupon rd = new ResponseDataCoupon(jCoupon);
////        	  ListItem tmpItem = new ListItem(rd);
////        	  list.add(tmpItem);
//        	}
        return parsedText;
    }
    catch (JSONException e){
        // 例外処理
        return "error";
    }
	}
	
	/** 
	 * 指定URLからgetした文字列を取得する 
	 * @param sUrl 
	 * @return 
	 */  
	public String getData(String sUrl) {  
	    HttpClient objHttp = new DefaultHttpClient();  
	    HttpParams params = objHttp.getParams();  
	    HttpConnectionParams.setConnectionTimeout(params, 1000); //接続のタイムアウト  
	    HttpConnectionParams.setSoTimeout(params, 1000); //データ取得のタイムアウト  
	    String sReturn = "";  
	    try {  
	        HttpGet objGet   = new HttpGet(sUrl);  
	        HttpResponse objResponse = objHttp.execute(objGet);  
	        if (objResponse.getStatusLine().getStatusCode() < 400){  
	            InputStream objStream = objResponse.getEntity().getContent();  
	            InputStreamReader objReader = new InputStreamReader(objStream);  
	            BufferedReader objBuf = new BufferedReader(objReader);  
	            StringBuilder objJson = new StringBuilder();  
	            String sLine;  
	            while((sLine = objBuf.readLine()) != null){  
	                objJson.append(sLine);  
	            }  
	            sReturn = objJson.toString();  
	            objStream.close();  
	        }  
	    } catch (IOException e) {  
	        return null;  
	    }     
	    return sReturn;  
	} 
	
	/** 
	 * 指定URLからpostした文字列を取得する 
	 * @param sUrl 送信先URL 
	 * @param sJson 文字列に変換したJSONデータ 
	 * @return 
	 */  
	public String postJsonData(String sUrl, String sJson) {  
	    HttpClient objHttp = new DefaultHttpClient();  
	    String sReturn = "";  
	    try {  
	        HttpPost objPost   = new HttpPost(sUrl);  
	        List<NameValuePair> objValuePairs = new ArrayList<NameValuePair>(2);    
	        objValuePairs.add(new BasicNameValuePair("json", sJson));  
	        objPost.setEntity(new UrlEncodedFormEntity(objValuePairs, "UTF-8"));  
	  
	        HttpResponse objResponse = objHttp.execute(objPost);  
	        if (objResponse.getStatusLine().getStatusCode() < 400){  
	            InputStream objStream = objResponse.getEntity().getContent();  
	            InputStreamReader objReader = new InputStreamReader(objStream);  
	            BufferedReader objBuf = new BufferedReader(objReader);  
	            StringBuilder objJson = new StringBuilder();  
	            String sLine;  
	            while((sLine = objBuf.readLine()) != null){  
	                objJson.append(sLine);  
	            }  
	            sReturn = objJson.toString();  
	            objStream.close();  
	        }  
	    } catch (IOException e) {  
	        return null;  
	    }     
	    return sReturn;  
	} 
}
