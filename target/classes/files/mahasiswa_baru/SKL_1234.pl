bawahanlangsung(adi,burhan).
bawahanlangsung(burhan,bahrun).
bawahanlangsung(burhan,bisrin).
bawahanlangsung(bahrun,fahri).
bawahanlangsung(bahrun,farah).
bawahanlangsung(bisrin,ferdi).

atasanlangsung(BW,AT):-bawahanlangsung(AT,BW).
bawahan(AT,BW):-bawahanlangsung(AT,BW).
bawahan(AT,BW):-bawahanlangsung(AT,BW1),bawahan(BW1,BW).
