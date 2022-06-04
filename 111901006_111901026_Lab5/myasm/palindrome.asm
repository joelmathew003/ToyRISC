	.data
a:
	12321
	.text
main:
	load %x0, $a, %x3
	load %x0, $a, %x5
	add %x0, %x0, %x9	
	addi %x0, 1, %x4
loop:
	blt %x5, %x4, endl
	divi %x5, 10, %x6
	muli %x6, 10, %x7
	sub %x5, %x7, %x8
	muli %x9, 10, %x9
	add %x9, %x8, %x9
	divi %x5, 10, %x5
	jmp loop
endl:
	beq %x9, %x3, success
	bne %x9, %x3, fail
success:
	addi %x0, 1, %x10
	end
fail:
	subi %x0, 1, %x10
	end
