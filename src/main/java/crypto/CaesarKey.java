package crypto;

public final class CaesarKey extends Key {
    
    private final int value;
    
    public CaesarKey(int value){
        this.value = value;
    }
    
    @Override
    public Integer get(){
        return this.value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaesarKey)) return false;
        
        CaesarKey caesarKey = (CaesarKey) o;
    
        return value == caesarKey.value;
    
    }
    
    @Override
    public int hashCode() {
        return value;
    }
}

