R = csvread('rich.csv');
M = csvread('middle.csv');
P = csvread('poor.csv');
x = [1:length(R)];
plot(x,R,'b',x,P,'r',x,M,'g');