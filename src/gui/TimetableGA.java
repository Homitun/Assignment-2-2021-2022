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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

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

    public static void main(String[] args) throws FileNotFoundException {
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
            System.out.println("Subject: " + 
                    timetable.getSubject(bestClass.getSubjectId()).getSubjectName());
            System.out.println("Group: " + 
                    timetable.getGroup(bestClass.getGroupId()).getGroupId());
            System.out.println("Room: " + 
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());                            
            System.out.println("Lecturer: " + 
                    timetable.getLecturer(bestClass.getLecturerId()).getLecturerName());
            System.out.println("Slot: " + 
                    timetable.getTimeslot(bestClass.getSlotId()).getTimeslot());
          
            System.out.println("-----");
            classIndex++;
        }
        FPTClass matrix[][] = new FPTClass[10][30];
        String cl[] = {"SE1501" , "SE1502", "SE1503", "SE1504", "SE1505", "SE1506", "SE1507", "SE1508", "SE1509","SE1510"};
        for(FPTClass bestClass: classes){
            int idx = -1;
            for(int i =0 ; i < 10 ; i++)
                if(bestClass.getGroupId().equals(cl[i])){
                    idx = i; break;
                }
            matrix[idx][bestClass.getSlotId() - 1] = bestClass;
        }
         try {
            FileWriter fw = new FileWriter("Timetable.csv");
            
            BufferedWriter bw = new BufferedWriter(fw);
            fw.write(",Class,");
            for(int i =0 ; i < 10 ; i++) fw.write(cl[i] +",");
            fw.write('\n');
            String day[] = {"Mon" , "Tue" , "Wed" , "Thur" , "Fri" };
            int day_now = 0 ;
            int loop = 1;
            for(int j= 0 ; j <30;j++){
                if(loop == 1 || loop == 7){
                        loop = 1;
                        fw.write(day[day_now++] + ",");
                    }else fw.write("-,");
                    fw.write(loop + ",");
                                        loop++;

                for(int i =0 ; i < 10; i++){
                    
                    
                    if(matrix[i][j] != null){
                        fw.write(matrix[i][j].toString() +",");
                    }else fw.write("-,");
                }
                fw.write('\n');
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("error");
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
		timetable.addRoom(1, "201", 15);
		timetable.addRoom(2, "202", 30);
		timetable.addRoom(3, "210", 30);
		timetable.addRoom(4, "203", 20);
		timetable.addRoom(5, "204", 25);
		timetable.addRoom(6, "205", 25);
		timetable.addRoom(7, "206", 25);
		timetable.addRoom(8, "207", 25);
		timetable.addRoom(9, "208", 25);
		timetable.addRoom(10, "209", 25);

		// Set up timeslots
                timetable.addSlot(1, "Mon", "[Slot 1]:7:00-8:30:00" );
                timetable.addSlot(2, "Mon", "[Slot 2]:8:45-10:15" );
                timetable.addSlot(3, "Mon", "[Slot 3]:10:30-12:00" );
                timetable.addSlot(4, "Mon", "[Slot 4]:12:30-14:00" );
                timetable.addSlot(5, "Mon", "[Slot 5]:14:15-15:45" );
                timetable.addSlot(6, "Mon", "[Slot 6]:16:00-17:30" );
                
                timetable.addSlot(7, "Tue", "[Slot 1]:7:00-8:30:00" );
                timetable.addSlot(8, "Tue", "[Slot 2]:8:45-10:15" );
                timetable.addSlot(9, "Tue", "[Slot 3]:10:30-12:00" );
                timetable.addSlot(10, "Tue", "[Slot 4]:12:30-14:00" );
                timetable.addSlot(11, "Tue", "[Slot 5]:14:15-15:45" );
                timetable.addSlot(12, "Tue", "[Slot 6]:16:00-17:30" );
                
                timetable.addSlot(13, "Wed", "[Slot 1]:7:00-8:30:00" );
                timetable.addSlot(14, "Wed", "[Slot 2]:8:45-10:15" );
                timetable.addSlot(15, "Wed", "[Slot 3]:10:30-12:00" );
                timetable.addSlot(16, "Wed", "[Slot 4]:12:30-14:00" );
                timetable.addSlot(17, "Wed", "[Slot 5]:14:15-15:45" );
                timetable.addSlot(18, "Wed", "[Slot 6]:16:00-17:30" );
                
                timetable.addSlot(19, "Thu", "[Slot 1]:7:00-8:30:00" );
                timetable.addSlot(20, "Thu", "[Slot 2]:8:45-10:15" );
                timetable.addSlot(21, "Thu", "[Slot 3]:10:30-12:00" );
                timetable.addSlot(22, "Thu", "[Slot 4]:12:30-14:00" );
                timetable.addSlot(23, "Thu", "[Slot 5]:14:15-15:45" );
                timetable.addSlot(24, "Thu", "[Slot 6]:16:00-17:30" );
                
                timetable.addSlot(25, "Fri", "[Slot 1]:7:00-8:30:00" );
                timetable.addSlot(26, "Fri", "[Slot 2]:8:45-10:15" );
                timetable.addSlot(27, "Fri", "[Slot 3]:10:30-12:00" );
                timetable.addSlot(28, "Fri", "[Slot 4]:12:30-14:00" );
                timetable.addSlot(29, "Fri", "[Slot 5]:14:15-15:45" );
                timetable.addSlot(30, "Fri", "[Slot 6]:16:00-17:30" );
                
               
		

		// Set up professors
		timetable.addLecturer(1, "NgocNT");
		timetable.addLecturer(2, "MaiHT");
		timetable.addLecturer(3, "PhongDB");
		timetable.addLecturer(4, "HoangBT");
		timetable.addLecturer(5, "VuongHM");
		timetable.addLecturer(6, "PhongHM");

		// Set up modules and define the professors that teach them
		timetable.addSubject(1, "LAB211", "OOP with Java Lab", new int[]{1,3});
		timetable.addSubject(2, "CSD201", "Data Structures and Algorithms", new int[]{1,3,4});
		timetable.addSubject(3, "DBI202", "Database Systems", new int[]{3,4});
		timetable.addSubject(4, "JPD113", "Japanese Elementary", new int[]{4,5});
		timetable.addSubject(5, "WED201c", "Web Design", new int[]{5});
		timetable.addSubject(6, "VOV", "Vovinam", new int[]{6});

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


