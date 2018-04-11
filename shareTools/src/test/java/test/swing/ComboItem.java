package test.swing;

public class ComboItem{
private String value;
private String text;
public String getValue() {
   return value;
}
public void setValue(String value) {
   this.value = value;
}
public String getText() {
   return text;
}
public void setText(String text) {
   this.text = text;
}

public ComboItem(String value,String text){
   this.value = value;
   this.text = text;
}
public String toString(){
   return this.text;
}
}