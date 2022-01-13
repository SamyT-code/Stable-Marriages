/* * Samy Touabi - 300184721
   * projectCSI2510_300184721 
   * Date: Automne 2021 
   * 
   * Le but de cette classe est de créer un objet qui peut entrer en tant que paire dans la file de priorité avec une clé et une valeur
   * 
   * */

public class Couple implements Comparable<Couple> {

    public int num1; // Key-Index
    public int num2; // Value

    public Couple(int num1, int num2){
        this.num1 = num1;
        this.num2 = num2;
    }

    // Getters de la classe Couple
    public int getNum1(){
        return this.num1;
    }

    public int getNum2(){
        return this.num2;
    }

    @Override
    public int compareTo(Couple other){ // Cette méthode override la méthode compareTo dans l'interface Comparable
        
        if(this.num1 < other.num1){
            return -1;
        }
        else if(this.num1 > other.num1){
            return 1;
        }
        return 0;
    }
    
}
