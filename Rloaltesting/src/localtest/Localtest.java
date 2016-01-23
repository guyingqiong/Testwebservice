package localtest;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;






import java.util.List;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;




public class Localtest {

	 //private static //Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
	public static void main(String[] args) throws JSONException, IOException {
		
		   long lStartTime = System.currentTimeMillis();
            Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
	        System.out.println("Rengine created, waiting for R");
	 
	        // the engine creates R is a new thread, so we should wait until it's
	        // ready
	        if (!re.waitForR()) {
	            System.out.println("Cannot load R");
	            return;
	        }
	 
	        REXP x0 = re.eval("R.version.string");
            System.out.println(x0.asString());
	
            
            //run the R locally with JRI
 
             //String first3 ="library(bnlearn)";
             //re.eval(first3);
            String first4 ="library(gRain)";
             re.eval(first4);
             
             
             String fifth ="i=1";
             x0=re.eval(fifth);
             String second ="load(\"/Users/ygu01/Downloads/BN_MODEL1.rdata\")";
             x0=re.eval(second);
             String fourth = "a<-predict(bn_tree_g, response = c(\"parts_installed\"), newdata = t[i,], predictors = c(1:41), type = \"dist\")";
             x0=re.eval(fourth);
             //System.out.println(x0.asString());
             String third ="prob<- a$pred$parts_installed ";
             x0=re.eval(third);
             //System.out.println(x0.asString());
             String last ="v <- prob[,which.max(prob)] ";
             x0=re.eval(last);
             System.out.println(x0.asDouble());
             String lastone ="v <- names(prob[,which.max(prob)]) ";
             x0=re.eval(lastone);
             System.out.println(x0.asString());
             
             long lEndTime = System.currentTimeMillis();
             long difference = lEndTime - lStartTime;

           	System.out.println("Elapsed milliseconds: " + difference);
       
           	File file = new File("timeconsuming.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			long max =0;
			long min = 10000;
			long recount= 0;
		    long total =0;
			
			for(int count=0;count<1;count++){
			
	       // calling local service 
           	long lStartTime1 = System.currentTimeMillis();
           	System.out.println("test local service 1 ");
           	JSONArray ja = new JSONArray();
           	JSONObject js = new JSONObject();
           	JSONObject j = new JSONObject();
           
           	List<String> container1 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
           	List<String> container2=  Arrays.asList("WASHERDD","IN","110","Top Load","Motor Coupler","Non Steam","Non HE","Timer","47","02/13/2014","starting or stopping.not starting;draining.not working at all");
           	
           	for(int i=0;i<container1.size();i++){
           		js.put(container1.get(i), container2.get(i));
           	}
           	 
           	ja.put(js);
         	             
         	JSONObject jsonObject = new JSONObject(ja.toString().substring(1,ja.toString().length()-1));
         	System.out.println(" string is "  + jsonObject.toString());
         	if(jsonObject.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject.has("mds_cd"));
         	
         	String temp1 = ja.toString().replace("[", "");
            String temp2=temp1.replace("]", "");
            String temp3=temp2.replaceAll(" ", "%20");
            String temp4=temp3.replaceAll("/", "\\/");
         	String url1 = "http://localhost:8080/getmax?input="+temp4;
         	System.out.println(" string is "  + url1);
          
         	URL url = new URL(url1);
         	System.out.println(" string is "  + url);
         			
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");

    		if (conn.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));

    		String output;
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    		}

    		conn.disconnect();
    		
           	 
           	
           	long lEndTime1 = System.currentTimeMillis();
            long difference1 = lEndTime1 - lStartTime1;

          	System.out.println("Elapsed milliseconds: " + difference1);
          	bw.write("Time is :"+ Long.toString(difference1));
          	bw.newLine();
          	if(difference1 > max) max =difference1;
          	if(difference1 < min ) min =difference1;
          	total= total + difference1;
          	recount++;
          	
          	//make a second call 
        	long lStartTime2 = System.currentTimeMillis();
           	System.out.println("test local service 2 ");
           	JSONArray ja2 = new JSONArray();
           	JSONObject js2 = new JSONObject();
           	JSONObject j2 = new JSONObject();
           
           	List<String> container21 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
           	List<String> container22=  Arrays.asList("WASHERDD","KY","401","Top Load","Rotor Stator","Non Steam","HE","Electronic","14","02/20/2014","spinning.not working at all");
           	
           	for(int i=0;i<container21.size();i++){
           		js2.put(container21.get(i), container22.get(i));
           	}
           	 
           	ja2.put(js2);
         	             
         	JSONObject jsonObject2 = new JSONObject(ja2.toString().substring(1,ja2.toString().length()-1));
         	System.out.println(" string is "  + jsonObject2.toString());
         	if(jsonObject.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject.has("mds_cd"));
         	
         	String temp21 = ja2.toString().replace("[", "");
            String temp22=temp21.replace("]", "");
            String temp23=temp22.replaceAll(" ", "%20");
            String temp24=temp23.replaceAll("/", "\\/");
         	String url21 = "http://localhost:8080/getmax?input="+temp24;
         	System.out.println(" string is "  + url21);
          
         	URL url2 = new URL(url21);
         	System.out.println(" string is "  + url2);
         			
    		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
    		conn2.setRequestMethod("GET");
    		conn2.setRequestProperty("Accept", "application/json");

    		if (conn2.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn2.getResponseCode());
    		}

