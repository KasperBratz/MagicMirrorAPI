package MessageHandler;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Kasper on 01-Dec-16.
 */
public class MessageHolder {

    public static class messages {
        private String [] _morningMessages, _eveningMessages, _defaultMessages;

        public String[] getMorningMessages(){return _morningMessages;}
        public String [] getEveningMessages(){return _eveningMessages;}
        public String [] getDefaultMessages(){return _defaultMessages;}

        public void setDefaultMessages(String[] _defaultMessages) {
            this._defaultMessages = _defaultMessages;
        }

        public void setEveningMessages(String[] _eveningMessages) {
            this._eveningMessages = _eveningMessages;
        }

        public void setMorningMessages(String[] _morningMessages) {
            this._morningMessages = _morningMessages;
        }

        @Override
        public String toString() {
            System.out.println(_morningMessages.length);
           StringBuilder b = new StringBuilder();
            for(String s : _morningMessages){
                b.append(s + " ");
            }
            b.append('\n');
            for(String s : _eveningMessages){
                b.append(s + " ");
            }
            b.append('\n');
            for(String s : _defaultMessages){
                b.append(s + " ");
            }
            return b.toString();
        }
    }

    private messages _messages;
    public String getRandomMorningMessages(){
        String [] morning = _messages.getMorningMessages();
        int index = ThreadLocalRandom.current().nextInt(0, morning.length);
        return morning[index];
    }
    public String getRandomEveningMessages(){
        String[]evening =  _messages.getEveningMessages();
        int index = ThreadLocalRandom.current().nextInt(0, evening.length);
        return evening[index];
    }

    public String getRandomDefaultMessages(){
        String[] def = _messages.getDefaultMessages();
        int index = ThreadLocalRandom.current().nextInt(0, def.length);
        return def[index];
    }

    public messages getMessages() {return _messages; }
    public void setMessages(messages m){_messages = m;}

    @Override
    public String toString() {
        return _messages.toString();
    }
}
