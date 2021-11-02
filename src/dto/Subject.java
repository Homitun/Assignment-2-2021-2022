
package dto;

/**
 *
 * @author Walter White
 */
public class Subject {
    private final int subjectId;
    private final String subjectCode;
    private final String subjectName;
    private final int lecturerIds[];

    public Subject(int subjectId, String subjectCode, String subjectName, int[] lecturerIds) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.lecturerIds = lecturerIds;
    }


    public int getSubjectId(){
        return this.subjectId;
    }
  
    public String getSubjectCode(){
        return this.subjectCode;
    }
    
    public String getSubjectName(){
        return this.subjectName;
    }

    public int getRandomLecturerId(){
        int lecturerId = lecturerIds[(int) (lecturerIds.length * Math.random())];
        return lecturerId;
    }
}
