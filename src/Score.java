import java.util.ArrayList;
import java.util.List;

public class Score {
    private List<Integer> rolls = new ArrayList<>();
    private int currentRoll = 0;

    public void roll(int pins) {
        rolls.add(pins);
        currentRoll++;
    }

    public int getScore() {
        int score = 0;
        int rollIndex = 0;
        for (int frame = 0; frame < 10 && rollIndex < rolls.size(); frame++) {
            int frameScore = 0;
            frameScore += rolls.get(rollIndex);
            if (rollIndex + 1 < rolls.size())
                frameScore += rolls.get(rollIndex + 1);
            score += frameScore;
            rollIndex += 2;
        }
        return score;
    }

    public List<Integer> getRolls() {
        return rolls;
    }
}
