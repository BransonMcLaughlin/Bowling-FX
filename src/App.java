import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private GridPane pinPane = new GridPane();
    private VBox scorePane = new VBox(10);

    @Override
    public void start(Stage stage) {
        teams = setupTeams();
        playerScores = new HashMap<>();
        for (Team t : teams)
            for (Player p : t.getPlayers())
                playerScores.put(p, new Score());
        rack = new RackOfPins();

        Button rollBtn = new Button("Roll");
        rollBtn.setStyle("-fx-font-size:22px;");
        rollBtn.setOnAction(e -> nextRoll());

        statusLabel.setStyle("-fx-font-size:26px;-fx-padding:10px;");

        pinPane.setAlignment(Pos.CENTER);
        pinPane.setHgap(30);
        pinPane.setVgap(30);
        setupPinPane();

        scorePane.setAlignment(Pos.CENTER_LEFT);
        scorePane.setStyle("-fx-font-size:20px;-fx-padding:10px;");

        VBox mainVBox = new VBox(30, statusLabel, pinPane, rollBtn, scorePane);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setStyle("-fx-padding:30px;");

        StackPane root = new StackPane(mainVBox);
        root.setAlignment(Pos.CENTER);

        updateGui();

        Scene scene = new Scene(root, 800, 700);
        stage.setScene(scene);
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
        int pins = rand.nextInt(rack.getStandingPins().size() + 1);
        rack.knockDownRandomPins(pins);
        playerScores.get(current).roll(pins);

        String msg = current.getName() + " knocked down " + pins + " pins.";
        if (pins == 10) msg += " STRIKE!";
        else if (pins == 0) msg += " Gutter Ball!";
        statusLabel.setText(msg);

        updateGui();

        if (frameComplete(current)) nextPlayer();
    }

    private boolean frameComplete(Player p) {
        return playerScores.get(p).getRolls().size() % 2 == 0;
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
    int radius = 29;
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
        pinPane.add(pin, coords[i][0], coords[i][1]);
    }
}

    private void updateGui() {
        for (int i = 0; i < 10; i++) {
            Pin pinObj = rack.getPin(i + 1);
            Circle pin = (Circle) pinPane.getChildren().get(i);
            pin.setFill(pinObj.isKnockedDown() ? Color.GREY : Color.WHITE);
        }
        scorePane.getChildren().clear();
        for (Team t : teams) {
            Label tLabel = new Label(t.getName());
            tLabel.setStyle("-fx-font-weight: bold; -fx-font-size:22px;");
            scorePane.getChildren().add(tLabel);
            for (Player p : t.getPlayers()) {
                int score = playerScores.get(p).getScore();
                boolean isNow = (p == getCurrentPlayer());
                Label pl = new Label((isNow ? "▶ " : "") + p.getName() + ": " + score);
                pl.setStyle("-fx-font-size:18px;");
                scorePane.getChildren().add(pl);
            }
        }
        String now = "Bowling: " + getCurrentPlayer().getName();
        statusLabel.setText(now);
    }

    public static void main(String[] args) {
        launch();
    }
}
