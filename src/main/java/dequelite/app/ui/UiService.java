package dequelite.app.ui;

public class UiService {
    public void clientMessage(String clientIp, String message) {
        System.out.print("\r");
        System.out.println("Message: " + clientIp + " " + message);
        System.out.print("> ");
    }

    public void myMessage(){
        System.out.print("> ");
    }
}
