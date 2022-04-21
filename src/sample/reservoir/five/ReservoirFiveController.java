package sample.reservoir.five;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Journal;
import sample.Note;
import sample.menu.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservoirFiveController {

    @FXML
    public TextField max;
    public TextField min;
    public Label tankActive;
    public Label errorMsg;
    public TextArea areaData;
    public Button pumpOutA;
    public Button pumpInA;
    public TextField outspeed;
    public TextField inspeed;

    Note note = new Note();
    String[] errors = new String[]{
            " ",
            "Введите целое число",
            "Нижний допустимый уровень выше максимума",
            "Резервуар заполнен",
            "Резервуар пуст",
            "Введите положительное число"
    };

    String objName = "Резервуар №5:";

    @FXML
    void setMax(){
        try {
            if (Integer.parseInt(max.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                if (Integer.parseInt(max.getText()) <= MainController.tankFive.min) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else {
                    errorMsg.setText(errors[0]);
                    MainController.tankFive.setMax(Integer.parseInt(max.getText()));
                    if (MainController.tankFive.max < MainController.tankFive.level) { // Если верхний уровень ниже текущего, изменить текущий до верхнего
                        MainController.tankFive.setLvl(Integer.parseInt(MainController.tankFive.getMax()));
                    }
                    Date date = new Date();
                    SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                    note.setNote(formatForDate.format(date), objName, "верхний допустимый уровень изменен на " + MainController.tankFive.getMax());
                    Journal.setList(note);
                    Journal.Save();
                }
            }
        }
        catch (Exception e){
            errorMsg.setText(errors[1]);
        }

    } // Кнопка Задать Max

    @FXML
    void setMin(){
        try {
            if (Integer.parseInt(min.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                if (Integer.parseInt(min.getText()) >= MainController.tankFive.max) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else
                    errorMsg.setText(errors[0]);
                MainController.tankFive.setMin(Integer.parseInt(min.getText())); //если нижний уровень меньше текущего уровня, текущий = нижний
                if (MainController.tankFive.min > MainController.tankFive.level) {
                    MainController.tankFive.setLvl(Integer.parseInt(MainController.tankFive.getMin()));
                    // errorMsg.setText("Нижний");
                }
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "нижний допустимый уровень изменен на " + MainController.tankFive.getMin());
                Journal.setList(note);
                Journal.Save();
            }
        }
        catch (Exception e){
            errorMsg.setText(errors[1]);
        }
    } // Кнопка Задать Min

    @FXML
    void tankAction() throws IOException {  // Кнопка Вкл/Выкл
        if (MainController.tankFive.getAction()) {
            MainController.tankFive.setAct(false);
            tankActive.setText("Выкл");
            MainController.tankFive.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            MainController.tankFive.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "выключен");
        } else {
            MainController.tankFive.setAct(true);
            tankActive.setText("Вкл");
            timer1.start(); // Запускается каждые 5 секунд изменение уровня нефти!
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "включен");
        }
        Journal.setList(note);
        Journal.Save();
    }


    @FXML
    public void getInfo() throws IOException {
        areaData.setText(MainController.tankFive.getInfo());
        timer1.start();
    } // Кнопка Вывести Данные


    @FXML
    void pumpOutActive() throws IOException {   // Кнопка включения насоса откачки
        if (MainController.tankFive.pumpOut.getActive()) {
            MainController.tankFive.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "Насос откачки: выключен");
        } else {
            MainController.tankFive.pumpOut.setActive(true);
            pumpOutA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "Насос откачки: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }

    @FXML
    void outSpeed(){
        try {
            if (Integer.parseInt(outspeed.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                errorMsg.setText(errors[0]);
                MainController.tankFive.pumpOut.setSpeed(Integer.parseInt(outspeed.getText()));
                MainController.tankFive.pumpOut.setActive(true);
                pumpOutA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "Насос откачки: скорость изменена на " + MainController.tankFive.pumpOut.getSpeed());
                Journal.setList(note);
                Journal.Save();
            }
        }
        catch (Exception e){
            errorMsg.setText(errors[1]);
        }
    }

    @FXML
    void pumpInActive() throws IOException {    // Кнопка включения насоса закачки
        if (MainController.tankFive.pumpIn.getActive()) {
            MainController.tankFive.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "Насос закачки: выключен");
        } else {
            MainController.tankFive.pumpIn.setActive(true);
            pumpInA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "Насос закачки: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }

    @FXML
    void inSpeed(){
        try {
            if (Integer.parseInt(inspeed.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                errorMsg.setText(errors[0]);
                MainController.tankFive.pumpIn.setSpeed(Integer.parseInt(inspeed.getText()));
                MainController.tankFive.pumpIn.setActive(true);
                pumpInA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "Насос закачки: скорость изменена на " + MainController.tankFive.pumpIn.getSpeed());
                Journal.setList(note);
                Journal.Save();
            }
        }
        catch (Exception e){
            errorMsg.setText(errors[1]);
        }
    }

    Timer timer1 = new Timer(1000, new ActionListener(){
        boolean limit = false;
        public void actionPerformed(ActionEvent e){
            int k = MainController.tankFive.pumpIn.getSpeed() - MainController.tankFive.pumpOut.getSpeed();
            MainController.tankFive.changeLvl(k);
            areaData.setText(MainController.tankFive.getInfo());
            System.out.println(MainController.tankFive.level);
            if(MainController.tankFive.level==MainController.tankFive.max&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "Резервуар заполнен");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankFive.level==MainController.tankFive.min&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "Резервуар пуст");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankFive.max>MainController.tankFive.level&&MainController.tankFive.level>MainController.tankFive.min)limit = false;
        }
    });

}