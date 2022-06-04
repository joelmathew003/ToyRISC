	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	divi %x3, 2, %x4
	muli %x4, 2, %x5
	beq %x3, %x5, even
	bne %x3, %x5, odd
even:
	subi %x0, 1, %x10
	end
odd:
	addi %x0, 1, %x10
	end
