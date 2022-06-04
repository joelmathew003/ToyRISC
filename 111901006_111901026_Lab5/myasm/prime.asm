	.data
a:
	17
	.text
main:
	load %x0, $a, %x3
	addi %x0, 2, %x6
loop:
	div %x3, %x6, %x5
	mul %x5, %x6, %x7
	beq %x3, %x7, fail
	addi %x6, 1, %x6
	beq %x6, %x3, endl
	jmp loop
fail:
	subi %x0, 1, %x10
	end
endl:
	addi %x0, 1, %x10
	end	
