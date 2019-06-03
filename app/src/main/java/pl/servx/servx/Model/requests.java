package pl.servx.servx.Model;

import java.util.List;
import java.util.Vector;

public class requests {
   public List<request> req;


    public requests(){
        req =  new Vector<request>();
        request lol= new request();
        req.add(lol);
   }
}
