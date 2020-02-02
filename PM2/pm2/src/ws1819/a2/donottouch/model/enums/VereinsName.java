package ws1819.a2.donottouch.model.enums;

import java.util.Arrays;

public enum VereinsName {
    FIT12("Fit12"), LAZYCROWD("Lazy Crowd"), COUCHPOTATOES("Couch Potatoes"), STRONGPERFORMER("Strong Performers"), MOVERS("Movers");

    private final String name;

    VereinsName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    public static VereinsName fromNameString(String name){
        return Arrays.asList(values()).stream()
                .filter(vereinsName -> vereinsName.toString().equals(name)).findFirst().orElse(null);
    }
}
