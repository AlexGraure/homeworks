function INV = gram_schmidt(A)
	[n n] = size(A);
	Q = zeros(n,n);
	R = zeros(n);
	for j = 1 : n
		for i = 1 : j-1
			R(i,j) = Q(:,i)' * A(:,j);
		endfor
		s = zeros(n,1);
		s = Q(:, 1:j-1) * R(1:j-1, j);
		aux = A(:,j) - s;		
		
		R(j,j) = norm(aux,2);
		Q(:,j) = aux/R(j,j);
	endfor
  for i = 1:n
    INV(1:n,i) = SST(R,Q'(1:n,i));
  endfor
endfunction