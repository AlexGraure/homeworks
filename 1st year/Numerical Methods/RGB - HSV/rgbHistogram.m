function RGB = rgbHistogram(path, count_bins)
  %se citeste imaginea
  image = imread(path);
  
  %se declara vectorul returnat de functie
  RGB = zeros(1,3*count_bins);
  %se extrag dimensiunile matricei
  [m n p] = size(image);

  interval = 256/count_bins;%valoarea cu ajutorul careia se creeaza intervalul dorit
  
  %se creeaza 3 vectori coloana ce contin valorile pentru rosu, verde si albastru
  R(:,:,1) = image(:,:,1);
  Rx=reshape(R',1,m*n);
  X(:,1) = Rx;
  G(:,:,1) = image(:,:,2);
  Gx=reshape(G',1,m*n);
  X(:,2) = Gx;
  B(:,:,1) = image(:,:,3);
  Bx=reshape(B',1,m*n);
  for i = 1:count_bins
    %se verifica intervalul si se calculeaza numarul elementelor
    %incluse in intervalul respectiv, ce se gasesc in cei trei
    %vectori
    RGB(i) = sum(Rx >= ((i-1)*interval) & Rx < (i*interval ));
    RGB(i+count_bins) = sum(Gx >= ((i-1)*interval) & Gx < (i*interval ));
    RGB(i+2*count_bins) = sum(Bx >= ((i-1)*interval) & Bx < (i*interval ));
  endfor
endfunction