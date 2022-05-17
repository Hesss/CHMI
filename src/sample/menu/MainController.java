package sample.menu;


import javafx.fxml.FXML;
import sample.journal.JournalPage;
import sample.reservoir.Tank;
import sample.reservoir.five.ReservoirFive;
import sample.reservoir.four.ReservoirFour;
import sample.reservoir.one.ReservoirOne;
import sample.reservoir.three.ReservoirThree;
import sample.reservoir.two.ReservoirTwo;

public class MainController{

    public static Tank tankOne = new Tank();
    public static Tank tankTwo = new Tank();
    public static Tank tankThree = new Tank();
    public static Tank tankFour = new Tank();
    public static Tank tankFive = new Tank();

    public static int reservoir = 1;

    //todo сделать в главном таблицу с инфой о всех резервах, и журнал (текщая сессия +кнопка вся история)
    //todo
    @FXML
    void one() throws Exception {
        ReservoirOne firstReservoir = new ReservoirOne();
        firstReservoir.showWindow();
    }

    @FXML
    void two() throws Exception {
        ReservoirTwo SecondReservoir = new ReservoirTwo();
        SecondReservoir.showWindow();
    }

    @FXML
    void three() throws Exception {
        ReservoirThree thirdReservoir = new ReservoirThree();
        thirdReservoir.showWindow();
    }

    @FXML
    void four() throws Exception {
        ReservoirFour fourReservoir = new ReservoirFour();
        fourReservoir.showWindow();
    }

    @FXML
    void five() throws Exception {
        ReservoirFive fiveReservoir = new ReservoirFive();
        fiveReservoir.showWindow();
    }

    @FXML
    void journal() throws Exception {
        JournalPage j = new JournalPage();
        j.showWindow();
    }


}