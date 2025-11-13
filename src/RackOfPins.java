import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class RackOfPins {
    private ArrayList<Circle> pins = new ArrayList<>();
    private Group view = new Group();

    public RackOfPins() {
        double startX = 350;
        double startY = 150;
        double radius = 12;
        int[] rowCounts = {1,2,3,4};
        int yOff = 0;
        int idx = 0;
        for (int r=0;r<rowCounts.length;r++){
            int count = rowCounts[r];
            double rowWidth = (count-1) * 30;
            for (int i=0;i<count;i++){
                double x = startX - rowWidth/2 + i*30;
                double y = startY + yOff;
                Circle c = new Circle(x,y,radius, Color.LIGHTGRAY);
                c.setStroke(Color.BLACK);
                pins.add(c);
                view.getChildren().add(c);
                idx++;
                if (idx>=10) break;
            }
            yOff += 30;
        }
        Text caption = new Text(10,20,"Pins (gray = standing)");
        view.getChildren().add(caption);
    }

    public Node getView() {
        return view;
    }

    public void knockPins(int n) {
        int knocked = 0;
        for (Circle c : pins) {
            if (knocked >= n) break;
            if (c.getFill().equals(Color.LIGHTGRAY)) {
                c.setFill(Color.DARKGRAY);
                knocked++;
            }
        }
    }

    public void resetPins() {
        for (Circle c : pins) c.setFill(Color.LIGHTGRAY);
    }
}
