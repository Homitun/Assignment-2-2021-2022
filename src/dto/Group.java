
package dto;

/**
 *
 * @author Walter White
 */
public class Group {
    private final int groupId;
    private final int groupSize;
    private final int[] subjectIds;

    public Group(int groupId, int groupSize, int moduleIds[]){
        this.groupId = groupId;
        this.groupSize = groupSize;
        this.subjectIds = moduleIds;
    }
    
    public int getGroupId(){
        return this.groupId;
    }
 
    public int getGroupSize(){
        return this.groupSize;
    }
        
    public int[] getSubjectIds(){
        return this.subjectIds;
    }
}
