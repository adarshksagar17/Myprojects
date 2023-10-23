import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Player {
    String name;
    int score;

    Player(String name) {
        this.name = name;
        this.score = 0;
    }
}

class Game {
    Player player1;
    Player player2;
    String result;

    Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    void playGame() {
        String[] moves = {"rock", "paper", "scissors"};
        Random random = new Random();
        int move1 = random.nextInt(3);
        int move2 = random.nextInt(3);

        result = determineWinner(moves[move1], moves[move2]);

        if (result.equals(player1.name)) {
            player1.score++;
        } else if (result.equals(player2.name)) {
            player2.score++;
        }
    }

    String determineWinner(String move1, String move2) {
        if (move1.equals(move2)) {
            return "draw";
        } else if ((move1.equals("rock") && move2.equals("scissors")) ||
                   (move1.equals("scissors") && move2.equals("paper")) ||
                   (move1.equals("paper") && move2.equals("rock"))) {
            return player1.name;
        } else {
            return player2.name;
        }
    }
}

public class TournamentManager {
    public static void main(String[] args) {
        TournamentManager manager = new TournamentManager();

        manager.addPlayer("Player 1");
        manager.addPlayer("Player 2");
        manager.addPlayer("Player 3");
        manager.addPlayer("Player 4");

        manager.createGames();
        manager.playTournament();
        manager.printLeaderboard();
    }

    List<Player> players;
    List<Game> games;
    List<Player> leaderboard;

    TournamentManager() {
        players = new ArrayList<>();
        games = new ArrayList<>();
        leaderboard = new ArrayList<>();
    }

    void addPlayer(String playerName) {
        players.add(new Player(playerName));
    }

    void createGames() {
        if (players.size() % 2 != 0) {
            System.out.println("The number of players must be even for tournament games.");
            return;
        }

        for (int i = 0; i < players.size(); i += 2) {
            Game game = new Game(players.get(i), players.get(i + 1));
            games.add(game);
        }
    }

    void playTournament() {
        for (Game game : games) {
            game.playGame();
        }

        leaderboard.clear();
        leaderboard.addAll(players);
        leaderboard.sort((p1, p2) -> Integer.compare(p2.score, p1.score));
    }

    void printLeaderboard() {
        System.out.println("Tournament Leaderboard:");
        for (int i = 0; i < leaderboard.size(); i++) {
            System.out.println((i + 1) + ". " + leaderboard.get(i).name + " - Score: " + leaderboard.get(i).score);
        }
    }
}
