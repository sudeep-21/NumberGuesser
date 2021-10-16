package engineManager;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Window extends Application {
    private class createNodes {
        private int lives = 8;
        private int usrAns = 0;

        Text header = new Text();
        Text guess = new Text();
        String guessTxt = "Computer generated a number between 1 and 50!";

        TextField answer = new TextField();

        Button submit = new Button("Submit");
        Button giveup = new Button("Give up");

        ImageView imgView = new ImageView();
        Text status = new Text();
        Text live = new Text();
        Text lowHigh = new Text();

        public void typingAnimation(String str) {
            final IntegerProperty i = new SimpleIntegerProperty(0);
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(50),
                    event -> {
                        if (i.get() > str.length()) {
                            timeline.stop();
                        } else {
                            guess.setText(str.substring(0, i.get()));
                            i.set(i.get() + 1);
                        }
                    });
            timeline.getKeyFrames().add(keyFrame);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }

        public void results() throws FileNotFoundException {
            Calculations calculations = new Calculations();
            final int ans = calculations.number;

            submit.setOnMouseClicked(mouseEvent -> {
                status.setVisible(false);
                lowHigh.setVisible(false);
                if (!answer.getText().isBlank()) {
                    usrAns = Integer.valueOf(answer.getText());

                    if (usrAns > 0 && usrAns < 101) {
                        if (usrAns == ans) {
                            status.setFill(Color.BLUE);
                            status.setText("Correct Answer!!");
                            status.setVisible(true);
                        } else {
                            lives -= 1;
                            live.setText("Lives: " + String.valueOf(lives));
                            if (ans > usrAns) {
                                lowHigh.setText(usrAns + " is smaller than actual answer!");
                                lowHigh.setVisible(true);
                            } else if (usrAns > ans) {
                                lowHigh.setText(usrAns + " is larger than actual answer!");
                                lowHigh.setVisible(true);
                            }

                            if (lives == 0) {
                                status.setText("Game Over!!!");
                                status.setVisible(true);
                                lowHigh.setText("The number was " + calculations.number);
                                answer.setText("");
                            }
                        }
                    } else {
                        status.setText("Please Enter Valid Number!!");
                        status.setVisible(true);
                    }
                } else {
                    status.setText("Please Enter Valid Number!!");
                    status.setVisible(true);
                }
                System.out.println("Mouse Clicked" + calculations.number);
            });

            giveup.setOnMouseClicked(mouseEvent -> {
                lowHigh.setText("The number was " + calculations.number);
                answer.setText("");
            });
        }

        public createNodes() throws FileNotFoundException {
            Calculations calculations = new Calculations();
            String gethints = calculations.hint[0];

            header.setText("Guess The Number!");
            header.setFont(Font.font(50));

            guess.setFont(Font.font(25));
            typingAnimation(guessTxt);

            answer.setMaxWidth(150);
            answer.setPrefHeight(50);
            answer.setTranslateY(20);
            answer.setFont(Font.font(20));
            answer.setAlignment(Pos.CENTER);

            submit.setFont(Font.font(20));
            giveup.setFont(Font.font(20));

            FileInputStream io = new FileInputStream("src/main/resources/images/guess.png");
            Image image = new Image(io);
            imgView.setImage(image);
            imgView.setFitWidth(250);
            imgView.setFitHeight(250);
            imgView.setTranslateY(-50);
            imgView.setTranslateX(-260);

            status.setTranslateY(-175);
            status.setFont(Font.font(20));
            status.setFill(Color.RED);

            live.setText("Lives: " + String.valueOf(lives));
            live.setFont(Font.font(20));
            live.setTranslateY(-300);
            live.setTranslateX(250);

            lowHigh.setTranslateY(-190);
            lowHigh.setTranslateX(75);
            lowHigh.setFill(Color.DARKGOLDENROD);
            lowHigh.setFont(Font.font(30));

            results();
        }
    }

    private class createLayout {
        VBox main = new VBox();
        HBox buttons = new HBox();

        public createLayout() throws FileNotFoundException {
            createNodes cN = new createNodes();

            buttons.getChildren().addAll(cN.submit, cN.giveup);
            buttons.setSpacing(75);
            buttons.setAlignment(Pos.CENTER);
            buttons.setTranslateY(40);

            main.setAlignment(Pos.TOP_CENTER);
            main.getChildren().addAll(cN.header, cN.guess, cN.answer, buttons, cN.imgView, cN.status, cN.live, cN.lowHigh);
            main.setPrefSize(750, 250);
            main.setId("main");
        }
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        createLayout cL = new createLayout();
        Scene scene = new Scene(cL.main);
        scene.getStylesheets().add("styles/styles.css");

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
