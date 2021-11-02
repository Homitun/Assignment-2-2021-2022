
package dto;

/**
 *
 * @author Walter White
 */

public class FPTClass {
    private final int classId;
    private final String groupId;
    private final int subjectId;
    private int lecturerId;
    private int slotId;
    private int roomId;

    public FPTClass(int classId, String groupId, int subjectId) {
        this.classId = classId;
        this.groupId = groupId;
        this.subjectId = subjectId;
    }
// add lecturer to class
    
    public void addLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }
// add slot
    public void addSlotId(int slotId) {
        this.slotId = slotId;
    }
 // add room to class
    public void addRoomId(int roomId){
        this.roomId = roomId;
    }
    
    public int getClassId(){
        return this.classId;
    }
   
    public String getGroupId(){
        return this.groupId;
    }

    public int getSubjectId(){
        return this.subjectId;
    }

    public int getLecturerId(){
        return this.lecturerId;
    }
    
    public int getSlotId(){
        return this.slotId;
    }

    public int getRoomId(){
        return this.roomId;
    }
}



