m=mobiledev;
m.logging=1;

while(1)

[a,~] = accellog(m);
[b,~] = angvellog(m);

UFT_accel=2.8;                   % Upper Fall Threshold  2.8g
UFT_ang=3.1;                     % Upper Fall Threshold  200 degree's

% receive and judge six data are processed in one loop

MemLen=10;             % The length of motion data memory
motionData=zeros(6,MemLen);      % Motion data memery
memPoint=1;                      % Current processing time point

current=1;
while(memPoint~=0)               % with a period of fall duration
    rx=0;

accel_x = a(current,1);
accel_y = a(current,2);
accel_z = a(current,3);

ang_x = b(current,1);
ang_y = b(current,2);
ang_z = b(current,3);
    
motionData(1,memPoint)=accel_x;     % Acceleration on axis x
motionData(2,memPoint)=accel_y;     % Acceleration on axis y
motionData(3,memPoint)=accel_z;     % Acceleration on axis z

motionData(4,memPoint)=ang_x;       % Angular rate on axis x
motionData(5,memPoint)=ang_y;       % Angular rate on axis y
motionData(6,memPoint)=ang_z;       % Angular rate on axis z

acceleration=sqrt(accel_x^2+accel_y^2+accel_z^2);

angularVelocity = sqrt(ang_x^2+ang_y^2+ang_z^2);

if (acceleration>=UFT_accel&& angularVelocity>=UFT_ang)
    rx=1;
    disp('Fall detected');
end

memPoint=mod(memPoint+1,MemLen);

current=current+1;

if (rx==1)  
 fallDetectFile=fopen('H:\MATLAB\Matlab_algo\SendFallDetectValue.txt','w');  % open file in write mode to store the fall detect
 fprintf(fallDetectFile,'%d',rx); % only write when fall is captured
 fclose(fallDetectFile); %close the file
end

end 

end