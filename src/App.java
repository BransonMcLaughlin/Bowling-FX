import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

public class App extends Application {
    private List<Team> teams;
    private Map<Player, Score> playerScores;
    private int currentTeamIdx = 0;
    private int currentPlayerIdx = 0;
    private RackOfPins rack;
    private Random rand = new Random();

    private Label statusLabel = new Label();
    private Label nowLabel = new Label(); // small addition to keep roll messages separate
    private GridPane pinPane = new GridPane();
<<<<<<< HEAD
    private VBox scorePane = new VBox(10);
    private Button rollBtn; // made a field so we can disable it at game end

    // small reliable cache of the Circle nodes for each pin
    private final List<Circle> pinCircles = new ArrayList<>();
=======
    private VBox scorePane = new VBox(5);
>>>>>>> 106395513e189fc6d33e08dd8a8996121ad9e98c

    @Override
    public void start(Stage stage) throws Exception {
        teams = setupTeams();
        playerScores = new HashMap<>();
        for (Team t : teams)
            for (Player p : t.getPlayers())
                playerScores.put(p, new Score());
        rack = new RackOfPins();

<<<<<<< HEAD
        rollBtn = new Button("Roll");
        rollBtn.setStyle("-fx-font-size:22px;");
        rollBtn.setOnAction(e -> nextRoll());

        statusLabel.setStyle("-fx-font-size:20px;-fx-padding:6px;");
        nowLabel.setStyle("-fx-font-size:18px; -fx-padding:4px;");

        pinPane.setAlignment(Pos.CENTER);
        pinPane.setHgap(20);
        pinPane.setVgap(18);
        setupPinPane();

        scorePane.setAlignment(Pos.CENTER_LEFT);
        scorePane.setStyle("-fx-font-size:18px;-fx-padding:6px;");

        VBox mainVBox = new VBox(14, nowLabel, statusLabel, pinPane, rollBtn, scorePane);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setStyle("-fx-padding:18px;");

        StackPane root = new StackPane(mainVBox);
        root.setAlignment(Pos.CENTER);

=======
        Button rollBtn = new Button("Roll");
        rollBtn.setOnAction(e -> nextRoll());

        VBox mainPane = new VBox(20, statusLabel, pinPane, rollBtn, scorePane);
        setupPinPane();
>>>>>>> 106395513e189fc6d33e08dd8a8996121ad9e98c
        updateGui();

        stage.setScene(new Scene(mainPane, 420, 480));
        stage.setTitle("Simple Bowling Game");
        stage.show();
    }

    private List<Team> setupTeams() {
        Team t1 = new Team("Eagles", Arrays.asList(new Player("Ann", null), new Player("Bob", null)));
        Team t2 = new Team("Sharks", Arrays.asList(new Player("Cara", null), new Player("Dan", null)));
        return Arrays.asList(t1, t2);
    }

    private void nextRoll() {
        Player current = getCurrentPlayer();
        int standing = rack.getStandingCount();          // uses RackOfPins.getStandingCount()
        int pinsThisRoll = rand.nextInt(standing + 1);   // 0..standing
        rack.knockDownRandomPins(pinsThisRoll);
        playerScores.get(current).roll(pinsThisRoll);

        // cumulative pins so far in this frame
        int framePins = playerScores.get(current).getPinsInCurrentFrame();

        String msg = current.getName() + " knocked down " + pinsThisRoll + " pins";
        if (framePins > pinsThisRoll) msg += " (total " + framePins + " this frame)";
        msg += ".";
        if (pinsThisRoll == 10) msg += " STRIKE!";
        else if (pinsThisRoll == 0) msg += " Gutter Ball!";

        System.out.println(msg);        // console println
        statusLabel.setText(msg);       // show roll message in UI

        updateGui();

        // if player's current frame is complete, reset rack and advance
        if (frameComplete(current)) {
            rack.resetRack();
            nextPlayer();
        }

        // check if all players finished 10 frames -> show winner and disable rolling
        boolean allDone = true;
        for (Team t : teams) {
            for (Player p : t.getPlayers()) {
                if (playerScores.get(p).getCompletedFramesCount() < 10) {
                    allDone = false;
                    break;
                }
            }
            if (!allDone) break;
        }
        if (allDone) {
            // compute team totals and find winner
            Team winner = null;
            int best = Integer.MIN_VALUE;
            for (Team t : teams) {
                int sum = 0;
                for (Player p : t.getPlayers()) sum += playerScores.get(p).getScore();
                if (sum > best) { best = sum; winner = t; }
            }
            String endMsg = "Game over! Winner: " + (winner != null ? winner.getName() : "tie") + " (" + best + " pts)";
            statusLabel.setText(endMsg);
            System.out.println(endMsg);
            rollBtn.setDisable(true);
        }
    }

