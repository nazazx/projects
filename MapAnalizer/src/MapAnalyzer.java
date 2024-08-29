import java.util.*;

public class MapAnalyzer {
    private static ArrayList<Road> roads = new ArrayList<>();
    private static ArrayList<Road> roads1 = new ArrayList<>();
    private static String mainDeparture;
    private static String mainDestination;
    private static List<Path> paths=new ArrayList<>();
    private static Map<String, Set<String>> connections = new HashMap<>();
    private static double fastestWayDistanceForPreMadeMap;
    private static double fastestWayDistanceForNewMap;

    public static void main(String[] args) {
        String input=args[0];
        String output=args[1];

        readFileAndLoadingRoads(input);
        roads.sort(Road::compareTo);
        roads1.sort(Road::compareTo);



        startPath(roads,paths);
        printFastestWay(roads,paths,output);

     printNewMap(output);

    }


    /**
     * Reads the file from the given path and loads the roads into the road list and road1 list .
     *
     * @param path1 The file path to read from.
     */
    public static void readFileAndLoadingRoads(String path1) {
        String[] File = FileInput.readFile(path1, true, true);
        String fileContents = "";
        for (String i : File) {
            fileContents += i + "\n";
        }
        String[] lines = fileContents.split("\\n");
        int n = 0;
        for (String line : lines) {
            n++;
            if (n == 1) {
                mainDeparture = line.split("\t")[0];
                mainDestination = line.split("\t")[1];
            } else {
                String departure = line.split("\t")[0];
                String destination = line.split("\t")[1];
                double distance = Double.valueOf(line.split("\t")[2]);
                int id = Integer.valueOf(line.split("\t")[3]);

                roads.add(new Road(departure, destination, distance, id));
                roads.add(new Road(destination, departure, distance, id));
                roads1.add(new Road(departure, destination, distance, id));


            }

        }
    }

    /**
     * Starts the path.
     *
     * @param innerRoads List of roads to use for starting the path.
     * @param innerPaths List of initial paths to add to.
     */
    public static void startPath(ArrayList<Road> innerRoads,List<Path> innerPaths){
        for (Road road:innerRoads){
            if (road.getDeparturePoint().equals(mainDeparture)){
                Set<String> visited = new HashSet<>();
                visited.add(mainDeparture);
                innerPaths.add(new Path(road.getDistance(),road,visited));
            }
        }

    }

    /**
     * Recursive method to find all paths from start to destination,also it find the fastest way.
     * @param path Current path being expanded.
     * @param innerRoads List of all roads.
     * @param innerPaths List to store all paths.
     */
    public static void findingPaths(Path path,ArrayList<Road> innerRoads,List<Path> innerPaths) {
        String destination = path.getRoadList().get(path.getRoadList().size() - 1).getDestinationPoint();
        if (destination.equals(mainDestination)) {
            return;
        }

        for (Road road : innerRoads) {
            if (road.getDeparturePoint().equals(destination) && !path.getVisitedCities().contains(road.getDestinationPoint())) {
                Path newPath = new Path(path.getTotalDistance() + road.getDistance(), new ArrayList<>(path.getRoadList()), road, new HashSet<>(path.getVisitedCities()));
                newPath.getVisitedCities().add(road.getDestinationPoint());
                if (!innerPaths.contains(newPath)){
                    findingPaths(newPath,innerRoads,innerPaths);
                    innerPaths.add(newPath);
                    break;
                }

            }
        }

    }

    /**
     * Helper method to determine if a road is already considered in a path to avoid duplication.
     * @param road1 The road to check.
     * @param innerRoads List of all roads for reference.
     * @return The first instance of the road based on order or null if not found.
     */
    public static Road printRoadControl(Road road1,ArrayList<Road> innerRoads){
        for (Road road:innerRoads){
            if (road.getId()==road1.getId()){
                if (innerRoads.indexOf(road)<innerRoads.indexOf(road1)){
                    return road;
                }
                else{
                    return road1;
                }
            }
        }
        return null;
    }

