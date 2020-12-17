package displaying;

import javafx.application.Application;
import javafx.stage.Stage;


import static runAndData.FileReader.readFile;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Evolution generator");
        readFile("parameters.json");
//        Stage simulation2 = new Stage();
//        simulation2.setTitle("Simulation 2");
//        Label labelfirst= new Label("Enter number of animals: ");
//        Button s1= new Button("Simulation 1");
//        Button s2 = new Button("Simulation 2");
//        TextField text= new TextField();
//        text.setMaxWidth(100);
//
//        s1.setOnAction(b ->
//                {
//                    GlobalVariables.startAnimals = Integer.parseInt(text.getText());
//                    Stage simulation1 = new Stage();
//                    simulation1.setTitle("Simulation 1");
//                    SimulationDrawer simulationDrawer1 = new SimulationDrawer();
//                    Scene sim1 = new Scene(simulationDrawer1.getLayout(),GlobalVariables.sceneWidth,GlobalVariables.sceneHeight);
//                    simulation1.setScene(sim1);
//                    Thread thread = new Thread(() -> {
//                        Runnable updater = simulationDrawer1::updateSimulation;
//                        while (true) {
//                            try {
//                                simulationDrawer1.pauseSimulation();
//                                Thread.sleep(simulationDrawer1.simulationEngine.getInterval());
//                            } catch (InterruptedException e) {
//                                System.out.println("Thread interrupted!");
//                            }
//                            Platform.runLater(updater);
//                        }
//                    });
//                    //
//                    thread.setDaemon(true);
//                    thread.start();
//                    simulation1.show();
//                }
//        );
//
//        s2.setOnAction(b ->
//                {
//                    GlobalVariables.startAnimals = Integer.parseInt(text.getText());
//                    Stage simulation1 = new Stage();
//                    simulation1.setTitle("Simulation 2");
//                    SimulationDrawer simulationDrawer2 = new SimulationDrawer();
//                    Scene sim2 = new Scene(simulationDrawer2.getLayout(),GlobalVariables.sceneWidth,GlobalVariables.sceneHeight);
//                    simulation2.setScene(sim2);
//                    Thread thread = new Thread(() -> {
//                        Runnable updater = simulationDrawer2::updateSimulation;
//                        while (true) {
//                            try {
//                                simulationDrawer2.pauseSimulation();
//                                Thread.sleep(simulationDrawer2.simulationEngine.getInterval());
//                            } catch (InterruptedException e) {
//                                System.out.println("Thread interrupted!");
//                            }
//                            Platform.runLater(updater);
//                        }
//                    });
//                    //
//                    thread.setDaemon(true);
//                    thread.start();
//                    simulation2.show();
//                }
//        );
//
//        VBox layout= new VBox(5);
//        layout.getChildren().addAll(labelfirst, text, s1, s2);
//        Scene scene = new Scene(layout, 300, 250);
        // primaryStage.setScene(scene);
        UserInterface userInterface = new UserInterface();
        primaryStage.setScene(userInterface.getPrimScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}