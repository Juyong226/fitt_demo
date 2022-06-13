package myprojects.fittdemo.api;

import lombok.Data;

@Data
public class MessageAndRedirection {
    private String message;
    private String url;

    public MessageAndRedirection(String message) {
        setMessage(message);
    }

    public MessageAndRedirection(String message, String url) {
        this(message);
        setUrl(url);
    }
}
