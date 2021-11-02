
package dto;

/**
 *
 * @author Walter White
 */
// class timeslot để biểu diễn thời gian

public class Slot {
    private final int slotId;
    private final String timeslot;

    public Slot(int timeslotId, String timeslot){
        this.slotId = timeslotId;
        this.timeslot = timeslot;
    }
 
    public int getSlotId(){
        return this.slotId;
    }

    public String getTimeslot(){
        return this.timeslot;
    }
}


