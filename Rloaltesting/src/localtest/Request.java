package localtest;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Callable;

public  class Request implements Callable<Response> {
    private URL url;
    
    
    public Request(URL url) throws IOException {
        this.url = url;
       
		
		
    }

    @Override
    public  Response call() throws Exception {
        return new Response(url.openStream());
    }
    
   
}