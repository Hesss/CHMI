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

    public void setAct(boolean active){
        this.isActive = active;
    }

    public String getMin(){return String.valueOf(this.min);}

    public String getMax(){return String.valueOf(this.max);}

    public boolean getAction(){return this.isActive;}

    public void changeLvl(int k){
        if(!(k<0&&this.level<min-k||k>0&&this.level>max-k))
        this.level+=k;
        else if(k>0)this.level=max;
        else if(k<0)this.level=min;
    }

    public void setPump(boolean active, int speed, boolean choice){ // choice = true - In
        if(choice){
            //this.pumpIn.setActive(active);
            //this.pumpIn.setSpeed(speed);
        }
        else{
            //this.pumpOut.setActive(active);
            //this.pumpOut.setSpeed(speed);
        }
    }

    public String getInfo(){
        return "Сост. работы : " + String.valueOf(this.isActive) +
                "\n Верхний допустимый уровень: "  + String.valueOf(this.max) +
                "\n Нижний допустимый уровень: "  + String.valueOf(this.min) +
                "\n Текущий уровень: "  + String.valueOf(this.level) +
                "\n Насос откачки: "  + "-------------" +
                "\n Сост. работы: "  + String.valueOf(this.pumpOut.getActive()) +
                "\n Скорость: "  + String.valueOf(this.pumpOut.getSpeed()) +
                "\n Насос закачки: "  + "-------------" +
                "\n Сост. работы: "  + String.valueOf(this.pumpIn.getActive()) +
                "\n Скорость: "  + String.valueOf(this.pumpIn.getSpeed());
    }

}
