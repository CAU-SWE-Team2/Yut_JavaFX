public class YutApp {
    public static void main(String[] args){
        SquareBoard board = new SquareBoard();
        Player player1 = new Player(1);
        for(int i = 0; i < 5; i++){
            Group group = new Group(i+1, player1);
            group.addToGame(board);
        }
        Group group1 = player1.currentGroups.get(0);
        Yut yut = new Yut();

        int yutResult;
        for(int i = 0; i < 100; i++){
            if(group1.currentLocation.id == 111){
                System.out.println("Waiting...");
            }
            else{
                System.out.println(group1.currentLocation.id);
            }
            yutResult = player1.throwYut(yut);
            System.out.println(match(yutResult));
            group1.move(yutResult);
            if(group1.currentLocation.id == 0){
                System.out.println("Goal!");
                group1.goal();
                break;
            }
        }
    }

    private static String match(int result){
        switch (result){
            case 1:
                return "Do";
            case 2:
                return "Gae";
            case 3:
                return "Geol";
            case 4:
                return "Yut";
            case 5:
                return "Mo";
        }
        return "Back-Do";
    }
}
