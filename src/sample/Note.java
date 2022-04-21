package sample;

public class Note {
    public String data = "";
    public String objName = "";
    public String changes = "";
    public String note = "";

    public void setNote(String data, String objName, String changes){
        this.data = data;
        this.objName = objName;
        this.changes = changes;
        this.note = data + " " + objName + " " + changes;
    }

    public String getNote(){
        return this.note;
    }
}
