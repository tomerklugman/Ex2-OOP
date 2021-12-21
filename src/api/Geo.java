package api;

public class Geo implements GeoLocation{

    double x;
    double y;
    double z;

    public Geo(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;

    }
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) { //sqrt[(x2-x1)^2+(y2-y1)^2+(z2-z1)^2
        return Math.sqrt(Math.pow(this.x-g.x(),2)+Math.pow(this.y-g.y(),2)+Math.pow(this.z-g.z(),2));
    }
}
