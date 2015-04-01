/*************************************************************************
 *  Compilation:  javac Person.java
 *  Execution:    java Person
 *
 *  Have the information of one location, amount of grains and number of 
 *	persons currently on it.
 *
 *  % java Test
 *  Test, Test
 *
 *  These 17 lines of text are comments. They are not part of the program;
 *  they serve to remind us about its properties. The first two lines tell
 *  us what to type to compile and test the program. The next line describes
 *  the purpose of the program. The next few lines give a sample execution
 *  of the program and the resulting output. We will always include such 
 *  lines in our programs and encourage you to do the same.
 *
 *************************************************************************/
  
public class Person {

    public static int life_expectancy_max;
    public static int life_expectancy_min;
    public static int metabolism_max;
    public static int vision_max;

    public static int max_wealth_now = 50;
    public static int min_wealth_now = 0;


	public int age;             //Current age of people.
    public int wealth;          //Current amount of grains owned.
    public int life_expectancy; //Until which age this person will live.
    public int metabolism;      //How much grains this person needs to eat.
    public int vision;          //How far this person can look for grains. 

// methods { isDead( ), updateWealthWith(float), getWealth()}

    public Person(){
        set_variables();
    }

    public void set_variables(){
        //reset parameters
        age = 0;
        life_expectancy = randomInt(life_expectancy_min, life_expectancy_max);
        metabolism = randomInt(1,metabolism_max);
        wealth = randomInt(min_wealth_now,max_wealth_now);
        //wealth = randomInt(metabolism, 50);
        vision = randomInt(1,vision_max);
    }


    public int randomInt( int min, int max){
        int range = max-min;
        return (int)(Math.random()*(range+1)+min);
    }

    public void Update(){
        wealth = wealth - metabolism;
        age = age + 1 ; 
        if (age >= life_expectancy || wealth < 0) { // Checking if is Dead                                            
            set_variables();                     // Reset parameters
        }else{
            if (max_wealth_now < wealth){ 
                max_wealth_now = wealth;
            }else if (min_wealth_now > wealth){
                min_wealth_now = wealth;
            }
        }
    }

    public void add_wealth (int wealth_optained){
        wealth = wealth + wealth_optained;
    }

    

    public void getGrains (){
		System.out.println("Hello, World");
	}


    public static void main(String[] args) {
    	Person loc1 = new Person();
    	System.out.println(loc1.randomInt(0,1));
    }
}
























