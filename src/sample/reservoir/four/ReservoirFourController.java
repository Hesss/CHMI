package sample.reservoir.four;

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

public class ReservoirFourController {

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
            "Введите целое число"
    };

    @FXML
    void setMax() throws IOException {
        try {
            if(Integer.parseInt(max.getText())<0){errorMsg.setText(errors[5]);}
            else {
                if (Integer.parseInt(max.getText()) <= MainController.tankFour.min) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else {
                    errorMsg.setText(errors[0]);
                    MainController.tankFour.setMax(Integer.parseInt(max.getText()));
                    if (MainController.tankFour.max < MainController.tankFour.level) { // Если верхний уровень ниже текущего, изменить текущий до верхнего
                        MainController.tankFour.setLvl(Integer.parseInt(MainController.tankFour.getMax()));
                    }
                    Date date = new Date();
                    SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                    note.setNote(formatForDate.format(date) + " Резервуар №4: верхний допустимый уровень изменен на " + MainController.tankTwo.getMax());
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
                if (Integer.parseInt(min.getText()) >= MainController.tankFour.max) {
                    errorMsg.setText(errors[2]);
                } // если max<min -> ошибка
                else
                    errorMsg.setText(errors[0]);
                MainController.tankFour.setMin(Integer.parseInt(min.getText())); //если нижний уровень меньше текущего уровня, текущий = нижний
                if (MainController.tankFour.min > MainController.tankFour.level) {
                    MainController.tankFour.setLvl(Integer.parseInt(MainController.tankFour.getMin()));
                    // errorMsg.setText("Нижний");
                }
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №4: нижний допустимый уровень изменен на " + MainController.tankFour.getMin());
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
        if (MainController.tankFour.getAction()) {
            MainController.tankFour.setAct(false);
            tankActive.setText("Выкл");
            MainController.tankFour.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            MainController.tankFour.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №4: выключен");
        } else {
            MainController.tankFour.setAct(true);
            tankActive.setText("Вкл");
            timer1.start(); // Запускается каждые 5 секунд изменение уровня нефти!
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №4: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }


    @FXML
    public void getInfo() throws IOException {
        areaData.setText(MainController.tankFour.getInfo());
        timer1.start();
    } // Кнопка Вывести Данные


    @FXML
    void pumpOutActive() throws IOException {   // Кнопка включения насоса откачки
        if (MainController.tankFour.pumpOut.getActive()) {
            MainController.tankFour.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №4: Насос откачки: выключен");
        } else {
            MainController.tankFour.pumpOut.setActive(true);
            pumpOutA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №4: Насос откачки: включен");
        }
        Journal.setList(note);
        Journal.Save();
    }

    @FXML
    void outSpeed() throws IOException {
        try {
            if (Integer.parseInt(outspeed.getText()) < 0) {
                errorMsg.setText(errors[5]);
            } else {
                errorMsg.setText(errors[0]);
                MainController.tankFour.pumpOut.setSpeed(Integer.parseInt(outspeed.getText()));
                MainController.tankFour.pumpOut.setActive(true);
                pumpOutA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №4: Насос откачки: скорость изменена на " + MainController.tankFour.pumpOut.getSpeed());
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
        if (MainController.tankFour.pumpIn.getActive()) {
            MainController.tankFour.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №4: Насос закачки: выключен");
        } else {
            MainController.tankFour.pumpIn.setActive(true);
            pumpInA.setText("Включено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date) + " Резервуар №4: Насос закачки: включен");
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
                MainController.tankFour.pumpIn.setSpeed(Integer.parseInt(inspeed.getText()));
                MainController.tankFour.pumpIn.setActive(true);
                pumpInA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №4: Насос закачки: скорость изменена на " + MainController.tankFour.pumpIn.getSpeed());
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
            int k = MainController.tankFour.pumpIn.getSpeed() - MainController.tankFour.pumpOut.getSpeed();
            MainController.tankFour.changeLvl(k);
            areaData.setText(MainController.tankFour.getInfo());
            System.out.println(MainController.tankFour.level);
            if(MainController.tankFour.level==MainController.tankFour.max&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №4: Резервуар заполнен");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankFour.level==MainController.tankFour.min&&!limit){
                limit = true;
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date) + " Резервуар №4: Резервуар пуст");
                Journal.setList(note);
                try {
                    Journal.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if(MainController.tankFour.max>MainController.tankFour.level&&MainController.tankFour.level>MainController.tankFour.min)limit = false;
        }
    });

}