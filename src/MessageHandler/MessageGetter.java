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

    public MessageGetter(){
        try{
            File jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            String propFileName = jarPath.getAbsolutePath();
            if(propFileName.endsWith(".jar")){
                propFileName = jarPath.getParentFile().getAbsolutePath();
            }
            messages = mapper.readValue( new File(propFileName + "/messages.json")
                    , MessageHolder.class);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getMessage(){
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if(hour > 7 && hour < 10)
            return messages.getRandomMorningMessages();
        if(hour > 21 || hour <= 7)
            return messages.getRandomEveningMessages();
        return messages.getRandomDefaultMessages();

    }

}
