package MessageHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Kasper on 01-Dec-16.
 */
public class MessageGetter {
    private ObjectMapper mapper = new ObjectMapper();
    private MessageHolder messages = null;
    private String pathname = "";

    public MessageGetter(){

        File jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String propFileName = jarPath.getAbsolutePath();
        if(propFileName.endsWith(".jar")){
            propFileName = jarPath.getParentFile().getAbsolutePath();
        }
        pathname = propFileName + "/messages.json";

    }

    public String getMessage(){
        try {
            messages = mapper.readValue(new File(pathname)
                    , MessageHolder.class);
        }catch (IOException e){
            System.out.print("Something wrong with messages");
        }
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if(hour > 7 && hour < 10)
            return messages.getRandomMorningMessages();
        if(hour > 21 || hour <= 7)
            return messages.getRandomEveningMessages();
        return messages.getRandomDefaultMessages();

    }

}
