/* * Samy Touabi - 300184721
   * projectCSI2510_300184721 
   * Date: Automne 2021 
   * 
   * Pour ce devoir, on vous demande de programmer une solution au problème dit des mariages stables.
   * Dans notre cas, le problème consiste à associer des étudiants avec des employeurs. Afin de simplifier
   * ce problème nous allons considérer le cas ou n employeurs désirent embaucher n étudiants; chaque
   * employeur embauchant un seul étudiant. Chaque employeur doit donc classer les étudiants en ordre de
   * préférence (de 1 à n) et les étudiants font de même. La solution que nous recherchons associe chaque
   * employeur avec un étudiant de façon que tous sont aussi satisfaits que possible.
   * 
   * */

// Importation des bibliothèques Java requises pour le bon fonctionnement de ce programme
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner; 
import java.util.HashMap;

public class GaleShapley{

	// Initialiser les variables

	public static boolean validFile = false; // Vérifie si le document entré par l'utilisateur est valide ou non
	
	public static Sue Sue = new Sue(); // La pile Sue contient le n montant d'associations employeurs-étudiants de 0 (au fond) à n-1 (en haut de la pile)
	
	public static int[] students; // Les tableaux de nombres entiers studens et employeurs seront initialisés à n valeurs -1
	public static int[] employers;

	public static String[] namesEmployers; // Ces deux tableaux contiendront les noms de chaque employeur et étidiant pour chaque position tel que donné dans les fichiers
	public static String[] namesStudents;

	public static String[][] aString; // aString contiendra les valeurs (séparés par des virgules) des préférences emploteur-étudiant

	public static int[][] aPQ; // aPQ contiendra les premiers nombres de aString et sert à remplir PQ[] 

	public static int[][] A; // A[][] contiendra les deuxièmes numéros de aString de haut en bas et de droite à gauche (c-à-d A[i][j] = aString[j][i].deuxièmeNuméro)

	public static PQ[] PQ; // Créer un tableau contenant des files de priorités PQ

	public static HashMap<Integer, Integer> map; // Cette map contiendra les associations finales employer-student



	public static void initialize(String fileName){ // Cette méthode donne les bonnes valeurs au variables statiques initialisées ci-haut en se basant 
		                                            // du fichier entré par l'utilisateur

		File file = new File("./" + fileName); // Trouver le path du fichier

		try{ // Un bloc try est nécessaire pour vérifier si fileName entré est valide

			Scanner sc = new Scanner(file); // J'utilise un Scanner pour parcourir le fichier .txt

			validFile = true; // Signaler au bloc While dans main qu'un fichier valide a été entré
		
			int numEmployers = Integer.parseInt(sc.nextLine()); // Trouver le no d'employeurs et d'employés en lisant
                                                                // la première ligne du fichier. sc maintenant à 2e ligne, c-à-d au premier employeur

			for(int i = 0; i < numEmployers; i++){ // Empiler de 0 à numEmployeurs dans la pile Sue          
				Sue.push(i);
			}

			students = new int[numEmployers]; // Donner une taille aux tableaux, celle de PQ[] est donnée plus tard
			employers = new int[numEmployers];
			namesEmployers = new String[numEmployers];
			namesStudents = new String[numEmployers];
			aString = new String[numEmployers][numEmployers];
			aPQ = new int[numEmployers][numEmployers];
			A = new int[numEmployers][numEmployers];

			for(int i = 0; i < numEmployers; i++){ // Donner une valeur initiale de -1 aux valeurs des tableaux students et employers
				students[i] = -1;
				employers[i] = -1;
			}	

			for(int i = 0; i < numEmployers; i++){ // Populer namesEmployers avec les nom des employeurs
				namesEmployers[i] = sc.nextLine();
			}

			for(int i = 0; i < numEmployers; i++){ // Populer namesEmployers avec les nom des étudiants
				namesStudents[i] = sc.nextLine();
			}

			// NOTE: sc est maintenant juste avant le "tableau 2D" dans le fichier .txt

			for (int i = 0; i < aString.length; i++) { // Mettre les rankings employeur-étudiant dans le tableau 2D aString
				for (int j = 0; j < aString.length; j++) {
					aString[i][j] = sc.next();
				}
			}

			// NOTE: sc a maintenant fini de parcourir le fichier .txt en entier
			sc.close(); // Fermer le Scanner


			for (int i = 0; i < aPQ.length; i++) { // Mettre les premiers numeros de aString dans chaque entrée de aPQ
				for (int j = 0; j < aPQ.length; j++) {
					String[] array = aString[i][j].split(",");
					aPQ[i][j] = A[i][j] = Integer.parseInt(array[0]);
				}
			}


			for (int i = 0; i < A.length; i++) { // Mettre les deuxièmes numeros de a1 dans chaque entrée de a2 en allant de haut en bas puis de droite à gauche
				for (int j = 0; j < A.length; j++) {
					String[] array = aString[j][i].split(","); // NOTE: aString[j][i] a été écrit et non pas aString[i][j]
					A[i][j] = Integer.parseInt(array[1]);
				}
			}

			PQ = new PQ[numEmployers]; // Initialiser la taille de PQ[]
			
			for(int i = 0; i < numEmployers; i++){ // Créer des files de priorités dans le tableau
				PQ[i] = new PQ();
			}

			for(int i = 0; i < numEmployers; i++){ // Populer les files des priorités
				
				for (int j = 0; j < numEmployers; j++) {
					int num1 = aPQ[i][j]; // Chercher les numéros de chaque entrée de aPQ[][]
					int num2 = j; // Le deuxième numéro est toujours 0,1,2,3...	
					PQ[i].insert(new Couple(num1, num2));
				}
			}

		}

		catch(FileNotFoundException e){
			System.out.println("Vous n'avez pas entre un nom de fichier valide. Essayez encore."); // Message d'erreur au cas ou le nom du fichier est invalide
			// e.printStackTrace();
		}

	} // Fin de la méthode Initialize(String fileName)

