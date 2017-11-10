function [ X t ]= preprocess(path_to_dir,hist,count_bins)
  M = 3*count_bins;
  %se creeaza calea catre directorul ce contine imaginilor cu pisici
  cats_path = fullfile(path_to_dir,'cats','*.jpg');
  
  %se creeaza calea catre directorul care nu contine imaginilor fara pisici
  not_cats_path = fullfile(path_to_dir,'not_cats','*.jpg');
  
  %se creeaza doua structuri ce contin numele imaginilor
  %atat celor cu pisici, cat si celor fara pisici
  imgs_cats = dir(cats_path);
  imgs_not_cats = dir(not_cats_path);
  %se determina numarul imaginilor cu/fara pisici
  N1 = length(imgs_cats);
  N2 = length(imgs_not_cats);
  N = N1 + N2;%se calculeaza numarul total de imagini
  %se creeaza vectorul ce contine informatia referitoare la continul imaginilor
  %1 -> pisici, -1 -> fara pisici
  t = zeros(1,N);
  t(1,1:N1) = 1;
  t(1,N1+1:N) = -1;
  %se initializeaza cu 0 matricea returnata
  X = zeros(N,M);
  %se realizeaza histograma fiecarei imagini (cu/fara pisici)
  %in functie de tipul ales (hist)
  if strcmp(hist,'RGB') == 1
    for i = 1:N1
      %se creeaza calea catre fiecare imagine
      img_path1 = fullfile(path_to_dir,'cats',imgs_cats(i).name);
      X(i,:) = rgbHistogram(img_path1,count_bins);
    endfor
    for i = 1:N2
      img_path2 = fullfile(path_to_dir,'not_cats',imgs_not_cats(i).name);
      X(i+N1,:) = rgbHistogram(img_path2,count_bins);
    endfor
  else  for i = 1:N1
          img_path1 = fullfile(path_to_dir,'cats',imgs_cats(i).name);
          X(i,:) = hsvHistogram(img_path1,count_bins);
        endfor
        for i = 1:N2
          img_path2 = fullfile(path_to_dir,'not_cats',imgs_not_cats(i).name);
          X(i+N1,:) = hsvHistogram(img_path2,count_bins);
        endfor
  endif
endfunction