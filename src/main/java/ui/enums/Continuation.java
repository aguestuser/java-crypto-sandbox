package ui.enums;

public enum Continuation {
    CONTINUE("continue", "c"),
    QUIT("quit", "q");
    
    public final String name;
    public final String flag;
    
    Continuation(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
}
