
package dao;
import dto.Group;
import dto.Subject;

/**
 *
 * @author Walter White
 */
public class Individual {

	
	
	private int[] chromosome;
	private double fitness = -1;

	
	public Individual(Timetable timetable) {
		int numClasses = timetable.getNumClasses();

		// 1 gene for room, 1 for time, 1 for professor
		int chromosomeLength = numClasses * 3;
		// Create random individual
		int newChromosome[] = new int[chromosomeLength];
		int chromosomeIndex = 0;
		// Loop through groups
		for (Group group : timetable.getGroupsAsArray()) {
			// Loop through modules
			for (int subjectId : group.getSubjectIds()) {
				// Add random time
				int timeslotId = timetable.getRandomTimeslot().getSlotId();
				newChromosome[chromosomeIndex] = timeslotId;
				chromosomeIndex++;

				// Add random room
				int roomId = timetable.getRandomRoom().getRoomId();
				newChromosome[chromosomeIndex] = roomId;
				chromosomeIndex++;

				// Add random professor
				Subject subject = timetable.getSubject(subjectId);
				newChromosome[chromosomeIndex] = subject.getRandomLecturerId();
				chromosomeIndex++;
			}
		}

		this.chromosome = newChromosome;
	}

	
	public Individual(int chromosomeLength) {
		// Create random individual
		int[] individual;
		individual = new int[chromosomeLength];
		
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individual[gene] = gene;
		}
		
		this.chromosome = individual;
	}
    
	public Individual(int[] chromosome) {
		// Create individual chromosome
		this.chromosome = chromosome;
	}

	public int[] getChromosome() {
		return this.chromosome;
	}

	
	public int getChromosomeLength() {
		return this.chromosome.length;
	}

	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}

	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getFitness() {
		return this.fitness;
	}
	
	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene] + ",";
		}
		return output;
	}

	
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}


	


}
