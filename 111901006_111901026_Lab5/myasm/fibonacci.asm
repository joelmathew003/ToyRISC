	.data
n:
	10
	.text
main:
	load %x0, $n, %x3
	add %x0, %x0, %x4
	addi %x0, 1, %x5
	addi %x0, 0, %x6
	addi %x0, 65535, %x9
	store %x6, 0, %x9
	subi %x9, 1, %x9
	add %x0, %x5, %x6
	store %x6, 0, %x9
	addi %x0, 2, %x11
loop:
	add %x4, %x5, %x6
	add %x0, %x5, %x4
	add %x0, %x6, %x5
	subi %x9, 1, %x9
	store %x6, 0, %x9
	addi %x11, 1, %x11
	beq %x11, %x3, endl
	jmp loop
endl:
	end
