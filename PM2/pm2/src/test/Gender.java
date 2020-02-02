package test;

public class Gender {
    public static final Gender MALE = new Gender("Male");
    public static final Gender FEMALE = new Gender("Female");
    public static final Gender DIVERSE = new Gender("Diverse");
    private String name;

    private Gender(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
