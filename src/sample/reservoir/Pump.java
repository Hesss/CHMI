package sample.reservoir;

public class Pump {
    public boolean isActive = false;
    public int speed = 0;

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
        if(!isActive){
            this.speed=0;
        }
    }

    public int getSpeed(){
        return this.speed;
    }

    public boolean getActive(){
        return this.isActive;
    }

    public String getInfo(){
        return String.valueOf(this.isActive) + " "  + String.valueOf(this.speed);
    }

}
