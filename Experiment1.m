People = 100;
vision_max = 1;
metabolism_max = 1;
life_expectancy_min = 100;
life_expectancy_max = 100;
percent_best_land = 25;
grain_grow_interval = 1;
num_grain_grown = 10;
Ticks_to_Run = 1000;	

meanR = [];
meanM = [];
meanP = [];

range = 1:15;

for vision_max = range
    system(['java Main ' num2str(People) ' ' num2str(vision_max) ' ' num2str(metabolism_max) ' ' num2str(life_expectancy_min) ' ' num2str(life_expectancy_max) ' ' num2str(percent_best_land) ' ' num2str(grain_grow_interval) ' ' num2str(num_grain_grown) ' ' num2str(Ticks_to_Run)]);
R = csvread('Matlab/rich.csv');
M = csvread('Matlab/middle.csv');
P = csvread('Matlab/poor.csv');
meanR = [meanR mean(R)];
meanM = [meanM mean(M)];
meanP = [meanP mean(P)];
end

x = range;
plot(x,meanR,'b',x,meanP,'r',x,meanM,'g')