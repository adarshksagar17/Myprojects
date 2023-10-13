package project;

import java.util.*;

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
        String[] moves = {"rock", "scissors"};
        Random random = new Random();
        int move1 = random.nextInt(2);
        int move2 = random.nextInt(2);

        result = determineWinner(moves[move1], moves[move2]);
    }

    String determineWinner(String move1, String move2) {
        if (move1.equals(move2)) {
            return "draw";
        } else if ((move1.equals("rock") && move2.equals("scissors")) ||
                (move1.equals("scissors") && move2.equals("rock")) {
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

        manager.createTournamentTree();
        manager.playTournament();
        manager.printLeaderboard();
    }

    List<Player> players;
    List<Game> tournamentTree;
    List<Player> leaderboard;

    TournamentManager() {
        players = new ArrayList<>();
        tournamentTree = new ArrayList<>();
        leaderboard = new ArrayList<>();
    }

    void addPlayer(String playerName) {
        players.add(new Player(playerName));
    }

    void createTournamentTree() {
        if (players.size() < 2) {
            System.out.println("The tournament requires at least 2 players.");
            return;
        }

        for (int i = 0; i < players.size() - 1; i += 2) {
            Game game = new Game(players.get(i), players.get(i + 1));
            tournamentTree.add(game);
        }
    }

    void playTournament() {
        while (tournamentTree.size() > 1) {
            List<Game> nextRound = new ArrayList<>();
            for (int i = 0; i < tournamentTree.size(); i += 2) {
                Game game = new Game(tournamentTree.get(i).player1, tournamentTree.get(i + 1).player2);
                game.playGame();
                String result = game.result;

                if (!result.equals("draw")) {
                    Player winner = players.stream()
                            .filter(player -> player.name.equals(result))
                            .findFirst()
                            .orElse(null);

                    if (winner != null) {
                        winner.score++;
                    }
                }

                nextRound.add(game);
            }
            tournamentTree = nextRound;
        }
    }

    void printLeaderboard() {
        players.sort((p1, p2) -> Integer.compare(p2.score, p1.score));

        System.out.println("Tournament Leaderboard:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).name + " - Score: " + players.get(i).score);
        }
    }
}
