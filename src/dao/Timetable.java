
package dao;
/**
 *
 * @author Walter White
 */

import dto.*;

import java.util.HashMap;

/**
 * Timetable is the main evaluation class for the class scheduler GA.
 * 
 * A timetable represents a potential solution in human-readable form, unlike an
 * Individual or a chromosome. This timetable class, then, can read a chromosome
 * and develop a timetable from it, and ultimately can evaluate the timetable
 * for its fitness and number of scheduling clashes.
 * 
 * The most important methods in this class are createClasses and calcClashes.
 * 
 * The createClasses method accepts an Individual (really, a chromosome),
 * unpacks its chromosome, and creates Class objects from the genetic
 * information. Class objects are lightweight; they're just containers for
 * information with getters and setters, but it's more convenient to work with
 * them than with the chromosome directly.
 * 
 * The calcClashes method is used by GeneticAlgorithm.calcFitness, and requires
 * that createClasses has been run first. calcClashes looks at the Class objects
 * created by createClasses, and figures out how many hard constraints have been
 * violated.
 * 
 */
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

	/**
	 * Create classes using individual's chromosome
	 * 
	 * One of the two important methods in this class; given a chromosome,
	 * unpack it and turn it into an array of Class (with a capital C) objects.
	 * These Class objects will later be evaluated by the calcClashes method,
	 * which will loop through the Classes and calculate the number of
	 * conflicting timeslots, rooms, professors, etc.
	 * 
	 * While this method is important, it's not really difficult or confusing.
	 * Just loop through the chromosome and create Class objects and store them.
	 * 
	 * @param individual
	 */
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

	/**
	 * Calculate the number of clashes between Classes generated by a
	 * chromosome.
	 * 
	 * The most important method in this class; look at a candidate timetable
	 * and figure out how many constraints are violated.
	 * 
	 * Running this method requires that createClasses has been run first (in
	 * order to populate this.classes). The return value of this method is
	 * simply the number of constraint violations (conflicting professors,
	 * timeslots, or rooms), and that return value is used by the
	 * GeneticAlgorithm.calcFitness method.
	 * 
	 * There's nothing too difficult here either -- loop through this.classes,
	 * and check constraints against the rest of the this.classes.
	 * 
	 * The two inner `for` loops can be combined here as an optimization, but
	 * kept separate for clarity. For small values of this.classes.length it
	 * doesn't make a difference, but for larger values it certainly does.
	 * 
	 * @return numClashes
	 */
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

