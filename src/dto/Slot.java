
package dto;

/**
 *
 * @author Walter White
 */
// class timeslot để biểu diễn thời gian

public class Slot {
    private final int slotId;
    private final String Date;
    private final String timeslot;

    public Slot(int timeslotId,String Date, String timeslot){
        this.slotId = timeslotId;
        this.timeslot = timeslot;
        this.Date = Date;
    }
    public String getSlotDate(){
    return this.Date;
}
    public int getSlotId(){
        return this.slotId;
    }

    public String getTimeslot(){
        return this.timeslot;
    }
}