    /**
     * prints the fastest way from the departure to the destination.
     * @param innerRoads List of all roads.
     * @param innerPaths List of all paths.
     * @param path2 Output file path to write the fastest route.
     */
    public static void printFastestWay(ArrayList<Road> innerRoads,List<Path> innerPaths,String path2){
        for (int i=0;i< innerPaths.size();i++){
            findingPaths(innerPaths.get(i),innerRoads,innerPaths);
        }
        innerPaths.sort(Path::compareTo);


        for (Path path:innerPaths){
            if ((path.getRoadList().get(path.getRoadList().size()-1).getDestinationPoint().equals(mainDestination))){
                String totalDistance=String.format("%.0f",path.getTotalDistance());
                FileOutput.writeToFile(path2,"Fastest Route from "+ mainDeparture +" to " +mainDestination +" ("+totalDistance+" KM):",true,true);

                for (Road road:path.getRoadList()){
                    Road road1=printRoadControl(road,roads);
                    fastestWayDistanceForPreMadeMap+=road1.getDistance();
                    String departure= road1.getDeparturePoint();
                    String destination=road1.getDestinationPoint();
                    String distance=String.format("%.0f",road1.getDistance());
                    String id=String.format("%.0f",road1.getId());
                    FileOutput.writeToFile(path2,departure+"\t"+ destination+"\t"+distance+"\t"+id,true,true);

                }
                break;
            }

        }


    }

    /**
     * Adds a connection between two cities.
     * @param city1 The first city in the connection.
     * @param city2 The second city in the connection.
     */
    public static void addConnection(String city1, String city2) {
        if (!connections.containsKey(city1)) {
            connections.put(city1, new HashSet<String>());
        }
        if (!connections.containsKey(city2)) {
            connections.put(city2, new HashSet<String>());
        }
        connections.get(city1).add(city2);
        connections.get(city2).add(city1);
    }


    /**
     * Checks if there is a connection between two points.
     * @param startPoint The starting point of the connection.
     * @param endPoint The endpoint of the connection.
     * @return True if a connection exists, otherwise false.
     */
    public  static boolean isThereConnection(String startPoint,String endPoint){
        Set<String> visited = new HashSet<>();
        return controlConnection(startPoint, endPoint, visited);
    }


    /**
     * Recursively checks for a connection between two points.
     * @param currentPoint The current point being checked.
     * @param endPoint The endpoint we are trying to reach.
     * @param visited A set of already visited points to avoid loops.
     * @return True if a connection is found, otherwise false.
     */
    public static boolean controlConnection(String currentPoint,String endPoint,Set<String> visited){
        if (currentPoint.equals(endPoint)){
            return true;
        }
        visited.add(currentPoint);

        Set<String> neighbors=connections.get(currentPoint);
        if (neighbors!=null){
            for (String n:neighbors){
                if ((!visited.contains(n))&&controlConnection(n,endPoint,visited)){
                    return true;
                }
            }
        }
        return false;
    }



    public static Set<String> points(){
        roads1.sort(Road::compareTo);
        Set<String> points=new TreeSet<>();
        for (Road road:roads1){
            points.add(road.getDeparturePoint());
            points.add(road.getDestinationPoint());
        }
        return points;
    }


    /**
     * Creates a new map ensuring minimal connectivity between all points.
     * @return A list of roads that represent the barely connected map.
     */
    public static ArrayList<Road> createMap(){
        Set<String> points=points();
        ArrayList<Road> map=new ArrayList<>();
        for (Road road:roads1){
            if (!isThereConnection(road.getDeparturePoint(),road.getDestinationPoint())){
                addConnection(road.getDeparturePoint(),road.getDestinationPoint());
                map.add(road);
            }
            if (map.size()==points.size()+2){
                return map;
            }}
        return map;
    }

