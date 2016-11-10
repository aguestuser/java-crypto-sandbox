package crypto;

public enum CypherType {
    CAESAR("Caesar Cypher", 1),
    NACL_SYMMETRIC("NaCl Symmetric", 2);
    
    public final String str;
    public final int num;
    
    CypherType(String str, int num) {
        this.str = str;
        this.num = num;
    }
}