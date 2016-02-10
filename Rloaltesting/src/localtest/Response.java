package localtest;

import java.io.InputStream;
import java.util.Random;


public class Response {
    private InputStream body;
    //Random rn = new Random();
    //private int count = rn.nextInt(1000) ;
    private long lStartTime;
    private long lEndTime;

    public Response(InputStream body) {
        this.body = body;
        this.lStartTime=System.currentTimeMillis();
    }

    public InputStream getBody(int count ) {
    	this.lEndTime=System.currentTimeMillis();
    	long difference6 = this.lEndTime - this.lStartTime;
        System.out.println("thread "+ count + " Elapsed milliseconds: " + difference6  );
        return body;
    }
}