	public static HashMap<Integer, Integer> execute(){ // Cette méthode met en marche l'algorithme Gale-Shapley à l'aide des variables
													   // mises en oeuvre lors de la méthode Initialize(String fileName) et retourne un
													   // ensemble de paires stables à l'aide d'une HashMap.
		while (!Sue.empty()){
			int e1 = Sue.pop();                // e1 cherche un étidiant s 
			int s = PQ[e1].removeMin();        // Étudiant préféré de l'employeur e1
			int e2 = students[s];
			if(students[s] == -1){             // Étudiant s n'est pas pairé
				students[s] = e1;
				employers[e1] = s;             // Paire (e1,s) créée
			}

			else if(A[s][e1] < A[s][e2]){      // s préfère e1 à e2
				students[s] = e1;
				employers[e1] = s;             // Remplace la paire
				employers[e2] = -1;            // Maintenant non-pairé         
				Sue.push(e2);
			}

			else{ 							   // s rejette l'offre de e1
				Sue.push(e1);
			}
		}

		map = new HashMap<Integer, Integer>(); // Initialisation de la HashMap

		for (int i = 0; i < aString.length; i++) {
			map.put(i, employers[i]); // Jumelage des paires dans la HashMap
		}

		return map; // La méthode retourne le jumelage des paires

	} // Fin de la méthode execute()

	public static void save(String fileName){ // Cette méthode prend les jumelage des paires retournés par execute() et créée un nouveau fichier .txt
											  // avec les paires.						
		try {
			FileWriter myWriter = new FileWriter("matches_" +  fileName); // Nom du nouveau fichier
			int n = namesEmployers.length - 1; 
			for(int i = 0; i < n; i++){ // Arrêter la boucle à n-1 afin de ne pas créer une nouvelle ligne après la dernière paire
				myWriter.write("Match " + i + ": " + namesEmployers[i] + " - " + namesStudents[map.get(i)] + "\n");
			}
			myWriter.write("Match " + n + ": " + namesEmployers[n] + " - " + namesStudents[map.get(n)]); // Écrire la dernière ligne sans créer 
			                                                                                             // de ligne vide après elle
			myWriter.close();
			System.out.println("Ecriture des resultats dans le nouveau fichier completee.");

		  } catch (IOException e) {
			System.out.println("Il y a eu une erreur lors de l'ecriture du nouveau fichier..."); // Message au cas ou il y aurait une IOException
			// e.printStackTrace();
		  }

	} // Fin de la méthode save(String fileName)

	public static void main(String[] args){

		String fileName = "";

		while(!validFile){ // Utiliser une boucle qui reste active jusqu'à ce qu'un nom de fichier valide soit entrer

			Scanner myObj = new Scanner(System.in); // Demander à l'utilisateur le nom du fichier à entrer
			
			System.out.println("Entrer le nom du fichier (en incluant .txt a la fin) :");

			fileName = myObj.nextLine(); // Le programme prendra ce que l'utilisateur entre pour combler la varialbe filename

			System.out.println();
		
			initialize(fileName); // Appel de la fonction initialize(String fileName) pour combler les variables statique de GaleShapley.java

			myObj.close(); // Fermeture du Scanner myObj
		}
		
		execute(); // Appel de la fonction execute() pour exécuter l'algorithme GaleShapley à l'aide des variables de classe précédemment comblés
		save(fileName); // Appel de la fonction save(String fileName) pour créer un nouveau ficher avec toutes les paires stables en faveur des employeurs

	} // Fin de la méthode main(String[] args)

}