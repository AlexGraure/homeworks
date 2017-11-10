function p = evaluate(test_path,w,hist,count_bins)
  %se genereaza matricea de test X si vectorul t
  [X t] = preprocess(test_path,hist,count_bins);
  %se extrag dimensiunile matricei X
  [m n] = size(X);
  %se construieste X tilda, cu 1 pe ultima coloana adaugata
  Xt = zeros(m, n+1);
  Xt(:,1:n) = X(1:m,1:n);
  Xt(1:m, n+1) = 1;
  y = zeros(1,m);
  for i = 1:m
    x = Xt(i,:);%linie din Xt
    y(i) = w' * x';
  endfor
  corect = 0;
  for i = 1:m
    %se verifica precizia
    if y(i) >= 0
      corect++;
    endif
  endfor
  p = (corect/m)*100;%se returneaza procentul
endfunction