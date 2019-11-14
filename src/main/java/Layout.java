public class Layout {
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    private Room currentRoom;
    private int moves = 0;

    public int getMoves() {
        return moves;
    }

    public void setMoves(int movesToSet) {
        moves = movesToSet;
    }

    public Room getCurrentRoom() {
        //currentRoom can only be null if we are still in the starting room
        if (currentRoom == null) {
            currentRoom = returnRoomFromName(startingRoom);
        }

        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }

    /**
     * given a name, returns the room by that name
     * @param roomName
     * @return as member of Room class
     */
    public Room returnRoomFromName(String roomName) {
        Room returnRoom = null;

        for (Room currRoom : rooms) {
            if (currRoom.getName().equals(roomName)) {
                returnRoom = currRoom;
            }
        }

        return returnRoom;
    }

    /**
     * given a direction, returns the room in that direction
     * @param direction
     * @return as a member of the Room class
     */
    public Room returnRoomFromDirection(String direction) {
        if (direction == null) {
            return null;
        }

        //initialized to null, returns as null if direction is not viable
        Room retRoom = null;
        String dirLower = direction.toLowerCase();

        for (Direction directions : currentRoom.getDirections()) {
            if (directions.getDirectionName().toLowerCase().equals(dirLower)) {
                retRoom = returnRoomFromName(directions.getRoom());
            }
        }

        return retRoom;
    }

    //if (("go " + directions.getDirectionName().toLowerCase()).equals(dirLower)) {
    //                retRoom = returnRoomFromName(directions.getRoom());
    //            } else

    /**
     * check method for whether the user is in the ending room
     * @return boolean
     */
    public boolean isEnd() {
        //currentRoom can only be null if we are still in the starting room
        if (currentRoom == null) {
            currentRoom = returnRoomFromName(startingRoom);
        }

        if (currentRoom.equals(returnRoomFromName(endingRoom))) {
            return true;
        } else {
            return false;
        }
    }
}