    // uses Score helper to decide whether the player's current frame is complete
    private boolean frameComplete(Player p) {
        return playerScores.get(p).isCurrentFrameComplete();
    }
    private Player getCurrentPlayer() {
        Team team = teams.get(currentTeamIdx);
        return team.getPlayers().get(currentPlayerIdx);
    }
    private void nextPlayer() {
        rack.resetRack();
        currentPlayerIdx++;
        if (currentPlayerIdx >= teams.get(currentTeamIdx).getPlayers().size()) {
            currentPlayerIdx = 0;
            currentTeamIdx = (currentTeamIdx + 1) % teams.size();
        }
        updateGui();
    }

    private void setupPinPane() {
        pinPane.getChildren().clear();
<<<<<<< HEAD
        pinCircles.clear();
        int radius = 22; // smaller so UI fits
        // triangle layout: [col, row]
        int[][] coords = {
            {3,0},      // pin 1
            {2,1},{4,1},           // pins 2,3
            {1,2},{3,2},{5,2},     // pins 4,5,6
            {0,3},{2,3},{4,3},{6,3} // pins 7,8,9,10
        };
        for (int i = 0; i < 10; i++) {
            Circle pin = new Circle(radius, Color.WHITE);
            pin.setStroke(Color.BLACK);
            pin.setUserData(i + 1);
            pinCircles.add(pin);           // <-- cache it
=======
        int[][] coords = {
                {2, 0},{1, 1},{2, 1},{3, 1},
                {0, 2},{1, 2},{2, 2},{3, 2},{4, 2},
                {2, 3}
        };
        for (int i = 0; i < 10; i++) {
            Circle pin = new Circle(14, Color.WHITE);
            pin.setStroke(Color.BLACK);
            pin.setUserData(i + 1);
>>>>>>> 106395513e189fc6d33e08dd8a8996121ad9e98c
            pinPane.add(pin, coords[i][0], coords[i][1]);
        }
    }

    private void updateGui() {
        // update pin visuals using the cached circles (reliable mapping)
        for (int i = 0; i < 10; i++) {
            Pin pinObj = rack.getPin(i + 1);
            Circle pin = pinCircles.get(i); // <-- use cached circle
            pin.setFill(pinObj.isKnockedDown() ? Color.GREY : Color.WHITE);
        }

        // build score display and show team totals
        scorePane.getChildren().clear();
        for (Team t : teams) {
            int teamTotal = 0;
            Label tLabel = new Label(t.getName());
            tLabel.setStyle("-fx-font-weight: bold");
            scorePane.getChildren().add(tLabel);
            for (Player p : t.getPlayers()) {
                int score = playerScores.get(p).getScore();
                teamTotal += score;
                boolean isNow = (p == getCurrentPlayer());
                Label pl = new Label((isNow ? "▶ " : "") + p.getName() + ": " + score);
                scorePane.getChildren().add(pl);
            }
            Label totalLbl = new Label("Team total: " + teamTotal);
            totalLbl.setStyle("-fx-font-size:14px; -fx-font-style: italic;");
            scorePane.getChildren().add(totalLbl);
        }

        // nowLabel shows who's turn; statusLabel keeps the last roll message
        nowLabel.setText("Bowling: " + getCurrentPlayer().getName());
    }

    public static void main(String[] args) {
        launch();
    }
}
