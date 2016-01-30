package Main;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Landfläche;
import AllThoseTerritories.Territorium;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thomas on 03/01/2016.
 */
public class IOHelper {
    public static List<Kontinent> createPlayingFieldsFromFile(String file) {
        // TODO: Add I/O logik and return a new list containing all the continents of the file.
        //return null;
        /*List<Point>points=new LinkedList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(200,400));
        points.add(new Point(500,600));
        points.add(new Point(500,90));

        List<Landfläche>land=new LinkedList<Landfläche>();
        land.add(new Landfläche(points));
        List<Territorium>test=new LinkedList<Territorium>();
        test.add(new Territorium(land,"TEST",new Point(100,100),null,new Armee(land.get(0),true,2)));
        List<Kontinent>toRet=new LinkedList<Kontinent>();
        toRet.add(new Kontinent(test));
        return toRet;

        ^- Test implementation
        */

        List<Kontinent> toRet = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = "";
            List<Territorium> terrs = new LinkedList<>();


            while ((line = reader.readLine()) != null) {
                String infos[] = line.split("\\s+"); // splits the line at every splace

                switch (infos[0]) {
                    case "patch-of":// adds a country to a 'Territorium'
                        // Get the name of the current Territorium
                        int offset = 1;
                        String name = infos[1];
                        while (1 + offset < infos.length && !infos[1 + offset].matches("^-?\\d+$")) { // if it isn't an integer
                            name += " " + infos[1 + offset];
                            offset++;
                        }
                        offset--;

                        // Find the Territorium in our temp list of territoriums (terrs)
                        Territorium testTerr = getTerritoriumByName(terrs, name);

                        if (testTerr != null) { // if it does exist, just add another country
                            testTerr.addCountry(getLandflächeFromStringArray(infos, offset + 2));
                            break;
                        }

                        // else add a new Territorium containing the new country
                        terrs.add(new Territorium(getLandflächeFromStringArray(infos, offset + 2), name, new Point(0, 0), null, new Armee(null, 0)));
                        break;
                    case "capital-of": // sets the capital of a 'Territorium'
                        if (infos.length > 3) { // if there are enough infos...
                            // than get the name
                            offset = 1;
                            name = infos[1];
                            while (1 + offset < infos.length && !infos[1 + offset].matches("^-?\\d+$")) { // if it isn't an integer
                                name += " " + infos[1 + offset];
                                offset++;
                            }
                            offset--;

                            // find the Territorium in our temp list of territoriums (terrs)
                            testTerr = getTerritoriumByName(terrs, name);
                            if (testTerr != null) { // and if it contains our target, than set the capital of it.
                                testTerr.capital = new Point(Integer.parseInt(infos[2 + offset]), Integer.parseInt(infos[3 + offset]));
                                break;
                            }

                            // else create a new one
                            terrs.add(new Territorium(null, name, new Point(Integer.parseInt(infos[2 + offset]), Integer.parseInt(infos[3 + offset])), null, new Armee(null, 0)));
                        }

                        break;
                    case "neighbors-of": // connects mulitple 'Territorium'
                        // Get the name
                        offset = 1;
                        name = infos[1];
                        while (1 + offset < infos.length && !infos[1 + offset].equals(":")) {
                            name += " " + infos[1 + offset];
                            offset++;
                        }
                        offset--;

                        // Create a string, that is better to work with, because I'll just split it at " - " wich will than returns every name to add, but if I would
                        // work with the string line, I would also get the name of the main Territorium.
                        // Example: "neighbors-of TEST : AHA das - Austria"
                        // if I would split it with the (string) line, I would get: "neighbors-of TEST : AHA das", "Austria"
                        // if I would split it with the (string) substring (which would be "AHA - Austria"), I would get: "AHA das", "Austria" (which is perfect)
                        String substringing = line;
                        while (substringing.charAt(0) != ':') {
                            substringing = substringing.substring(1);
                        }

                        substringing = substringing.substring(2);
                        String otherSplited[] = substringing.split(" - ");
                        testTerr = getTerritoriumByName(terrs, name);

                        if (testTerr != null) { // if the main territory exists, than add the territories (and if they don't exist, just create than and add than to terrs and the main Territory
                            for (int i = 0; i < otherSplited.length; i++) {
                                Territorium neigh = getTerritoriumByName(terrs, otherSplited[i]);

                                if (neigh == null) {
                                    neigh = new Territorium(null, otherSplited[i], new Point(0, 0), null, new Armee(null, 0));
                                    terrs.add(neigh);
                                }

                                neigh.addNeighour(testTerr);
                            }

                            break;
                        }

                        testTerr = new Territorium(null, name, new Point(0, 0), null, new Armee(null, 0));
                        terrs.add(testTerr);
                        for (int i = 0; i < otherSplited.length; i++) {
                            Territorium neigh = getTerritoriumByName(terrs, otherSplited[i]);

                            if (neigh == null) {
                                neigh = new Territorium(null, otherSplited[i], new Point(0, 0), null, new Armee(null, 0));
                                terrs.add(neigh);
                            }

                            neigh.addNeighour(testTerr);
                        }

                        break;
                    case "continent": // creates a continent containing these Territories
                        offset = 1;
                        boolean exists4 = false;
                        name = infos[1];
                        while (1 + offset < infos.length && !infos[1 + offset].matches("^-?\\d+$")) { // if it isn't a integer
                            name += " " + infos[1 + offset];
                            offset++;
                        }
                        offset--;
                        int value = Integer.parseInt(infos[2 + offset]);
                        substringing = line;
                        while (substringing.charAt(0) != ':') {
                            substringing = substringing.substring(1);
                        }

                        substringing = substringing.substring(2);
                        otherSplited = substringing.split(" - ");
                        Kontinent testCon = getKontinentByName(toRet, name);

                        if (testCon != null) {
                            for (int i = 0; i < otherSplited.length; i++) {
                                testTerr = getTerritoriumByName(terrs, otherSplited[i]);

                                if (testTerr == null) {
                                    testTerr = new Territorium(null, otherSplited[i], new Point(0, 0), null, new Armee(null, 0));
                                }

                                testCon.addTerritory(testTerr);
                            }

                            break;
                        }

                        testCon = new Kontinent(name, value);
                        toRet.add(testCon);
                        for (int i = 0; i < otherSplited.length; i++) {
                            testTerr = getTerritoriumByName(terrs, otherSplited[i]);

                            if (testTerr == null) {
                                testTerr = new Territorium(null, otherSplited[i], new Point(0, 0), null, new Armee(null, 0));
                            }

                            testCon.addTerritory(testTerr);
                        }
                        break;
                    default: // Something that shouldn't be triggered
                        break;
                }
            }

            if (toRet.size() == 0 && terrs.size() > 0) { // if there are no continents but some Territoriums, than just create a temp continent
                Kontinent temp = new Kontinent("TEMP", 0);
                for (Territorium ter :
                        terrs) {
                    temp.addTerritory(ter);
                }
                toRet.add(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toRet;
    }

    private static Territorium getTerritoriumByName(List<Territorium> terrs, String name) {
        for (Territorium t :
                terrs) {
            if (t.name.equals(name)) {
                return t;
            }
        }

        return null;
    }

    private static Kontinent getKontinentByName(List<Kontinent> continents, String name) {
        for (Kontinent c :
                continents) {
            if (c.name.equals(name)) {
                return c;
            }
        }

        return null;
    }

    private static Landfläche getLandflächeFromStringArray(String[] infos, int offset) {
        List<Point> pointsToAdd = new LinkedList<>();
        for (int i = offset; i < infos.length; i += 2) {
            if (infos.length > (i + 1)) {
                pointsToAdd.add(new Point(Integer.parseInt(infos[i]), Integer.parseInt(infos[i + 1])));
            }
        }

        return new Landfläche(pointsToAdd);
    }
}
