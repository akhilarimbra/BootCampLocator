package me.akhilarimbra.bootcamplocator.services;

import java.util.ArrayList;

import me.akhilarimbra.bootcamplocator.model.Devslopes;

/**
 * Created by akhilraj on 10/12/16.
 */
public class DataService {
    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {
        
    }

    public ArrayList<Devslopes> getBootCampLocationsWithInTenMilesOfZip(int zip) {
        //pretending we are downloading data from the server

        ArrayList<Devslopes> list = new ArrayList<>();
        list.add(new Devslopes(10.949382f, 76.137491f, "Federal Bank ATM", "Kolathur Malappuram Rd, Kolathur, Kerala 679338", "map_pin"));
        list.add(new Devslopes(10.947259f, 76.138258f, "Jabal Bakery", "Kolathur Malappuram Rd, Kolathur, Kerala 679338", "map_pin"));
        list.add(new Devslopes(10.947196f, 76.137550f,
                "Kolathur Post Office", "Kolathur Malappuram Rd, Kolathur, Kerala 679338", "map_pin"));

        return list;
    }
}
