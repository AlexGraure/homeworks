function y = Apartenenta(x,val1,val2)
  b = val1/(val2 - val1);
  a = (-1)/(val2 - val1);
  if x >= 0 & x < val1
    y = 0;
  else  if x >= val1 & x <= val2
          y = a*x+b;
        else  if x > val2 & x <= 1
                y = 1;
              endif
        endif
  endif
  if y != 0
    y = abs(y);
  endif
endfunction