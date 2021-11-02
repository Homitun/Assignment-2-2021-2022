
package dto;

/**
 *
 * @author Walter White
 */

// Simple classRoom abstraction --lưu trữ sức chứa của class room để so sánh với kích thước  group
 
public class classRoom {
	private final int roomId;
        private final String roomNumber;
	private final int capacity;

	public classRoom(int roomId, String roomNumber, int capacity) {
		this.roomId = roomId;
		this.roomNumber = roomNumber;
		this.capacity = capacity;
	}
	public String getRoomNumber() {
		return this.roomNumber;
	}

	public int getRoomId() {
		return this.roomId;
	}
	public int getRoomCapacity() {
		return this.capacity;
	}

}
