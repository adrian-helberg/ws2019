package ws1819.a2.donottouch.model.enums;

import static java.util.Arrays.asList;

public enum Sportart {
        FUSSBALL("Fußball"), HANDBALL("Handball"), VOLLEYBALL("Volleyball"), SCHIMMEN("Schwimmen"), LAUFEN("Laufen"), LEICHTATHLETIK("Leichtathletik"), TURNEN("Turnen"), WORKOUT("Workout");

        private final String name;

        Sportart(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    public static Sportart fromNameString(String name){
        return asList(values()).stream()
                .filter(vereinsName -> vereinsName.toString().equals(name)).findFirst().orElse(null);
    }
}
