function R = Algebraic(nume, d)
output_precision(6);
  file = fopen(nume, "r");
  N = fgetl(file);
  N = str2num(N);
  I = eye(N);
  A = zeros(N,N);
  K = zeros(N,N);
  for i = 1:N
    L = fgetl(file);
    L = str2num(L);
    nod = L(1);
    nr_vec = L(2);
    K(i,i) = nr_vec;
    for j = 3:nr_vec + 2 
      A(nod,L(j)) = 1;
      if nod == L(j)
        K(i,i) = K(i,i) - 1;
      endif
    endfor
    A(i,i) = 0;
  endfor
  K = inversa(K);
  M = (K*A)';
  l(1:N,1) = 1;
  R_init = 1/N*l;
  MDinv = I - d*M;
  for i=i:N
    R = (gram_schmidt(MDinv))*(((1-d)/N) * l);
  endfor
  fclose(file);
endfunction