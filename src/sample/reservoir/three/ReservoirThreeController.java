package sample.reservoir.three;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Journal;
import sample.Note;
import sample.menu.MainController;
import sample.reservoir.Tank;
import sample.reservoir.two.ReservoirTwo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservoirThreeController {

    @FXML
    public TextField max;
    @FXML
    public TextField min;
    @FXML
    public Label tankActive;
    @FXML
    public Label errorMsg;
    @FXML
    public TextArea areaData;
    @FXML
    public Button pumpOutA;
    @FXML
    public Button pumpInA;
    @FXML
    public TextField outspeed;
    @FXML
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
    @FXML
    void setMax() throws IOException {
        try {
            if(Integer.parseInt(max.getText())<0){errorMsg.setText(errors[5]);}
            else {
                if (Integer.parseInt(max.getText()) <= MainController.tankThree.min) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else {
                    errorMsg.setText(errors[0]);
                    MainController.tankThree.setMax(Integer.parseInt(max.getText()));
                    if (MainController.tankThree.max < MainController.tankThree.level) { // Если верхний уровень ниже текущего, изменить текущий до верхнего
                        MainController.tankThree.setLvl(Integer.parseInt(MainController.tankThree.getMax()));
                    }
                    Date date = new Date();
                    SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                    note.setNote(formatForDate.format(date) + " Резервуар №3: верхний допустимый уровень изменен на " + MainController.tankThree.getMax());
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
    void setMin() throws IOException {
        try {
            if (Integer.parseInt(min.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                if (Integer.parseInt(min.getText()) >= MainController.tankThree.max) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else
                    errorMsg.setText(errors[0]);
                MainController.tankThree.setMin(Integer.parseInt(min.getText())); //если нижний уровень меньше текущего уровня, текущий = нижний
                if (MainController.tankThree.min > MainController.tankThree.level) {
                    MainController.tankThree.setLvl(Integer.parseInt(MainController.tankThree.getMin()));
                    // errorMsg.setText("Нижний");
                }
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №3: нижний допустимый уровень изменен на " + MainController.tankThree.getMin());
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
        if (MainController.tankThree.getAction()) {
            MainController.tankThree.setAct(false);
            tankActive.setText("Выкл");
            MainController.tankThree.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            MainController.tankThree.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №3: выключен");
        } else {
            MainController.tankThree.setAct(true);
            tankActive.setText("Вкл");
            timer1.start(); // Запускается каждые 5 секунд изменение уровня нефти!
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №3: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }


    @FXML
    public void getInfo() throws IOException {
        areaData.setText(MainController.tankThree.getInfo());
        timer1.start();
    } // Кнопка Вывести Данные


    @FXML
    void pumpOutActive() throws IOException {   // Кнопка включения насоса откачки
        if (MainController.tankThree.pumpOut.getActive()) {
            MainController.tankThree.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №3: Насос откачки: выключен");
        } else {
            MainController.tankThree.pumpOut.setActive(true);
            pumpOutA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №3: Насос откачки: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }

    @FXML
    void outSpeed() throws IOException {
        try {
            if(Integer.parseInt(outspeed.getText())<0){errorMsg.setText(errors[5]);}
            else {
                errorMsg.setText(errors[0]);
                MainController.tankThree.pumpOut.setSpeed(Integer.parseInt(outspeed.getText()));
                MainController.tankThree.pumpOut.setActive(true);
                pumpOutA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №3: Насос откачки: скорость изменена на " + MainController.tankThree.pumpOut.getSpeed());
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
        if (MainController.tankThree.pumpIn.getActive()) {
            MainController.tankThree.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №3: Насос закачки: выключен");
        } else {
            MainController.tankThree.pumpIn.setActive(true);
            pumpInA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №3: Насос закачки: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }

    @FXML
    void inSpeed() throws IOException {
        try {
            if (Integer.parseInt(inspeed.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                errorMsg.setText(errors[0]);
                MainController.tankThree.pumpIn.setSpeed(Integer.parseInt(inspeed.getText()));
                MainController.tankThree.pumpIn.setActive(true);
                pumpInA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №3: Насос закачки: скорость изменена на " + MainController.tankThree.pumpIn.getSpeed());
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
            int k = MainController.tankThree.pumpIn.getSpeed() - MainController.tankThree.pumpOut.getSpeed();
            MainController.tankThree.changeLvl(k);
            areaData.setText(MainController.tankThree.getInfo());
            System.out.println(MainController.tankThree.level);
            if(MainController.tankThree.level==MainController.tankThree.max&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №3: Резервуар заполнен");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankThree.level==MainController.tankThree.min&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №3: Резервуар пуст");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankThree.max>MainController.tankThree.level&&MainController.tankThree.level>MainController.tankThree.min)limit = false;
        }
    });

}