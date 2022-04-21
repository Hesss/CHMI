package sample.reservoir.one;

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

public class ReservoirOneController {


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

    String objName = "Резервуар №1:";
    @FXML
    void setMax(){
        try {
            if(Integer.parseInt(max.getText())<0){errorMsg.setText(errors[5]);}
            else
            if(Integer.parseInt(max.getText())<=MainController.tankOne.min){errorMsg.setText(errors[2]);} // если max<min -> ошибка
            else {
                errorMsg.setText(errors[0]);
                MainController.tankOne.setMax(Integer.parseInt(max.getText()));
                if(MainController.tankOne.max<MainController.tankOne.level){ // Если верхний уровень ниже текущего, изменить текущий до верхнего
                    MainController.tankOne.setLvl(Integer.parseInt(MainController.tankOne.getMax()));
                }
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "верхний допустимый уровень изменен на " + MainController.tankOne.getMax());
                Journal.setList(note);
                Journal.Save();
            }
        }
        catch (Exception e){
            errorMsg.setText(errors[1]);
        }

    } // Кнопка Задать Max

    @FXML
    void setMin(){
        try {
            if(Integer.parseInt(min.getText())<0){errorMsg.setText(errors[5]);}
            else
            if (Integer.parseInt(min.getText()) >= MainController.tankOne.max) {
                errorMsg.setText(errors[2]);
            } // если max<min -> ошибка
            else
                errorMsg.setText(errors[0]);
                MainController.tankOne.setMin(Integer.parseInt(min.getText())); //если нижний уровень меньше текущего уровня, текущий = нижний
            if (MainController.tankOne.min > MainController.tankOne.level) {
                MainController.tankOne.setLvl(Integer.parseInt(MainController.tankOne.getMin()));
                // errorMsg.setText("Нижний");
            }
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "нижний допустимый уровень изменен на " + MainController.tankTwo.getMin());
            Journal.setList(note);
            Journal.Save();
        }
        catch (Exception e){
            errorMsg.setText(errors[1]);
        }
    } // Кнопка Задать Min

    @FXML
    void tankAction() throws IOException {  // Кнопка Вкл/Выкл
        if (MainController.tankOne.getAction()) {
            MainController.tankOne.setAct(false);
            tankActive.setText("Выкл");
            MainController.tankOne.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            MainController.tankOne.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "выключен");
        } else {
            timer1.start();
            MainController.tankOne.setAct(true);
            tankActive.setText("Вкл");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "включен");
        }
        Journal.setList(note);
        Journal.Save();
    }


    @FXML
    public void getInfo() throws IOException {
        areaData.setText(MainController.tankOne.getInfo());
        timer1.start(); // Запускается каждые 5 секунд изменение уровня нефти!
    } // Кнопка Вывести Данные


    @FXML
    void pumpOutActive() throws IOException {   // Кнопка включения насоса откачки
        if (MainController.tankOne.pumpOut.getActive()) {
            MainController.tankOne.pumpOut.setActive(false);
            pumpOutA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "Насос откачки: выключен");
        } else {
            MainController.tankOne.pumpOut.setActive(true);
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
            if(Integer.parseInt(outspeed.getText())<0){errorMsg.setText(errors[5]);}
            else {
                errorMsg.setText(errors[0]);
                MainController.tankOne.pumpOut.setSpeed(Integer.parseInt(outspeed.getText()));
                MainController.tankOne.pumpOut.setActive(true);
                pumpOutA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "Насос откачки: скорость изменена на " + MainController.tankOne.pumpOut.getSpeed());
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
        if (MainController.tankOne.pumpIn.getActive()) {
            MainController.tankOne.pumpIn.setActive(false);
            pumpInA.setText("Выключено");
            Date date = new Date();
            SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
            note.setNote(formatForDate.format(date), objName, "Насос закачки: выключен");
        } else {
            MainController.tankOne.pumpIn.setActive(true);
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
            if(Integer.parseInt(inspeed.getText())<0){errorMsg.setText(errors[5]);}
            else {
                errorMsg.setText(errors[0]);
                MainController.tankOne.pumpIn.setSpeed(Integer.parseInt(inspeed.getText()));
                MainController.tankOne.pumpIn.setActive(true);
                pumpInA.setText("Включено");
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("MM.dd '|' hh:mm:ss '|'");
                note.setNote(formatForDate.format(date), objName, "Насос закачки: скорость изменена на " + MainController.tankOne.pumpIn.getSpeed());
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
            int k = MainController.tankOne.pumpIn.getSpeed() - MainController.tankOne.pumpOut.getSpeed();
            MainController.tankOne.changeLvl(k);
            areaData.setText(MainController.tankOne.getInfo());
            System.out.println(MainController.tankOne.level);
            if(MainController.tankOne.level==MainController.tankOne.max&&!limit){ //если уровень достиг максимума
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
            else if(MainController.tankOne.level==MainController.tankOne.min&&!limit){ //если уровень достиг минимума
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
            else if(MainController.tankOne.max>MainController.tankOne.level&&
                    MainController.tankOne.level>MainController.tankOne.min){
                limit = false;
            } // если текущий уровень в пределе
        }
    });

}

