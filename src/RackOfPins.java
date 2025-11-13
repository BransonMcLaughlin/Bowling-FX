import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RackOfPins {
    private Pin p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;

    public RackOfPins() {
        p1 = makePin(1);
        p2 = makePin(2);
        p3 = makePin(3);
        p4 = makePin(4);
        p5 = makePin(5);
        p6 = makePin(6);
        p7 = makePin(7);
        p8 = makePin(8);
        p9 = makePin(9);
        p10 = makePin(10);
    }

    private Pin makePin(int num) {
        Pin p = new Pin();
        p.setPinNum(num);
        return p;
    }

    public void resetRack() {
        for (int i = 1; i <= 10; i++) {
            getPin(i).setKnockedDown(false);
        }
    }

    public List<Pin> getStandingPins() {
        List<Pin> standing = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            if (!getPin(i).isKnockedDown()) standing.add(getPin(i));
        }
        return standing;
    }

    public Pin getPin(int pinNumber) {
        switch (pinNumber) {
            case 1: return p1;
            case 2: return p2;
            case 3: return p3;
            case 4: return p4;
            case 5: return p5;
            case 6: return p6;
            case 7: return p7;
            case 8: return p8;
            case 9: return p9;
            case 10: return p10;
            default: return null;
        }
    }

    public void knockDownRandomPins(int number) {
        List<Pin> standing = getStandingPins();
        Collections.shuffle(standing, new Random());
        for (int i = 0; i < number && i < standing.size(); i++) {
            standing.get(i).setKnockedDown(true);
        }
    }
}