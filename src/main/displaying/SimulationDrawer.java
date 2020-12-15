package displaying;

import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
    Button pausePlay = new Button();
    SimulationEngine simulationEngine;

    SimulationDrawer(){
        this.simulationEngine = new SimulationEngine();
    }

    @Override
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
        this.grid.setHgap((GlobalVariables.sceneHeight*0.03)/(h+3));
        this.grid.setVgap((GlobalVariables.sceneWidth*0.03)/(w+3));
    }

    public void updateSimulation(){
        this.simulationEngine.run();
    }

    public GridPane getGrid() {
        return grid;
    }

    public GridPane getActualGrid(){
        drawMap();
        return this.grid;
    }

    @Override
    public void pauseSimulation() {

    }
}
