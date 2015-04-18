/*************************************************************************
 *  Compilation:  javac Person.java
 *  Execution:    java Person
 *
 *  Have the information of a Person: age, wealth, life_expectacy, metabolism
 *  vision. When Update is called, age get older metabolism consume wealth. 
 *
 *  Example 
 *  % java Person
 *  
 * Create 1 person
 * -----------------------------------------
 * age: 0
 * life_expectancy: 64
 * metabolism: 9
 * wealth: 18
 * vision:3
 * -----------------------------------------
 * Updating Person
 * -----------------------------------------
 * age: 1
 * life_expectancy: 64
 * metabolism: 9
 * wealth: 9
 * vision:3
 *
 * if metabolism > wealth or age is > life_expentacy, all parameters are reset
 *
 *  % java Person
 *
 *  Create 1 person
 * -----------------------------------------
 * age: 0
 * life_expectancy: 11
 * metabolism: 8
 * wealth: 5
 * vision:2
 * -----------------------------------------
 * Updating Person
 * -----------------------------------------
 * age: 0
 * life_expectancy: 6
 * metabolism: 2
 * wealth: 16
 * vision:5
 * 
 *************************************************************************/
  
public class Person {

    // variables far all persons 

    public static int life_expectancy_max = 100;    // (1-100)
    public static int life_expectancy_min = 1;      // (1-100)
    public static int metabolism_max = 10;           // (1-25)
    public static int vision_max = 5;               // (1-15)

    public int col;
    public int row;


    // Variables for individual persons

	public int age;             //Current age of people.
    public int wealth;          //Current amount of grains owned.
    public int life_expectancy; //Until which age this person will live.
    public int metabolism;      //How much grains this person needs to eat.
    public int vision;          //How far this person can look for grains. 

    public char myClass;        // p = poor, r = rich, m = middle



    public Person(){
        // Inital values generated for each person 

        set_variables();
        age = randomInt(0,life_expectancy);
    }

    public void Update(){
    // method to consume wealth and reset if the person is dead

        wealth = wealth - metabolism;
        age = age + 1 ; 
        if (age >= life_expectancy || wealth < 0) { // Checking if is Dead                                            
            set_variables();                        // Reset parameters
        }
    }

    public void updateLocation(Location[][] all_Location ){
        // Method determinates the best next location for a person 
        // depending on their vision

        int gRight = 0;
        int gLeft = 0;
        int gUp = 0;
        int gDown = 0;

        for (int i = 1; i <= vision; i++){
            gRight += all_Location[Main.moveColRight(col,i)][row].grains_here;
            gLeft += all_Location[Main.moveColLeft(col,i)][row].grains_here;
            gUp += all_Location[col][Main.moveRowUp(row,i)].grains_here;
            gDown += all_Location[col][Main.moveRowDown(row,i)].grains_here;
        }

        if (gRight >= gLeft && gRight >= gUp && gRight >= gDown){
            all_Location[Main.moveColRight(col,1)][row].addPerson(this);
            col = Main.moveColRight(col,1);
            return;
        }
        if (gLeft >= gUp && gLeft >= gDown){
            all_Location[Main.moveColLeft(col,1)][row].addPerson(this);
            col = Main.moveColLeft(col,1);
            return;
        }
        if (gUp >= gDown){
            all_Location[col][Main.moveRowUp(row,1)].addPerson(this);
            row = Main.moveRowUp(row,1);
        }else{
            all_Location[col][Main.moveRowDown(row,1)].addPerson(this);
            row = Main.moveRowDown(row,1);
        }
    }


    public void set_variables(){
        //reset parameters
        age = 0;
        life_expectancy = randomInt(life_expectancy_min, life_expectancy_max);
        metabolism = randomInt(1,metabolism_max);
        wealth = randomInt(metabolism, 50);
        vision = randomInt(1,vision_max);
    }

    public static void updateClass(Person[] all_People){
        // Add a class to each person on an array, depending on ther current 
        // wealth. For that first gets the wealthiest person/
        
        int max_wealth_now = 0;
        for(Person p: all_People){
                if (max_wealth_now<p.wealth){
                    max_wealth_now = p.wealth;
                }
        }

        // if the person owns less then 1/3 of the weathiest one then is poor
        // if the person owns between 1/3 and 2/3 of the wealthiest is middle
        // if the person owns more than 2/3 of the wealthies is rich

        for(Person p: all_People){
            if (p.wealth <= max_wealth_now/3){
                p.myClass = 'p';
            }else{
                if (p.wealth <= max_wealth_now*2/3){
                    p.myClass = 'm';
                }else{
                    p.myClass = 'r';
                }
            }
        }  

    }

    public void add_wealth (int wealth_optained){
        // method to add wealth from the harvest
        wealth += wealth_optained;
    }


    public int randomInt( int min, int max){
        // Generates a random int between min and max including both
        int range = max-min;
        return (int)(Math.random()*(range+1)+min);
    }



    public static void main(String[] args) {
        // main class for testing

        System.out.println("Create 1 person");
        System.out.println("-----------------------------------------");
        Person per1 = new Person();
    	System.out.println("age: " + per1.age);
        System.out.println("life_expectancy: " + per1.life_expectancy);
        System.out.println("metabolism: " + per1.metabolism);
        System.out.println("wealth: " + per1.wealth);
        System.out.println("vision:" + per1.vision);
        System.out.println("-----------------------------------------");
        System.out.println("Updating Person");
        per1.Update();
        System.out.println("-----------------------------------------");
        System.out.println("age: " + per1.age);
        System.out.println("life_expectancy: " + per1.life_expectancy);
        System.out.println("metabolism: " + per1.metabolism);
        System.out.println("wealth: " + per1.wealth);
        System.out.println("vision:" + per1.vision);
    }

}
























