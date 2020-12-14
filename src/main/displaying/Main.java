package displaying;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mapElements.Animal;
import mapElements.Vector2d;
import runAndData.GlobalVariables;
import runAndData.SimulationEngine;

public class Main extends Application{

    SimulationEngine simulationEngine;
    GridPane grid;
    int sceneWidth,sceneHeight;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Evolution generator");
        GlobalVariables.moveEnergy = 10;
        GlobalVariables.startEnergy = 100;
        GlobalVariables.height = 15;
        GlobalVariables.jungleRatio = 0.3;
        GlobalVariables.width = 15;
        GlobalVariables.startAnimals = 7;
        GlobalVariables.plantEnergy = 100;
        this.simulationEngine = new SimulationEngine();
        this.sceneWidth = 800;
        this.sceneHeight = 600;
        drawMap();
        Scene scene = new Scene(this.grid,sceneWidth,sceneHeight);

        Thread thread = new Thread(() -> {
            Runnable updater = () -> {
                updateSimulation();
                drawMap();
            };
            while(true){
                try{
                    Thread.sleep(getInterval());
                }catch (InterruptedException e){ }
                Platform.runLater(updater);
            }
        });
        thread.setDaemon(true);
        thread.start();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void drawMap() {
        int w = this.simulationEngine.getMap().getWidth(), h = this.simulationEngine.getMap().getHeight();
        this.grid = new GridPane();
        String display = this.simulationEngine.getMap().buildDisplay();
        for (int j = h; j >= -1; j--) {
            for (int i = -1; i <= w; i++) {
                Rectangle r = new Rectangle((this.sceneWidth) * 0.97 / (w + 2), (this.sceneHeight) * 0.97 / (h + 2));
                if ((j == -1 || i == -1) || (j == h || i == w))
                    r.setFill(Color.WHITE);
                else if (this.simulationEngine.getMap().getElements().containsKey(new Vector2d(i, j))) {
                    if (this.simulationEngine.getMap().getElements().get(new Vector2d(i, j)).stream().anyMatch(a -> a instanceof Animal))
                        r.setFill(Color.YELLOW);
                    else
                        r.setFill(Color.GREEN);
                } else {
                    if (this.simulationEngine.getMap().inJungle(new Vector2d(i, j)))
                        r.setFill(Color.LIGHTGREEN);
                    else
                        r.setFill(Color.SANDYBROWN);
                }
                this.grid.add(r, i + 1, j + 1);
            }

        }
        this.grid.setHgap((this.sceneHeight*0.03)/(h+3));
        this.grid.setVgap((this.sceneWidth*0.03)/(w+3));
    }

    void updateSimulation(){
        this.simulationEngine.run();
    }

    long getInterval(){
        return this.simulationEngine.getInterval();
    }
}