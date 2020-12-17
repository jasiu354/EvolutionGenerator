package displaying;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import mapElements.Animal;
import mapElements.MapElement;
import mapElements.Vector2d;
import runAndData.GlobalVariables;
import runAndData.SimulationEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimulationDrawer implements SimulationDrawerInterface {
    GridPane grid;
    Button pausePlay;
    BorderPane layout;
    HBox up;
    SimulationEngine simulationEngine;

    SimulationDrawer(){
        this.grid = new GridPane();
        this.simulationEngine = new SimulationEngine();
        this.pausePlay = new Button("play");
        this.layout = new BorderPane();
        this.pausePlay.setOnAction(e -> this.simulationEngine.setSimulationStatus(!this.simulationEngine.getSimulationStatus()));
        HBox down = new HBox(this.pausePlay);
        down.setAlignment(Pos.CENTER);
        this.up = new HBox(this.grid);
        this.layout.setCenter(this.up);
        this.layout.setBottom(down);
    }

    @Override
    public void drawMap() {
        int w = this.simulationEngine.getMap().getWidth(), h = this.simulationEngine.getMap().getHeight();
        this.grid = new GridPane();
        String display = this.simulationEngine.getMap().buildDisplay();
        for (int j = h; j >= -1; j--) {
            for (int i = -1; i <= w; i++) {
                Rectangle r = new Rectangle((GlobalVariables.sceneWidth) * 0.9 / (w + 2), (GlobalVariables.sceneHeight) * 0.9 / (h + 2));
                if ((j == -1 || i == -1) || (j == h || i == w))
                    r.setFill(Color.WHITE);
                else if (this.simulationEngine.getMap().getElements().containsKey(new Vector2d(i, j))) {
                    if (this.simulationEngine.getMap().getElements().get(new Vector2d(i, j)).stream().anyMatch(a -> a instanceof Animal)){
                        List<Animal> animals = new ArrayList<>();
                        Set<MapElement> mapElements = this.simulationEngine.getMap().getElements().get(new Vector2d(i,j));
                        for(MapElement m : mapElements) {
                            if (m instanceof Animal)
                                animals.add((Animal) m);
                        }
                        int energyOfStrAnimal = this.simulationEngine.getStrongestAnimal(animals).getEnergyLevel();
                        if(energyOfStrAnimal > GlobalVariables.startEnergy*0.75)
                            r.setFill(Color.MAGENTA);
                        else if(energyOfStrAnimal > GlobalVariables.startEnergy*0.5)
                            r.setFill(Color.LIGHTPINK);
                        else
                            r.setFill(Color.LAVENDERBLUSH);
                    }

                    else
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
        this.grid.setHgap((GlobalVariables.sceneHeight*0.02)/(h+3));
        this.grid.setVgap((GlobalVariables.sceneWidth*0.02)/(w+3));
    }

    public void updateSimulation(){
        this.simulationEngine.run();
        this.up.getChildren().remove(this.getGrid());
        this.up.getChildren().add(getActualGrid());
        this.up.setAlignment(Pos.BOTTOM_CENTER);
    }

    public GridPane getGrid() {
        return grid;
    }

    public GridPane getActualGrid(){
        drawMap();
        return this.grid;
    }

    @Override
    public void pauseSimulation() throws InterruptedException {
        while(!this.simulationEngine.getSimulationStatus()){
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.out.println("Thread interrupted");
            }
        }
    }

    public BorderPane getLayout() { return this.layout; }

}
