package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x;
    private float y; //Максимальное значение поля: 266
    private static final long serialVersionUID = 1L;

    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "data.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public String toCsv() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
    }


}
