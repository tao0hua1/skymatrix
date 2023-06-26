package cn.seiua.skymatrix.message;

public class MessageBuilder {
    public static Message build(String name) {
        return new Message(name);
    }
}