    		BufferedReader br2 = new BufferedReader(new InputStreamReader(
    			(conn2.getInputStream())));

    		String output2;
    		System.out.println("Output from Server .... \n");
    		while ((output2 = br2.readLine()) != null) {
    			System.out.println(output2);
    		}

    		conn2.disconnect();
    		
           	 
           	
           	long lEndTime2 = System.currentTimeMillis();
            long difference2 = lEndTime2 - lStartTime2;

          	System.out.println("Elapsed milliseconds: " + difference2);
          	 
          	bw.write("Time is :"+ Long.toString(difference2));
          	bw.newLine();
          	if(difference2 > max) max =difference2;
          	if(difference2 < min ) min =difference2;
          	total= total + difference2;
          	recount++;
          	
          //make a 3 call 
        	long lStartTime3 = System.currentTimeMillis();
           	System.out.println("test local service 3 ");
           	JSONArray ja3 = new JSONArray();
           	JSONObject js3 = new JSONObject();
           	JSONObject j3 = new JSONObject();
           
           	List<String> container31 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
           	List<String> container32=  Arrays.asList("WASHTOP1YR3","OH","110","Top Load","Belt","Non Steam","HE","Electronic","40","02/21/2014","spinning.not working at all");
           	
           	for(int i=0;i<container31.size();i++){
           		js3.put(container31.get(i), container32.get(i));
           	}
           	 
           	ja3.put(js3);
         	             
         	JSONObject jsonObject3 = new JSONObject(ja3.toString().substring(1,ja3.toString().length()-1));
         	System.out.println(" string is "  + jsonObject3.toString());
         	if(jsonObject.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject3.has("mds_cd"));
         	
         	String temp31 = ja3.toString().replace("[", "");
            String temp32=temp31.replace("]", "");
            String temp33=temp32.replaceAll(" ", "%20");
            String temp34=temp33.replaceAll("/", "\\/");
         	String url31 = "http://localhost:8080/getmax?input="+temp34;
         	System.out.println(" string is "  + url31);
          
         	URL url3 = new URL(url31);
         	System.out.println(" string is "  + url3);
         			
    		HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
    		conn3.setRequestMethod("GET");
    		conn3.setRequestProperty("Accept", "application/json");