    /**
     * Prints the new map to the output file.
     * @param path2 The output file path where the new map will be written.
     */
    public static void printNewMap(String path2){
        ArrayList<Road> map=createMap();
        map.sort(Road::compareTo);
        int mapSize= map.size();

        List<Path> innerPaths=new ArrayList<>();
        FileOutput.writeToFile(path2,"Roads of Barely Connected Map is:",true,true);

        for (Road road :map){
            String departure= road.getDeparturePoint();
            String destination=road.getDestinationPoint();
            String distance=String.format("%.0f",road.getDistance());
            String id=String.format("%.0f",road.getId());
            FileOutput.writeToFile(path2,departure+"\t"+ destination+"\t"+distance+"\t"+id,true,true);

        }
        for (int i=0;i<mapSize;i++){
            String departure= map.get(i).getDeparturePoint();
            String destination= map.get(i).getDestinationPoint();
            double distance= map.get(i).getDistance();
            double id= map.get(i).getId();
            map.add(new Road(destination,departure,distance,id));
        }
        printFastestWayInNewMap(map,innerPaths,path2);

    }


    /**
     * Computes and prints the fastest way from departure to destination on the barely connected map.
     * @param map The road map.
     * @param innerPaths List of paths computed.
     * @param path2 The output file path where the fastest route will be written.
     */
    public  static void printFastestWayInNewMap(ArrayList<Road> map,List<Path> innerPaths,String path2){

        startPath(map,innerPaths);
        for (int i=0;i< innerPaths.size();i++){
            findingPaths(innerPaths.get(i),map,innerPaths);
        }

        innerPaths.sort(Path::compareTo);


        for (Path path:innerPaths){
            if ((path.getRoadList().get(path.getRoadList().size()-1).getDestinationPoint().equals(mainDestination))){
                String totalDistance=String.format("%.0f",path.getTotalDistance());
                FileOutput.writeToFile(path2,"Fastest Route from "+ mainDeparture +" to " +mainDestination +" on Barely Connected Map ("+totalDistance+" KM):",true,true);

                for (Road road:path.getRoadList()){
                    Road road1=printRoadControl(road,map);
                    fastestWayDistanceForNewMap+=road1.getDistance();
                    String departure= road1.getDeparturePoint();
                    String destination=road1.getDestinationPoint();
                    String distance=String.format("%.0f",road1.getDistance());
                    String id=String.format("%.0f",road1.getId());
                    FileOutput.writeToFile(path2,departure+"\t"+ destination+"\t"+distance+"\t"+id,true,true);

                }
                break;
            }

        }
        analize(map,innerPaths,path2);
    }


    /**
     * Analyzes and compares the original and newly created barely connected maps.
     * @param map The new road map.
     * @param innerPaths List of paths computed on the new map.
     * @param path2 The output file path where the analysis will be written.
     */
    public static void analize(ArrayList<Road> map,List<Path> innerPaths,String path2){
        double totalCostPreMadeMap=0;
        double totalCostForNewMap=0;
        for (Road road: map){
            totalCostForNewMap+=road.getDistance();
        }
        for (Road road:roads1){
            totalCostPreMadeMap+=road.getDistance();
        }
        totalCostForNewMap=totalCostForNewMap/2;
        String ratio=String.format(Locale.US,"%.2f",totalCostForNewMap/totalCostPreMadeMap);
        FileOutput.writeToFile(path2,"Analysis:",true,true);

        FileOutput.writeToFile(path2,"Ratio of Construction Material Usage Between Barely Connected and Original Map: "+ratio,true,true);


        String ratio2=String.format(Locale.US,"%.2f",fastestWayDistanceForNewMap/fastestWayDistanceForPreMadeMap);

        FileOutput.writeToFile(path2,"Ratio of Fastest Route Between Barely Connected and Original Map: "+ratio2,true,false);

    }
}