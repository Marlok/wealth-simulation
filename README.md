# wealth-simulation
Simulation of wealth distribution

The wealth distribution model is a representation of where the wealth in
a society is distributed, and how this wealth distribution changes
over time.

This program creates 3 csv files in the directory Matlab, the files are
called poor.csv, middle.csv and rich.csv. In the directory Matlab, a script
is included to plot this model.
Each file contains the number if poor, middle and rich people between
each tick.

The program accept initial parameters for:

 *	#People                 (2-1000) Number of people in the simulation
 *	#vision_max  			(1-15) Max range in one person vision
 *  #metabolism_max			(1 - 25) Max value of metabolism
 *	#life_expectancy_min 	(1-100) Min ticks a person will live   
 *	#life_expectancy_max 	(1-100) Max ticks a person can live 
 *	#percent_best_land 		(5% - 25%) Porcentage of land with many grains
 *	#grain_grow_interval 	(1-10) How often grain grows
 *	#num_grain_grown        (1-10) How much grain is grown each time
 *	#Ticks_to_Run			(0 - ...) Amount of ticks in the simulation


File list
------------
 * 	makefile			Compile all the needed classes to use
 *	Main.jave			Main model
 *	Location.java	    Class with methods and information for each location
 *	Person.java		    Class with methods and information for each person
 * 	WealthPlot.m 		Matlab code to plot changes on class on ticks
 * 	Experiment1.m       Matlab code to run and plot experiment 1  
 * 	Experiment2.m       Matlab code to run and plot experiment 2  

Program can be built using
------------

% make

Program is run with
------------

  % java Main

 or

 % java Main #People #vision_max  #metabolism_max #life_expectancy_min
 										#life_expectancy_max #percent_best_land
 										#grain_grow_interval #Ticks_to_Run

 % java Main 250 5 15 1 83 10 4 10 1000

Plot csv files 
------------

In the Directory Matlab included is a file WealthPlot.m to plot changes on class
on ticks. To run this script, In Matlab locate the directory and run the script.


Experiment are run in Matlab to show plots
------------
To run experiment first compile the files and then run  Experiment1.m or
Experiment2.m  with Matlab.



------------
This program follow the NetLogo model for Wealth Distribution with Copyright
1998 Uri Wilensky.
