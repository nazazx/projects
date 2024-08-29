import java.util.*;

public class Path implements Comparable {
    private double totalDistance;
    private List<Road> roadList;
    private Set<String> visitedCities;

    public Path(double totalDistance, Road road,Set<String> visitedCities) {
        this.totalDistance = totalDistance;
        this.roadList = new ArrayList<>();
        this.visitedCities = new HashSet<>(visitedCities);
        roadList.add(road);
        this.visitedCities.add(road.getDestinationPoint());
    }


    public Path(double totalDistance, List<Road> roads,Road road,Set<String> visitedCities) {
        this.totalDistance = totalDistance;
        this.roadList = roads;
        this.visitedCities = new HashSet<>(visitedCities);
        roadList.add(road);

    }

    public List<Road> getRoadList() {
        return roadList;
    }

    public void setRoadList(List<Road> roadList) {
        this.roadList = roadList;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
    public void addToList(Road road){
        roadList.add(road);
        totalDistance+=road.getDistance();
    }

    @Override
    public String toString() {
        return "Path{" +
                "totalDistance=" + totalDistance +
                ", roadList=" + roadList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Double.compare(totalDistance, path.totalDistance) == 0 && Objects.equals(roadList, path.roadList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalDistance, roadList);
    }

    public Set<String> getVisitedCities() {
        return visitedCities;
    }

    public void setVisitedCities(Set<String> visitedCities) {
        this.visitedCities = visitedCities;
    }



    @Override
    public int compareTo(Object o) {
        return Double.compare(this.totalDistance,((Path) o).getTotalDistance());
    }
}
