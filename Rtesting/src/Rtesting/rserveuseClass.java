package Rtesting;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;

public class rserveuseClass {
    public static void main(String[] args) throws RserveException, REXPMismatchException {
        try {
        	System.out.println("building R !!!!");
            RConnection c = new RConnection();// make a new local connection on default port (6311)
            double d[] = c.eval("rnorm(10)").asDoubles();
            org.rosuda.REngine.REXP x0 = c.eval("R.version.string");
            System.out.println(x0.asString());
            
            long lStartTime = System.currentTimeMillis();
           //run the R locally
           String first ="source(\"http://bioconductor.org/biocLite.R\")";
            c.eval(first);
            String first1 ="library(\"BiocInstaller\", lib.loc=\"C:/Program Files/r/R-3.1.0/library\")";
            c.eval(first1);
            String first2 ="biocLite(c(\"graph\",\"RBGL\",\"Rgraphviz\",\"Rcpp\",\"bnlearn\",\"gRain\"),suppressUpdates=TRUE)";           
            //String first2 ="biocLite(c(\"graph\",\"RBGL\",\"Rgraphviz\",\"Rcpp\",\"bnlearn\",\"gRain\"))";   
            x0=c.eval(first2);
            System.out.println(x0.asString());
;
            String first3 ="library(bnlearn)";
            c.eval(first3);
            String first4 ="library(gRain)";
            c.eval(first4);
            
            
            String fifth ="i=1";
            x0=c.eval(fifth);
            String second ="load(\"/Users/ygu01/Downloads/BN_MODEL1.rdata\")";
            x0=c.eval(second);
            System.out.println(x0.asString());
            
             
           
            String fourth = "a<-predict(bn_tree_g, response = c(\"parts_installed\"), newdata = t[i,], predictors = c(1:41), type = \"dist\")";
            long lEndTime = System.currentTimeMillis();

            long difference = lEndTime - lStartTime;

        	System.out.println("Elapsed milliseconds: " + difference);
            
            
            
            
            x0=c.eval(fourth);
            String third ="prob<- a$pred$parts_installed ";
            x0=c.eval(third);
            String last ="v <- prob[,which.max(prob)] ";
            x0=c.eval(last);
            
            System.out.println(x0.asString());
            //System.out.println(x0._attr().toDebugString());
            
            int startindex = x0._attr().toDebugString().indexOf("\"");
            int lastindex = x0._attr().toDebugString().lastIndexOf("\"");
            String parts = x0._attr().toDebugString().substring(startindex+1, lastindex);

            
            
            
            System.out.println(parts);
            
            lStartTime = System.currentTimeMillis();
            String fifth1 ="i=2";
            x0=c.eval(fifth1);
            String second1 ="load(\"/Users/ygu01/Downloads/BN_MODEL1.rdata\")";
            x0=c.eval(second1);
            System.out.println(x0.asString());
            
             
           
            String fourth1 = "a<-predict(bn_tree_g, response = c(\"parts_installed\"), newdata = t[i,], predictors = c(1:41), type = \"dist\")";
         
              
             x0=c.eval(fourth1);
             String third1 ="prob<- a$pred$parts_installed ";
             x0=c.eval(third1);
             String last1 ="v <- prob[,which.max(prob)] ";
             x0=c.eval(last1);
             lEndTime = System.currentTimeMillis();

             difference = lEndTime - lStartTime;
             System.out.println("Elapsed milliseconds: haha " + difference);
             
             System.out.println(x0.asString());
             //System.out.println(x0._attr().toDebugString());
             
             int startindex1 = x0._attr().toDebugString().indexOf("\"");
             int lastindex1 = x0._attr().toDebugString().lastIndexOf("\"");
             String parts1 = x0._attr().toDebugString().substring(startindex1+1, lastindex1);
         

            
            
            
            System.out.println(parts1);
            
        } catch (Throwable e) {
            //manipulation
        	System.out.println(e.getMessage());
        	System.out.println(" Wrong!!!!");
        }       

    }
}