
package dao;
/**
 *
 * @author Walter White
 */

import dto.*;

import java.util.HashMap;
public class Timetable {
	private final HashMap<Integer, classRoom> rooms;
	private final HashMap<Integer, Lecturer> lecturer;
	private final HashMap<Integer, Subject> subject;
	private final HashMap<String, Group> groups;
	private final HashMap<Integer, Slot> slots;
	private FPTClass classes[];
	private int numClasses = 0;
	public Timetable() {
		this.rooms = new HashMap<Integer, classRoom>();
		this.lecturer = new HashMap<Integer, Lecturer>();
		this.subject = new HashMap<Integer, Subject>();
		this.groups = new HashMap<String, Group>();
		this.slots = new HashMap<Integer, Slot>();
	}
public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.lecturer = cloneable.getLecturer();
		this.subject = cloneable.getSubject();
		this.groups = cloneable.getGroups();
		this.slots = cloneable.getSlots();
	}

    public HashMap<Integer, Lecturer> getLecturer() {
        return lecturer;
    }

    public HashMap<Integer, Subject> getSubject() {
        return subject;
    }

    public HashMap<Integer, Slot> getSlots() {
        return slots;
    }

    private HashMap<String, Group> getGroups() {
	return this.groups;
    }
// addnew room 
	public void addRoom(int roomId, String roomNumber, int capacity) {
		this.rooms.put(roomId, new classRoom(roomId, roomNumber,capacity));
	}
// add new lecturer
	public void addLecturer(int lecturerId, String lecturerName) {
            this.lecturer.putIfAbsent(lecturerId, new Lecturer(lecturerId, lecturerName));
		
	}
// add new subject
	public void addSubject(int subjectId, String subjectCode, String subjectName, int lecturerIds[]) {
		this.subject.putIfAbsent(subjectId, new Subject(subjectId, subjectCode, subjectName, lecturerIds));
	}
// add group
	public void addGroup(String groupId, int groupSize, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, groupSize, moduleIds));
		this.numClasses = 0;
	}
// add slot
	public void addSlot(int slotId,String Date, String timeslot) {
		this.slots.putIfAbsent(slotId, new Slot(slotId,Date, timeslot));
	}

	// tạo class 
	public void createClasses(Individual individual) {
		// Init classes
                
		FPTClass classes[] = new FPTClass[this.getNumClasses()];

		// Get individual's chromosome
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;

		for (Group group : this.getGroupsAsArray()) {
			int[] subjectIds = group.getSubjectIds();
			for (int subjectId : subjectIds) {
				classes[classIndex] = new FPTClass(classIndex, group.getGroupId(), subjectId);

				// Add timeslot
				classes[classIndex].addSlotId(chromosome[chromosomePos]);
				chromosomePos++;

				// Add room
				classes[classIndex].addRoomId(chromosome[chromosomePos]);
				chromosomePos++;

				// Add professor
				classes[classIndex].addLecturerId(chromosome[chromosomePos]);
				chromosomePos++;

				classIndex++;
			}
		}

		this.classes = classes;
	}

// search classRoom từ classroom id
	public classRoom getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("Rooms doesn't contain key " + roomId);
		}
		return (classRoom) this.rooms.get(roomId);
	}
	public HashMap<Integer, classRoom> getRooms() {
		return this.rooms;
	}
// get randome room
	public classRoom getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		classRoom room = (classRoom) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}
// tìm lecturer từ lecturer id
	public Lecturer getLecturer(int lecturerId) {
		return (Lecturer) this.lecturer.get(lecturerId);
	}
// tìm subject từ subject id
	public Subject getSubject(int subjectId) {
		return (Subject) this.subject.get(subjectId);
	}
//tìm subject Id từ group id
	public int[] getGroupSubject(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getSubjectIds();
	}
// tìm group từ group id
	public Group getGroup(String groupId) {
		return (Group) this.groups.get(groupId);
	}

	/**
	 * Get all student groups
	 * 
	 * @return array of groups
	 */
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}
// get slot by slot id
	public Slot getTimeslot(int slotId) {
		return (Slot) this.slots.get(slotId);
	}
//Get random timeslotId
	public Slot getRandomTimeslot() {
		Object[] timeslotArray = this.slots.values().toArray();
		Slot timeslot = (Slot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	/**
	 * Get classes
	 * 
	 * @return classes
	 */
	public FPTClass[] getClasses() {
		return this.classes;
	}

	/**
	 * Get number of classes that need scheduling
	 * 
	 * @return numClasses
	 */
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getSubjectIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	
	public int calcClashes() {
		int clashes = 0;

		for (FPTClass classA : this.classes) {
			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
			int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();
			
			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if room is taken
			for (FPTClass classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getSlotId() == classB.getSlotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}

			// Check if professor is available
			for (FPTClass classB : this.classes) {
				if (classA.getLecturerId() == classB.getLecturerId() && classA.getLecturerId() == classB.getSlotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
		}

		return clashes;
	}
}

