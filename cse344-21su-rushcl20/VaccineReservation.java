import java.sql.*;
import java.util.*;


public class VaccineReservation {

    public static void main(String[] args) throws Exception {



        try {
            VaccineReservationScheduler vrs = new VaccineReservationScheduler();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        List<VaccineCaregiver> caregiversList = new ArrayList<VaccineCaregiver>();
        caregiversList.add(new VaccineCaregiver("Carrie Nation"));
        caregiversList.add(new VaccineCaregiver("Clare Barton"));


    }


}

class VaccineReservationScheduler {



    private int slotSchedulingId;
    private String getAppointmentSQL;
    private SqlConnectionManager sqlClient;


    public VaccineReservationScheduler() throws Exception {
        this.sqlClient = new SqlConnectionManager(
                System.getenv("Server"),
                System.getenv("DBName"),
                System.getenv("UserID"),
                System.getenv("Password")
        );
        sqlClient.openConnection();
        this.slotSchedulingId = 0;
        this.getAppointmentSQL = "SELECT something...";
    }


    /* Method that reserves a CareGiver appointment slot &
    returns the unique scheduling slotid
    Should return 0 if no slot is available  or -1 if there is a database error*/

    public int putHoldOnAppointmentSlot() throws Exception {

        try {
            sqlClient.executeQuery(getAppointmentSQL);
            return slotSchedulingId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /*method that marks a slot on Hold with a definite reservation  
    slotid is the slot that is currently on Hold and whose status will be updated 
    returns the same slotid when the database update succeeds 
    returns 0 is there if the database update dails 
    returns -1 the same slotid when the database command fails
    returns -2 if the slotid parm is invalid*/
    public int ScheduleAppointmentSlot(int slotId) {
        if (slotId < 1) {
            return -2;
        }
        try {
            sqlClient.executeQuery(getAppointmentSQL);
            return slotSchedulingId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }


}
