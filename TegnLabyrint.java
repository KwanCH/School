import java.io.*;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.FileChooser;

import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;


public class TegnLabyrint extends Application{
    private Labyrint labyrint;
    private GridPane gp = new GridPane();
    private int aRad, aKol;
    private Liste<String> utveier;
    private String forsteLosning;
    private int losninger;
    private Text statusInfo;
    private boolean[][] losning;
    Klikkbehandler finnLosning = new Klikkbehandler(); 
    GPrute[][]nRute;
    Rute[][]ruteListe;

    public static void main(String[] args){
        Application.launch(args);
    }



    @Override
    public void start(Stage stage){
        File file = new FileChooser().showOpenDialog(stage);
        try{
            labyrint = Labyrint.lesFraFil(file);
        }catch (Exception e){}
        statusInfo = new Text("Antall Utveier");
        statusInfo.setFont(new Font(20));
        statusInfo.setX(300);
        statusInfo.setY(30);
        aRad = labyrint.getRad();
        aKol = labyrint.getKol();
        gp.setGridLinesVisible(true);
        nRute = new GPrute[aRad][aKol];
        for (int i = 0; i < aRad; i++){
            for(int j = 0; j < aKol; j++){
                if(labyrint.ruter[i][j] instanceof HvitRute){
                    GPrute ny = new GPrute(labyrint.ruter[i][j]);
                    ny.setOnAction(finnLosning);
                    gp.add(ny,j,i);
                    nRute[i][j] = ny;
                }else if(labyrint.ruter[i][j] instanceof SortRute){
                        GPrute ny = new GPrute(labyrint.ruter[i][j]);
                        ny.setStyle("-fx-background-color: #000000");
                        gp.add(ny,j,i);
                        nRute[i][j] = ny;
                }else if(labyrint.ruter[i][j] instanceof Aapning){
                    GPrute ny = new GPrute(labyrint.ruter[i][j]);
                    ny.setStyle("-fx-background-color: #ff0000");
                    gp.add(ny,j,i);
                    nRute[i][j] = ny;
                }
            }
        }

        gp.setLayoutX(100);
        gp.setLayoutY(100);

        Pane pane = new Pane();
        pane.setPrefSize(800,800);
        pane.getChildren().add(gp);
        pane.getChildren().add(statusInfo);
        Scene scene = new Scene(pane);
        stage.setTitle("Labyrint");
        stage.setScene(scene);
        stage.show();
    }

    class Klikkbehandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            nullStill();

            GPrute gpRute = (GPrute)event.getSource();
            int rad = gpRute.rutee.rad;
            int kol = gpRute.rutee.kolonne;
            

            //try {
                utveier = labyrint.finnUtveiFra(kol, rad);
                forsteLosning = null;
                losninger = 0;
                for(String s : utveier){
                    if(forsteLosning == null){
                        forsteLosning = s;
                    }
                    losninger++;
                }
                System.out.println(forsteLosning);
                String tmpUtveier = Integer.toString(losninger);
                statusInfo.setText("Antall løsninger: " + tmpUtveier);
        
                losning = losningStringTilTabell(forsteLosning, labyrint.getKol(), labyrint.getRad());
                for (int i = 0; i < labyrint.getRad(); i++){
                    for(int j = 0; j < labyrint.getKol(); j++){
                        if(losning[i][j]){
                            nRute[i][j].setStyle("-fx-background-color: #ff0000");
                        } else {
                            if(ruteListe[i][j] instanceof SortRute){
                                nRute[i][j].setStyle("-fx-background.color: #000000");
                            }
                        }
                    }
                    System.out.println();
                }
            // } catch (Exception e) {
            //     System.out.println("//TODO: handle exception");
            // }

        }  
    }

    public void nullStill(){
        for(int rad = 0; rad < labyrint.rader; rad++){
            for(int kol = 0; kol < labyrint.kolonne; kol++){
                if(labyrint.ruter[rad][kol] instanceof HvitRute){
                    nRute[rad][kol].setStyle("-fx-background-color: #FFFFFF");
                }else{
                    nRute[rad][kol].setStyle("-fx-background-color: #000000");
                }
            }
        }
    }


    class GPrute extends Button{
        public Rute rutee;
    
        GPrute(Rute rutee){
            this.rutee = rutee;
            setFont(new Font(20));
            setPrefSize(50, 50);
        }
    }


    public static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));   
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        return losning;
    }
}


