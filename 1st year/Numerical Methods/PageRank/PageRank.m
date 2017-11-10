function PageRank(nume,d,eps)
  output_precision(6);
  file = fopen(nume, "r");
  N = fgetl(file);
  N = str2num(N);
  R1 = Iterative(nume,d,eps);
  R2 = Algebraic(nume,d);
  R3 = Power(nume,d,eps);
  M = N+2;
  for i=1:M
    L = fgetl(file);
    L = str2num(L);
    if i == M-1
      val1 = L;
    else  if i == M
            val2 = L;
          endif
    endif
  endfor    
  fclose(file);
  PR1 = R2;
  for i=1:N-1
    for j = i+1: N
      if PR1(i) < PR1(j)
        aux = PR1(j);
        PR1(j) = PR1(i);
        PR1(i) = aux;
      endif
    endfor
  endfor
  
  for i = 1:N
    for j = 1:N
      if R2(i) == PR1(j)
        k(j) = i;
      endif
    endfor
  endfor
  k=reshape(k,N,1);
  for i=1:N-1
    if k(i) == k(i+1)
      k(i)= k(i)-1;
    endif
  endfor
  
  nume_out = strcat(nume,'.out');
  output = fopen(nume_out,'w');
  fprintf(output,'%d\n\n',N);
    fprintf(output,"%d\n",R1(1:N));
  fprintf(output,'\n');
    fprintf(output,"%d\n",R2(1:N));
  fprintf(output,'\n');
    fprintf(output,"%d\n",R3(1:N));
  fprintf(output,'\n');
  for i = 1:N
    fprintf(output,'%d %d %d',i,k(i),Apartenenta(PR1(i),val1,val2));
    fprintf(output,'\n');
  endfor
  fclose(output);
endfunction