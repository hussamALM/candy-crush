package candycrush;

public class Orb {

    String color = null;
    boolean status = true;

    public Orb(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public void setStatus() {
        this.status = false;
    }
    public void setColor() {
        this.color = "null";
    }


    @Override
    public String toString() {
        return this.color;
    }
}
