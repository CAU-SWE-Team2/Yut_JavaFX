import java.util.ArrayList;

public class Group {
    int id;
    int numOfPieces;
    ArrayList<Integer> currentPieces = new ArrayList<>();
    Node currentLocation;
    Player owner;
    Board playingBoard;

    Group(int id, Player owner){
        this.id = id;
        this.currentPieces.add(id);
        this.owner = owner;
    }

    private void carryBack(Group existingGroup){
        existingGroup.currentPieces.addAll(this.currentPieces);
        this.currentPieces.clear();
        this.owner.currentGroups.remove(this);
    }

    private void catchOtherGroup(Group existingGroup){
        int size = existingGroup.currentPieces.size();
        for(int i = 0; i < size; i++){
            Group repairGroup = new Group(existingGroup.currentPieces.get(i), this.owner);
            repairGroup.currentLocation = playingBoard.waitingNode;
            existingGroup.owner.currentGroups.add(repairGroup);
        }
        existingGroup.owner.currentGroups.remove(existingGroup);
        this.currentLocation.currentGroup = this;
    }

    public void addToGame(Board board){
        this.currentLocation = board.waitingNode;
        this.playingBoard = board;
    }

    public void move(int yutResult){
        this.currentLocation.currentGroup = null;
        if(yutResult == -1){
            this.currentLocation = this.currentLocation.beforeNode;
        }
        else {
            int whereToMove = this.currentLocation.id/100-1;
            for (int i = 0; i < yutResult; i++) {
                this.currentLocation = this.currentLocation.nextNodes[whereToMove];
            }
        }
        if(this.currentLocation.currentGroup != null){
            if(this.currentLocation.currentGroup.owner == this.owner){
                this.carryBack(this.currentLocation.currentGroup);
            }
            else{
                this.catchOtherGroup(this.currentLocation.currentGroup);
            }
        }
        else {
            this.currentLocation.currentGroup = this;
        }
    }

    public Node getNextNode(int n){
        Node nextNode = this.currentLocation;
        if(n == -1){
            nextNode = nextNode.beforeNode;
        }
        else {
            int whereToMove = nextNode.id/100-1;
            for (int i = 0; i < n; i++) {
                nextNode = nextNode.nextNodes[whereToMove];
            }
        }
        return nextNode;
    }

    public void goal(){
        if(this.currentLocation.id == 0){
            this.owner.numOfCurrentPieces -= this.numOfPieces;
            this.owner.currentGroups.remove(this);
        }
    }
}
