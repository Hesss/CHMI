package sample.reservoir.two;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Journal;
import sample.Note;
import sample.menu.MainController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservoirTwoController {


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
                if (Integer.parseInt(max.getText()) <= MainController.tankTwo.min) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else {
                    errorMsg.setText(errors[0]);
                    MainController.tankTwo.setMax(Integer.parseInt(max.getText()));
                    if (MainController.tankTwo.max < MainController.tankTwo.level) { // Если верхний уровень ниже текущего, изменить текущий до верхнего
                        MainController.tankTwo.setLvl(Integer.parseInt(MainController.tankTwo.getMax()));
                    }
                    Date date = new Date();
                    SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                    note.setNote(formatForDate.format(date) + " Резервуар №2: верхний допустимый уровень изменен на " + MainController.tankTwo.getMax());
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
                if (Integer.parseInt(min.getText()) >= MainController.tankTwo.max) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else
                    errorMsg.setText(errors[0]);
                MainController.tankTwo.setMin(Integer.parseInt(min.getText())); //если нижний уровень меньше текущего уровня, текущий = нижний
                if (MainController.tankTwo.min > MainController.tankTwo.level) {
                    MainController.tankTwo.setLvl(Integer.parseInt(MainController.tankTwo.getMin()));
                    // errorMsg.setText("Нижний");
                }
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №2: нижний допустимый уровень изменен на " + MainController.tankTwo.getMin());
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
        if (MainController.tankTwo.getAction()) {
            MainController.tankTwo.setAct(false);
            tankActive.setText("Выкл");
            MainController.tankTwo.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            MainController.tankTwo.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №2: выключен");
        } else {
            MainController.tankTwo.setAct(true);
            tankActive.setText("Вкл");
            timer1.start(); // Запускается каждые 5 секунд изменение уровня нефти!
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №2: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }


    @FXML
    public void getInfo() throws IOException {
        timer1.start();
        areaData.setText(MainController.tankTwo.getInfo());
    } // Кнопка Вывести Данные


    @FXML
    void pumpOutActive() throws IOException {   // Кнопка включения насоса откачки
        if (MainController.tankTwo.pumpOut.getActive()) {
            MainController.tankTwo.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №2: Насос откачки: выключен");
        } else {
            MainController.tankTwo.pumpOut.setActive(true);
            pumpOutA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №2: Насос откачки: включен");
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
                MainController.tankTwo.pumpOut.setSpeed(Integer.parseInt(outspeed.getText()));
                MainController.tankTwo.pumpOut.setActive(true);
                pumpOutA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №2: Насос откачки: скорость изменена на " + MainController.tankTwo.pumpOut.getSpeed());
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
        if (MainController.tankTwo.pumpIn.getActive()) {
            MainController.tankTwo.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №2: Насос закачки: выключен");
        } else {
            MainController.tankTwo.pumpIn.setActive(true);
            pumpInA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №2: Насос закачки: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }

    @FXML
    void inSpeed() throws IOException {
        try {
            if(Integer.parseInt(inspeed.getText())<0){errorMsg.setText(errors[5]);}
            else {
                errorMsg.setText(errors[0]);
                MainController.tankTwo.pumpIn.setSpeed(Integer.parseInt(inspeed.getText()));
                MainController.tankTwo.pumpIn.setActive(true);
                pumpInA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №2: Насос закачки: скорость изменена на " + MainController.tankTwo.pumpIn.getSpeed());
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
            int k = MainController.tankTwo.pumpIn.getSpeed() - MainController.tankTwo.pumpOut.getSpeed();
            MainController.tankTwo.changeLvl(k);
            areaData.setText(MainController.tankTwo.getInfo());
            System.out.println(MainController.tankTwo.level);
            if(MainController.tankTwo.level==MainController.tankTwo.max&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №2: Резервуар заполнен");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankTwo.level==MainController.tankTwo.min&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №2: Резервуар пуст");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankTwo.max>MainController.tankTwo.level&&MainController.tankTwo.level>MainController.tankTwo.min)limit = false;
        }
    });

}