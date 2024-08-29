public class Road implements Comparable<Road>{
    private String departurePoint;
    private String destinationPoint;
    private double distance;
    private double id;

    public Road(String departurePoint,String destinationPoint, double distance, double id) {
        this.destinationPoint=destinationPoint;
        this.distance = distance;
        this.departurePoint = departurePoint;
        this.id = id;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Road o) {
        int distanceComprasipon=Double.compare(this.distance,o.distance);
        if (distanceComprasipon!=0){
            return distanceComprasipon;
        }
        else {
            return Double.compare(this.id,o.id);
        }
    }

    @Override
    public String toString() {
        return "Road{" +
                "departurePoint='" + departurePoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", distance=" + distance +
                ", id=" + id +
                '}';
    }
}
