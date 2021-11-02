/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.GeneticAlgorithm;
import dao.Population;
import dao.Timetable;
import dto.FPTClass;

/**
 *
 * @author Walter White
 */

/**
 * Don't be daunted by the number of classes in this chapter -- most of them are
 * just simple containers for information, and only have a handful of properties
 * with setters and getters.
 * 
 * The real stuff happens in the GeneticAlgorithm class and the Timetable class.
 * 
 * The Timetable class is what the genetic algorithm is expected to create a
 * valid version of -- meaning, after all is said and done, a chromosome is read
 * into a Timetable class, and the Timetable class creates a nicer, neater
 * representation of the chromosome by turning it into a proper list of Classes
 * with rooms and professors and whatnot.
 * 
 * The Timetable class also understands the problem's Hard Constraints (ie, a
 * professor can't be in two places simultaneously, or a room can't be used by
 * two classes simultaneously), and so is used by the GeneticAlgorithm's
 * calcFitness class as well.
 * 
 * Finally, we overload the Timetable class by entrusting it with the
 * "database information" generated here in initializeTimetable. Normally, that
 * information about what professors are employed and which classrooms the
 * university has would come from a database, but this isn't a book about
 * databases so we hardcode it.
 * 
 * @author bkanber
 *
 */
public class TimetableGA {

    public static void main(String[] args) {
    	// Get a Timetable object with all the available information.
        Timetable timetable = initializeTimetable();
        
        // Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 2, 5);
        
        // Initialize population
        Population population = ga.initPopulation(timetable);
        
        // Evaluate population
        ga.evalPopulation(population, timetable);
        
        // Keep track of current generation
        int generation = 1;
        
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, 1000) == false
            && ga.isTerminationConditionMet(population) == false) {
            // Print fitness
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

        // Print fitness
        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print classes
        System.out.println();
        FPTClass classes[] = timetable.getClasses();
        int classIndex = 1;
        for (FPTClass bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + 
                    timetable.getSubject(bestClass.getSubjectId()).getSubjectName());
            System.out.println("Group: " + 
                    timetable.getGroup(bestClass.getGroupId()).getGroupId());
            System.out.println("Room: " + 
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());                            
            System.out.println("Professor: " + 
                    timetable.getLecturer(bestClass.getLecturerId()).getLecturerName());
            System.out.println("Time: " + 
                    timetable.getTimeslot(bestClass.getSlotId()).getTimeslot());
          
            System.out.println("-----");
            classIndex++;
        }
    }

    /**
     * Creates a Timetable with all the necessary course information.
     * 
     * Normally you'd get this info from a database.
     * 
     * @return
     */
	private static Timetable initializeTimetable() {
		// Create timetable
		Timetable timetable = new Timetable();

		// Set up rooms
		timetable.addRoom(1, "A1", 15);
		timetable.addRoom(2, "B1", 30);
		timetable.addRoom(4, "D1", 20);
		timetable.addRoom(5, "F1", 25);

		// Set up timeslots
                timetable.addSlot(1, "Mon", "7:00-8:30:00" );
                timetable.addSlot(2, "Mon", "8:45-10:15" );
                timetable.addSlot(3, "Mon", "10:30-12:00" );
                timetable.addSlot(4, "Mon", "12:30-14:00" );
                timetable.addSlot(5, "Mon", "14:15-15:45" );
                timetable.addSlot(6, "Mon", "16:00-17:30" );
                
                timetable.addSlot(1, "Tue", "7:00-8:30:00" );
                timetable.addSlot(2, "Tue", "8:45-10:15" );
                timetable.addSlot(3, "Tue", "10:30-12:00" );
                timetable.addSlot(4, "Tue", "12:30-14:00" );
                timetable.addSlot(5, "Tue", "14:15-15:45" );
                timetable.addSlot(6, "Tue", "16:00-17:30" );
                
//                timetable.addSlot(1, "Wed", "7:00-8:30:00" );
//                timetable.addSlot(2, "Wed", "8:45-10:15" );
//                timetable.addSlot(3, "Wed", "10:30-12:00" );
//                timetable.addSlot(4, "Wed", "12:30-14:00" );
//                timetable.addSlot(5, "Wed", "14:15-15:45" );
//                timetable.addSlot(6, "Wed", "16:00-17:30" );
//                
//                timetable.addSlot(1, "Thu", "7:00-8:30:00" );
//                timetable.addSlot(2, "Thu", "8:45-10:15" );
//                timetable.addSlot(3, "Thu", "10:30-12:00" );
//                timetable.addSlot(4, "Thu", "12:30-14:00" );
//                timetable.addSlot(5, "Thu", "14:15-15:45" );
//                timetable.addSlot(6, "Thu", "16:00-17:30" );
//                
//                timetable.addSlot(1, "Fri", "7:00-8:30:00" );
//                timetable.addSlot(2, "Fri", "8:45-10:15" );
//                timetable.addSlot(3, "Fri", "10:30-12:00" );
//                timetable.addSlot(4, "Fri", "12:30-14:00" );
//                timetable.addSlot(5, "Fri", "14:15-15:45" );
//                timetable.addSlot(6, "Fri", "16:00-17:30" );
                
               
		

		// Set up professors
		timetable.addLecturer(1, "NgocNT");
		timetable.addLecturer(2, "MaiHT");
		timetable.addLecturer(3, "PhongDB");
		timetable.addLecturer(4, "HoangBT");
		timetable.addLecturer(5, "VuongHM");

		// Set up modules and define the professors that teach them
		timetable.addSubject(1, "LAB211", "OOP with Java Lab", new int[]{1,3});
		timetable.addSubject(2, "CSD201", "Data Structures and Algorithms", new int[]{1,3,4});
		timetable.addSubject(3, "DBI202", "Database Systems", new int[]{3,4});
		timetable.addSubject(4, "JPD113", "Japanese Elementary", new int[]{4,5});
		timetable.addSubject(5, "WED201c", "Web Design", new int[]{5});

		// Set up student groups and the modules they take.
                
		timetable.addGroup("SE1501", 10, new int[] { 1, 3, 4 });
		timetable.addGroup("SE1502", 30, new int[] { 2, 3, 5,  });
		timetable.addGroup("SE1503", 18, new int[] { 3, 4, 5 });
		timetable.addGroup("SE1504", 25, new int[] { 1, 4 });
		timetable.addGroup("SE1505", 20, new int[] { 2, 3, 5 });
		timetable.addGroup("SE1506", 22, new int[] { 1, 4, 5 });
		timetable.addGroup("SE1507", 16, new int[] { 1, 3 });
		timetable.addGroup("SE1508", 18, new int[] { 2, 6 });
		timetable.addGroup("SE1509", 24, new int[] { 1, 5 });
		timetable.addGroup("SE1510", 25, new int[] { 3, 4 });
		return timetable;
	}
}


