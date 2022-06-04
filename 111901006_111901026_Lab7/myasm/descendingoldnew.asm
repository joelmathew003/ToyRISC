	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $a, %x3
	subi %x0, 1, %x10
	load %x0, $n, %x4	
loopi:
	addi %x10, 1, %x10
	subi %x0, 1, %x11
	beq %x10, %x4, finish
loopj:
	addi %x11, 1, %x11
	sub %x4, %x10, %x9
	subi %x9, 1, %x13
	addi %x11, 1, %x12
	beq %x11, %x13, endl
	load %x11, $a, %x5
	load %x12, $a, %x6
	blt %x5, %x6, swap
	jmp loopj
endl:
	jmp loopi
swap:
	store %x6, $a, %x11
	store %x5, $a, %x12
	jmp loopj 
finish:
	end
