% inversa prin partitionare
function X = inversa(A)
	[n n] = size(A); % dimensiuni
	% cazuri de baza n = 1, 2
	if n == 1
		X = 1/A(1)(1);
		return;
	endif
	if n == 2
		X = A^-1;
		return;
	endif
	% trebuie sa aleg A1 si A4 a.i sa fie inversabile
	% fie k dim lui A1, n-k dim lui A4.
	% k poate fi de la 1 la n-1
	k = 1;
	% asamblez A1 si A4
	A1 = A(1:k, 1:k);
	A4 = A(k+1:n, k+1:n);
	% daca cel putin una dintre ele nu este inversabila, maresc k
	while  det(A1) == 0 || det(A4) == 0  
		k++;
		A1 = A(1:k, 1:k);
		A4 = A(k+1:n, k+1:n);
	endwhile
	% asamblez celelalte matrici
	%A1
	A2=A(k+1:n, 1:k);
	A3=A(1:k, k+1:n);
	%A4
	% fie A11 = A1^-1, A41 = A4^-1
	A11 = inversa(A1);
	A41 = inversa(A4);
	% asamblez componentele inversei
	X1 = inversa(A1 - A3 * A41 * A2);
	X2 = - A41 * A2 * X1;
	X4 = inversa(A4 - A2 * A11 * A3);
	X3 = - A11 * A3 * X4;
	% asamblez inversa
	X =[X1 X3; X2 X4];
endfunction