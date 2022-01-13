/* * Samy Touabi - 300184721
   * projectCSI2510_300184721 
   * Date: Automne 2021 
   * 
   * Cette classe Sue étend simplement la classe PriorityQueue et prend en argument 
   * des objets Couple que j'ai créé et sera servie comme TAD dans GaleShapley.java 
   * 
   * */

import java.util.PriorityQueue;

public class PQ extends PriorityQueue<Couple>{

   public int removeMin(){ // Complexité temporelle: O(log n)
      return this.poll().getNum2();
   }

   public boolean insert(Couple couple){ // Complexité temporelle: O(log n)
      return this.add(couple);
   }
  

}