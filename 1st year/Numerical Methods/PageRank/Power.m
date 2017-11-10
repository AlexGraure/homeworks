function R = Iterative(nume, d, eps)
output_precision(6);
  file = fopen(nume, "r");
  N = fgetl(file);
  N = str2num(N);
  E = ones(N); 
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
  P = d*M + ((1-d)/N)*E;
  for i = 1:N
    R = P * R_init;
    conditie = abs(R - R_init);
    if conditie < eps
      return;
    endif
    R_init = R;
  endfor
  fclose(file);
endfunction