    		if (conn3.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn3.getResponseCode());
    		}

    		BufferedReader br3 = new BufferedReader(new InputStreamReader(
    			(conn3.getInputStream())));

    		String output3;
    		System.out.println("Output from Server .... \n");
    		while ((output3 = br3.readLine()) != null) {
    			System.out.println(output3);
    		}

    		conn3.disconnect();
    		
           	 
           	
           	long lEndTime3 = System.currentTimeMillis();
            long difference3 = lEndTime3 - lStartTime3;

          	System.out.println("Elapsed milliseconds: " + difference3);
        	bw.write("Time is :"+ Long.toString(difference3));
        	bw.newLine();
          	if(difference3 > max) max =difference3;
          	if(difference3 < min ) min =difference3;
          	total= total + difference3;
          	recount++;
          //make a 4 call 
        	long lStartTime4 = System.currentTimeMillis();
           	System.out.println("test local service 4 ");
           	JSONArray ja4 = new JSONArray();
           	JSONObject js4 = new JSONObject();
           	JSONObject j4 = new JSONObject();
           
           	List<String> container41 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
           	List<String> container42=  Arrays.asList("WASHERDD","KY","110","Top Load","Belt","Non Steam","HE","Electronic","19","02/21/2014","close - open/close/lock or unlock.not locking;starting or stopping");
           	
           	for(int i=0;i<container41.size();i++){
           		js4.put(container41.get(i), container42.get(i));
           	}
           	 
           	ja4.put(js4);
         	             
         	JSONObject jsonObject4 = new JSONObject(ja4.toString().substring(1,ja4.toString().length()-1));
         	System.out.println(" string is "  + jsonObject4.toString());
         	if(jsonObject.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject4.has("mds_cd"));
         	
         	String temp41 = ja4.toString().replace("[", "");
            String temp42=temp41.replace("]", "");
            String temp43=temp42.replaceAll(" ", "%20");
            String temp44=temp43.replaceAll("/", "\\/");
         	String url41 = "http://localhost:8080/getmax?input="+temp44;
         	System.out.println(" string is "  + url41);
          
         	URL url4 = new URL(url41);
         	System.out.println(" string is "  + url4);
         			
    		HttpURLConnection conn4 = (HttpURLConnection) url4.openConnection();
    		conn4.setRequestMethod("GET");
    		conn4.setRequestProperty("Accept", "application/json");

    		if (conn4.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn4.getResponseCode());
    		}

    		BufferedReader br4 = new BufferedReader(new InputStreamReader(
    			(conn4.getInputStream())));

    		String output4;
    		System.out.println("Output from Server .... \n");
    		while ((output4 = br4.readLine()) != null) {
    			System.out.println(output4);
    		}

    		conn4.disconnect();
    		
           	 
           	
           	long lEndTime4 = System.currentTimeMillis();
            long difference4 = lEndTime4 - lStartTime4;

          	System.out.println("Elapsed milliseconds: " + difference4);
          	bw.write("Time is :"+ Long.toString(difference4));
          	bw.newLine();
          	if(difference4 > max) max =difference4;
          	if(difference4 < min ) min =difference4;
          	total= total + difference4;
          	recount++;
          	
          	 //make a 5 call 
        	long lStartTime5 = System.currentTimeMillis();
           	System.out.println("test local service 5 ");
           	JSONArray ja5 = new JSONArray();
           	JSONObject js5 = new JSONObject();
           	JSONObject j5 = new JSONObject();
           
           	List<String> container51 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
           	List<String> container52=  Arrays.asList("waster","KY","417","Front Load","Belt","Non Steam","HE","Electronic","97","02/21/2014","draining.not working at all");
           	
           	for(int i=0;i<container51.size();i++){
           		js5.put(container51.get(i), container52.get(i));
           	}
           	 
           	ja5.put(js5);
         	             
         	JSONObject jsonObject5 = new JSONObject(ja5.toString().substring(1,ja5.toString().length()-1));
         	System.out.println(" string is "  + jsonObject5.toString());
         	if(jsonObject.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject5.has("mds_cd"));
         	
         	String temp51 = ja5.toString().replace("[", "");
            String temp52=temp51.replace("]", "");
            String temp53=temp52.replaceAll(" ", "%20");
            String temp54=temp53.replaceAll("/", "\\/");
         	String url51 = "http://localhost:8080/getmax?input="+temp54;
         	System.out.println(" string is "  + url51);
          
         	URL url5 = new URL(url51);
         	System.out.println(" string is "  + url5);
         			
    		HttpURLConnection conn5 = (HttpURLConnection) url5.openConnection();
    		conn5.setRequestMethod("GET");
    		conn5.setRequestProperty("Accept", "application/json");

    		if (conn5.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn5.getResponseCode());
    		}

    		BufferedReader br5 = new BufferedReader(new InputStreamReader(
    			(conn5.getInputStream())));

    		String output5;
    		System.out.println("Output from Server .... \n");
    		while ((output5 = br5.readLine()) != null) {
    			System.out.println(output5);
    		}

    		conn5.disconnect();
    		
           	 
           	
           	long lEndTime5 = System.currentTimeMillis();
            long difference5 = lEndTime5 - lStartTime5;

          	System.out.println("Elapsed milliseconds: " + difference5);
          	bw.write("Time is :"+ Long.toString(difference5));
          	bw.newLine();
          	if(difference5 > max) max =difference5;
          	if(difference5 < min ) min =difference5;
          	total= total + difference5;
          	recount++;
          	
          //make a 6 call 
        	long lStartTime6 = System.currentTimeMillis();
           	System.out.println("test local service 6 ");
           	JSONArray ja6 = new JSONArray();
           	JSONObject js6 = new JSONObject();
           	JSONObject j6 = new JSONObject();
           
           	List<String> container61 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
           	List<String> container62=  Arrays.asList("waster","AI","417","Front Load","Belt","Non Steam","HE","Electronic","97","02/21/2014","draining.not working at all");
           	
           	for(int i=0;i<container61.size();i++){
           		js6.put(container61.get(i), container62.get(i));
           	}
           	 
           	ja6.put(js6);
         	             
         	JSONObject jsonObject6 = new JSONObject(ja6.toString().substring(1,ja6.toString().length()-1));
         	System.out.println(" string is "  + jsonObject6.toString());
         	if(jsonObject.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject6.has("mds_cd"));
         	
         	String temp61 = ja6.toString().replace("[", "");
            String temp62=temp61.replace("]", "");
            String temp63=temp62.replaceAll(" ", "%20");
            String temp64=temp63.replaceAll("/", "\\/");
         	String url61 = "http://localhost:8080/getmax?input="+temp64;
         	System.out.println(" string is "  + url61);
          
         	URL url6 = new URL(url61);
         	System.out.println(" string is "  + url6);
         			
    		HttpURLConnection conn6 = (HttpURLConnection) url6.openConnection();
    		conn6.setRequestMethod("GET");
    		conn6.setRequestProperty("Accept", "application/json");

    		if (conn6.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn6.getResponseCode());
    		}

    		BufferedReader br6 = new BufferedReader(new InputStreamReader(
    			(conn6.getInputStream())));

    		String output6;
    		System.out.println("Output from Server .... \n");
    		while ((output6 = br6.readLine()) != null) {
    			System.out.println(output6);
    		}

    		conn6.disconnect();
    		
           	 
           	
           	long lEndTime6 = System.currentTimeMillis();
            long difference6 = lEndTime6 - lStartTime6;

          	System.out.println("Elapsed milliseconds: " + difference6);
          	bw.write("Time is :"+ Long.toString(difference6));
          	bw.newLine();
          	if(difference6 > max) max =difference6;
          	if(difference6 < min ) min =difference6;
          	total= total + difference6;
          	recount++;
          	
          	
			}
			
			bw.write("                                      ");
			bw.newLine();
			bw.write("We run services for  :"+ recount + "  times.");
			bw.newLine();
			bw.write("Average time for each call is   :"+ total/recount + "  Mill-seconds.");
			bw.newLine();
			bw.write("Max time for all calls is   :"+ max + " Mill-seconds.");
			bw.newLine();
			bw.write("Min time for all calls is   :"+ min + " Mill-seconds.");
			
			bw.newLine();
			bw.write("Test with 2000 + input.");
			bw.newLine();
			//File datafile = new File("data.txt");
			//File partsfile = new File("parts.txt");
			//File probfile = new File("prob.txt");
			BufferedReader datafile = new BufferedReader(new FileReader("data.txt"));
			BufferedReader partsfile = new BufferedReader(new FileReader("parts.txt"));
			BufferedReader probfile = new BufferedReader(new FileReader("prob.txt"));
			
			
			int currentline = 1;
			int errorcount =0;
			//calling 2000+ input
			String sCurrentLine;
			String partsCurrentLine;
			String probCurrentLine;
			System.out.println("test 2000+ input ");
			
			while ((sCurrentLine = datafile.readLine()) != null) {
				partsCurrentLine = partsfile.readLine();
				probCurrentLine = probfile.readLine();
				System.out.println("test Case  " + currentline);
				System.out.println(sCurrentLine + " " +partsCurrentLine +" "+probCurrentLine);
				long lStartTime6 = System.currentTimeMillis();
	           	
	           	JSONArray ja6 = new JSONArray();
	           	JSONObject js6 = new JSONObject();
	           	JSONObject j6 = new JSONObject();
	           
	           	List<String> container61 = Arrays.asList("mds_cd","state_cd","model_source","model_chassis","model_drivetype","model_steam","model_he","model_controltype" ,"mol","date","Normalized_Symptom");
	           	String[] items = sCurrentLine.split(",");
	            List<String> itemList = Arrays.asList(items);
	           	//List<String> container62=  Arrays.asList(sCurrentLine) ;
	           	
	           	for(int i=0;i<container61.size();i++){
	           		js6.put(container61.get(i), itemList.get(i).toString().substring(1,itemList.get(i).toString().length()-1 ));
	           	}
	           	 
	           	ja6.put(js6);
	         	             
	         	JSONObject jsonObject6 = new JSONObject(ja6.toString().substring(1,ja6.toString().length()-1));
	         	System.out.println(" string is "  + jsonObject6.toString());
	         	if(jsonObject6.has("mds_cd") != true) System.out.println("haha  string is "  + jsonObject6.has("mds_cd"));
	         	
	         	String temp61 = ja6.toString().replace("[", "");
	            String temp62=temp61.replace("]", "");
	            String temp63=temp62.replaceAll(" ", "%20");
	            String temp64=temp63.replaceAll("/", "\\/");
	         	String url61 = "http://localhost:8080/getmax?input="+temp64;
	         	System.out.println(" string is "  + url61);
	          
	         	URL url6 = new URL(url61);
	         	System.out.println(" string is "  + url6);
	         			
	    		HttpURLConnection conn6 = (HttpURLConnection) url6.openConnection();
	    		conn6.setRequestMethod("GET");
	    		conn6.setRequestProperty("Accept", "application/json");

	    		if (conn6.getResponseCode() != 200) {
	    			throw new RuntimeException("Failed : HTTP error code : "
	    					+ conn6.getResponseCode());
	    		}

	    		BufferedReader br6 = new BufferedReader(new InputStreamReader(
	    			(conn6.getInputStream())));

	    		String output6 = new String();
	    		StringBuilder tmp= new StringBuilder();
	    		System.out.println("Output from Server .... \n");
	    		while ((output6 = br6.readLine()) != null) {
	    			System.out.println(output6);
	    			tmp.append(output6);
	    		}

	    		conn6.disconnect();
	    		
	           	 
	           	
	           	long lEndTime6 = System.currentTimeMillis();
	            long difference6 = lEndTime6 - lStartTime6;
               int error = 0 ;
	          	System.out.println("Elapsed milliseconds: " + difference6);
	          	bw.write("Time is :"+ Long.toString(difference6));
	          	bw.write("   this is case:  "+ currentline + "   ");
	          	System.out.println("  partsCurrentLine: " + partsCurrentLine);
	          	System.out.println("  probCurrentLine: " + probCurrentLine);
	          	System.out.println("  tmp: " + tmp);
	          	System.out.println("  currentline: " + currentline);
	          	
	         // when
	           // JSONObject object = (JSONObject) JSONObjJsonNode.parse(tmp);
	          	 
	          	
	          	
	          	if(tmp.toString().contains(partsCurrentLine) !=true) {
	          		bw.write("  in line:" +currentline +"    Error in parts :  expected:  "+ partsCurrentLine );
	          		errorcount++;
	          		error = 1;
	          	}
	            if(probCurrentLine.length()<=5){
	            	if(tmp.toString().contains(probCurrentLine) != true){
	            	bw.write("  in line:" +currentline +"      Error in Prob :  expected:  "+ probCurrentLine );
	          		errorcount++;
	          		error = 1;
	          		}
	            }else if(tmp.toString().contains(probCurrentLine.substring(0, 6)) !=true) {
	          		bw.write("  in line:" +currentline +"      Error in Prob :  expected: "+ probCurrentLine );
	          		errorcount++;
	          		error = 1;
	          	}
	          	if(error ==1)   
	            bw.write("  the url link is "+ url6 );
	          	error =0;
	          	bw.newLine();
	          	currentline++;
			}
			
			bw.write("we run " + currentline+ " cases, failed " + errorcount + " cases.");
			datafile.close();
			partsfile.close();
			probfile.close();
			bw.close();
}
}