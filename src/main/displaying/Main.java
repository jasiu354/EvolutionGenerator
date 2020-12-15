package displaying;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mapElements.Animal;
import mapElements.MapElement;
import mapElements.Vector2d;
import runAndData.GlobalVariables;
import runAndData.SimulationEngine;

import java.util.*;


public class Main extends Application {

    SimulationEngine simulationEngine;
    GridPane grid;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Evolution generator");
        GlobalVariables.moveEnergy = 10;
        GlobalVariables.startEnergy = 300;
        GlobalVariables.height = 15;
        GlobalVariables.jungleRatio = 0.3;
        GlobalVariables.width = 15;
        GlobalVariables.startAnimals = 7;
        GlobalVariables.plantEnergy = 100;
        this.simulationEngine = new SimulationEngine();
        SimulationDrawer simulationDrawer = new SimulationDrawer();

        // button
        Button pausePlay = new Button("PAUSE");


        pausePlay.setOnAction(e -> {
            simulationDrawer.simulationEngine.setSimulationStatus(!simulationDrawer.simulationEngine.getSimulationStatus());
        });
        //

        BorderPane layout = new BorderPane();

        HBox up = new HBox(pausePlay);
        up.setAlignment(Pos.CENTER);
        HBox down = new HBox(simulationDrawer.getActualGrid());
        layout.setCenter(down);
        layout.setTop(up);

        Scene scene = new Scene(layout, GlobalVariables.sceneWidth, GlobalVariables.sceneHeight);

        Thread thread = new Thread(() -> {
            Runnable updater = () -> {
                  simulationDrawer.updateSimulation();
                  updateSimulation();
                  down.getChildren().remove(simulationDrawer.getGrid());
                  down.getChildren().add(simulationDrawer.getActualGrid());
            };
            while (true) {
                try {
                    Thread.sleep(getInterval());
                    pause(simulationDrawer);
                } catch (InterruptedException e) {
                }
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
                Rectangle r = new Rectangle((GlobalVariables.sceneWidth) * 0.97 / (w + 2), (GlobalVariables.sceneHeight) * 0.97 / (h + 2));
                if ((j == -1 || i == -1) || (j == h || i == w))
                    r.setFill(Color.WHITE);
                else if (this.simulationEngine.getMap().getElements().containsKey(new Vector2d(i, j))) {
                    if (this.simulationEngine.getMap().getElements().get(new Vector2d(i, j)).stream().anyMatch(a -> a instanceof Animal)) {
                        List<Animal> animals = new ArrayList<>();
                        Set<MapElement> mapElements = this.simulationEngine.getMap().getElements().get(new Vector2d(i, j));
                        for (MapElement m : mapElements) {
                            if (m instanceof Animal)
                                animals.add((Animal) m);
                        }
                        int energyOfStrAnimal = this.simulationEngine.getStrongestAnimal(animals).getEnergyLevel();
                        if (energyOfStrAnimal > GlobalVariables.startEnergy * 0.75)
                            r.setFill(Color.MAGENTA);
                        else if (energyOfStrAnimal > GlobalVariables.startEnergy * 0.5)
                            r.setFill(Color.LIGHTPINK);
                        else
                            r.setFill(Color.LAVENDERBLUSH);
                    } else
                        r.setFill(Color.GREEN);
                } else {
                    if (this.simulationEngine.getMap().inJungle(new Vector2d(i, j)))
                        r.setFill(Color.LIGHTGREEN);
                    else
                        r.setFill(Color.GOLDENROD);
                }
                this.grid.add(r, i + 1, j + 1);
            }

        }
        this.grid.setHgap((GlobalVariables.sceneHeight * 0.03) / (h + 3));
        this.grid.setVgap((GlobalVariables.sceneWidth * 0.03) / (w + 3));
    }

    void updateSimulation() {
        this.simulationEngine.run();
    }

    long getInterval() {
        return this.simulationEngine.getInterval();
    }

    void pause(SimulationDrawer s) throws InterruptedException {
        while(!s.simulationEngine.getSimulationStatus()){
            Thread.sleep(100);
        }
    }
}