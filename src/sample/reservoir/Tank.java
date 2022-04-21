package sample.reservoir;

public class Tank {
    public Pump pumpOut = new Pump();
    public Pump pumpIn = new Pump();
    public boolean isActive = false;
    public int max = 100;
    public int min = 0;
    public int level = 0;

    public void setMin(int min){
        this.min = min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public void setLvl(int lvl){
        this.level = lvl;
    }

    public void setAct(boolean active){ this.isActive = active;}

    public String getMin(){return String.valueOf(this.min);}

    public String getMax(){return String.valueOf(this.max);}

    public int getLvl(){return this.level;}

    public boolean getAction(){return this.isActive;}

    public void changeLvl(int k){
        if(!(k<0&&this.level<min-k||k>0&&this.level>max-k))
        this.level+=k;
        else if(k>0)this.level=max;
        else this.level=min;
    }

    public String getInfo(){
        return "Сост. работы : " + this.isActive +
                "\n Верхний допустимый уровень: " + this.max +
                "\n Нижний допустимый уровень: " + this.min +
                "\n Текущий уровень: " + this.level +
                "\n Насос откачки: " + "-------------" +
                "\n Сост. работы: " + this.pumpOut.getActive() +
                "\n Скорость: " + this.pumpOut.getSpeed() +
                "\n Насос закачки: " + "-------------" +
                "\n Сост. работы: " + this.pumpIn.getActive() +
                "\n Скорость: " + this.pumpIn.getSpeed();
    }

}
