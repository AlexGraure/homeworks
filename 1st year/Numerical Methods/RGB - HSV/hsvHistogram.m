function HSV = hsvHistogram(path, count_bins)
  %se citeste imaginea
  image = imread(path);
  %se initializeaza cu 0 vectorul ce va fi returnat de functie
  HSV = zeros(1,count_bins*3);
  %se extrag dimensiunile imaginilor
  [m n p] = size(image);
  %se initializeaza cu 0 trei vectori ce corespund calorile HSV
  H = zeros(m*n,1);
  S = zeros(m*n, 1);
  V = zeros(m*n,1);

  interval = 101/count_bins;%valoare cu ajutorul careia se creeaza intervalul
  
  %se creeaza 3 vectori coloana ce contin valorile pentru rosu, verde si albastru
  R(:,:,1) = image(:,:,1);
  Rx=reshape(R',1,m*n);
  G(:,:,1) = image(:,:,2);
  Gx=reshape(G',1,m*n);
  B(:,:,1) = image(:,:,3);
  Bx=reshape(B',1,m*n);
  
  %se imparte fiecare element din cei trei vectori la 256/count_bins
  Rp = double(Rx)./255;
  Gp = double(Gx)./255;
  Bp = double(Bx)./255;
  
  Cmax = max(Rp, Gp);%se calculeaza maximul 
  Cmax = max(Cmax, Bp);%elementelor din fiecare vector
  
  Cmin = min(Rp,Gp);%se calculeaza minimul
  Cmin = min(Cmin, Bp);%elementelor din fiecare vector
  
  delta = Cmax - Cmin;
  a = find( delta == 0);%se gasesc pozitiile elementelor din delta
  %care sunt egale cu 0
  z = find (delta != 0);%se gasesc pozitiile elementelor din delta
  %care sunt diferite de 0
  H(a) = 1;%elementele din H de pe pozitiile elementelor din delta
  %care sunt 0 devin 1, ca sa se evite impartirea lui 0
  delta(a) = 1;%elementele din delta care sunt egale cu0 devin 1
  %ca sa se evite impartirea la 0
  aux= find(Cmax == Rp);%se gasesc pozitiile elementelor din Cmax 
  %care sunt egale cu elementele din Rp de pe aceleasi pozitii
  H(aux) = 60*(mod((Gp(aux) - Bp(aux))./delta(aux),6));
  aux = find(Cmax == Gp);%se gasesc pozitiile elementelor din Cmax 
  %care sunt egale cu elementele din Gp de pe aceleasi pozitii
  H(aux) = 60*((Bp(aux) - Rp(aux))./delta(aux) + 2);
  aux = find(Cmax == Bp);%se gasesc pozitiile elementelor din Cmax 
  %care sunt egale cu elementele din Bp de pe aceleasi pozitii
  H(aux) = 60*((Rp(aux) - Gp(aux))./delta(aux) + 4);
  H = (H/360)*100;
  aux = find(Cmax == 0);%se gasesc elementele din Cmax egale cu 0
  dif = find(Cmax != 0);%si diferite de 0
  S(aux) = 0;%elementele din S ce corespund pozitiilor elemententelor 
  %din Cmax care sunt egale cu 0 devin 0
  S = 100 * (delta(dif)./Cmax(dif));%se impart doar elementele diferite
  %de 0
  V = 100 * Cmax;
  H(a) = 0;%elemtenele de la inceput facute 1, revin la valoarea 0
  for i = 1:count_bins
    %se verifica intervalul si se calculeaza numarul elementelor
    %incluse in intervalul respectiv, ce se gasesc in cei trei
    %vectori 
    HSV(i) = sum(H >= ((i-1)*interval) & H < (i*interval ));
    HSV(i+count_bins) = sum(S >= ((i-1)*interval) & S < (i*interval ));
    HSV(i+2*count_bins) = sum(V >= ((i-1)*interval) & V < (i*interval ));
  endfor 
endfunction