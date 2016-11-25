package crypto;

public enum CypherMode {
    ENCRYPT("encrypt", "e"),
    DECRYPT("decrypt", "d");
    
    public final String name;
    public final String flag;
    
    CypherMode(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
    
}
