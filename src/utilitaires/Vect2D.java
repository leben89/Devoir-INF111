package utilitaires;

import java.util.List;

public class Vect2D {
    double x;
    double y;

    public Vect2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    // constructeur par default
    public Vect2D() {
    }

    @Override
    public Vect2D clone()  {
        return new Vect2D(this.x,this.y);
    }

    // accesseur x
    public double getX() {
        return x;
    }

    public void setX(double x) {

        this.x = x;
    }
    // accesseur y

    public double getY() {
        return y;
    }

    public void setY(double y) {

        this.y = y;
    }

    public double getLongueur() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getAngle() {
        double angle = Math.atan2(y, x);
        return Math.toDegrees(angle);
    }

    public Vect2D calculerDiff(Vect2D posFin) {
        Vect2D vect2D = new Vect2D();
        vect2D.x = posFin.x - this.x;
        vect2D.y = posFin.y - this.y;
        return vect2D;
    }

    public void diviser(double a) {
        this.x = this.x / a;
        this.y = this.y / a;
    }

    public void ajouter(double x, double y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }

    @Override
    public String toString() {
        return "Vect2D(" + "x=" + x + ",y=" + y + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vect2D vect2D = (Vect2D) o;
        return Double.compare(x, vect2D.x) == 0 && Double.compare(y, vect2D.y) == 0;
    }

}
