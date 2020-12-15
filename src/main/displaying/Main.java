package displaying;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import runAndData.GlobalVariables;

import static runAndData.FileReader.readFile;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Evolution generator");
        readFile("parameters.json");
        GlobalVariables.startAnimals = 7;
        SimulationDrawer simulationDrawer = new SimulationDrawer();
        Scene scene = new Scene(simulationDrawer.getLayout(), GlobalVariables.sceneWidth, GlobalVariables.sceneHeight);
        Thread thread = new Thread(() -> {
            Runnable updater = simulationDrawer::updateSimulation;
            while (true) {
                try {
                    Thread.sleep(simulationDrawer.simulationEngine.getInterval());
                    simulationDrawer.pauseSimulation();
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
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

    public static void main(String[] args) {
        Application.launch(args);
    }
}