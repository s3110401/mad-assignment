package student.rmit.edu.au.s3110401mad_assignment.model;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Michaelsun Baluyos on 27/08/2015.
 */
public interface Party {
    int getId();

    String getImDB();

    Calendar getDate();

    String getVenue();

    double[] getLocation();

//    void setLocation(double longitude, double latitude);
}
