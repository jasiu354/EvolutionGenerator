package displaying;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import runAndData.GlobalVariables;

public class UserInterface {

    Scene primScene;

    UserInterface(){
        Button s1= new Button("Simulation 1");
        Button s2 = new Button("Simulation 2");
        TextField text= new TextField();
        buildButton(s1,text,1);
        buildButton(s2,text,2);
        text.setMaxWidth(100);
        VBox layout= new VBox(5);
        Label label= new Label("Enter number of animals: ");
        layout.getChildren().addAll(label, text, s1, s2);
        this.primScene = new Scene(layout, 300, 250);;
    }

    void buildButton(Button b, TextField text, int x) {
        b.setOnAction(action ->
                {
                    GlobalVariables.startAnimals = Integer.parseInt(text.getText());
                    Stage simulationX = new Stage();
                    simulationX.setTitle("Simulation " + x);
                    SimulationDrawer simX = new SimulationDrawer();
                    Scene layout = new Scene(simX.getLayout(), GlobalVariables.sceneWidth, GlobalVariables.sceneHeight);
                    simulationX.setScene(layout);
                    Thread thread = new Thread(() -> {
                        Runnable updater = simX::updateSimulation;
                        while (true) {
                            try {
                                simX.pauseSimulation();
                                Thread.sleep(simX.simulationEngine.getInterval());
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            Platform.runLater(updater);
                        }
                    });
                    thread.setDaemon(true);
                    thread.start();
                    simulationX.show();
                }
        );
    }

    Scene getPrimScene(){return this.primScene;}
}


/*        primaryStage.setTitle("Evolution generator");
        readFile("parameters.json");
        Stage simulation2 = new Stage();
        simulation2.setTitle("Simulation 2");
        Label labelfirst= new Label("Enter number of animals: ");
        Label label= new Label();
        Button s1= new Button("Simulation 1");
        Button s2 = new Button("Simulation 2");
        TextField text= new TextField();
        text.setMaxWidth(100);

        s1.setOnAction(b ->
                {
                    GlobalVariables.startAnimals = Integer.parseInt(text.getText());
                    Stage simulation1 = new Stage();
                    simulation1.setTitle("Simulation 1");
                    SimulationDrawer simulationDrawer1 = new SimulationDrawer();
                    Scene sim1 = new Scene(simulationDrawer1.getLayout(),GlobalVariables.sceneWidth,GlobalVariables.sceneHeight);
                    simulation1.setScene(sim1);
                    Thread thread = new Thread(() -> {
                        Runnable updater = simulationDrawer1::updateSimulation;
                        while (true) {
                            try {
                                simulationDrawer1.pauseSimulation();
                                Thread.sleep(simulationDrawer1.simulationEngine.getInterval());
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            Platform.runLater(updater);
                        }
                    });
                    //
                    thread.setDaemon(true);
                    thread.start();
                    simulation1.show();
                }
        );

        s2.setOnAction(b ->
                {
                    GlobalVariables.startAnimals = Integer.parseInt(text.getText());
                    Stage simulation1 = new Stage();
                    simulation1.setTitle("Simulation 2");
                    SimulationDrawer simulationDrawer2 = new SimulationDrawer();
                    Scene sim2 = new Scene(simulationDrawer2.getLayout(),GlobalVariables.sceneWidth,GlobalVariables.sceneHeight);
                    simulation2.setScene(sim2);
                    Thread thread = new Thread(() -> {
                        Runnable updater = simulationDrawer2::updateSimulation;
                        while (true) {
                            try {
                                simulationDrawer2.pauseSimulation();
                                Thread.sleep(simulationDrawer2.simulationEngine.getInterval());
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            Platform.runLater(updater);
                        }
                    });
                    //
                    thread.setDaemon(true);
                    thread.start();
                    simulation2.show();
                }
        );

        VBox layout= new VBox(5);
        layout.getChildren().addAll(labelfirst, text, s1, s2, label);
        Scene scene = new Scene(layout, 300, 250);*/