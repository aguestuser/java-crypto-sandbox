package ui.enums;

public enum CypherType {
    SYMMETRIC("Symmetric Cypher", 1),
    ASYMMETRIC("Asymmetric Cypher", 2);

    public final String str;
    public final int num;
    
    CypherType(String str, int num) {
        this.str = str;
        this.num = num;
    }
}