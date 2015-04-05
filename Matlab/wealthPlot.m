R = csvread('rich.csv')
M = csvread('middle.csv')
P = csvread('poor.csv')
x = [0:100]
plot(x,R,'b',x,P,'r',x,M,'g')