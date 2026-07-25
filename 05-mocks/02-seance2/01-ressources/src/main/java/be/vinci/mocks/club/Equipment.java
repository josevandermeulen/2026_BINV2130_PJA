package be.vinci.mocks.club;

public class Equipment {
    private final String code;

    private final String name;

    private boolean borrowed;

    public Equipment(String code, String name) {
        this.code = code;
        this.name = name;
        this.borrowed = false;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBorrowed() {
        return this.borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public String toString() {
        return "Equipment{code='" + this.code + "', name='" + this.name + "', borrowed=" + this.borrowed + "}";
    }
}
