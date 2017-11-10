function [ w ]= learn(X, t)
  %se initializeaza cu 0 vectorul returnat
  w = zeros(1, length(t) + 1);
  %se extrag dimeniunile matricei X
  [m n] = size(X);
  %se construieste matricea X tilda, cu 1 pe ultima coloana adaugata
  Xt = zeros(m, n+1);
  Xt(:,1:n) = X;
  Xt(1:m, n+1) = 1;
  %se factorizeaza matricea Xt prin procedeul Gram Schmidt
  A = Xt'*Xt;
  [L U] = crout(A);
  Uw = SIT(L,t);
  w=SST(U',Uw);

endfunction
