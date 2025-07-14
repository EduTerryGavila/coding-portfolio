##################
# Seccion de datos
	.data

$str1:
	.asciiz "\n"
_a:
	.word 0

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 10
	sw $t0, _a
$l1:
	lw $t0, _a
	li $t1, 2
	mul $t0, $t0, $t1
	sw $t0, _a
	lw $t0, _a
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t0, _a
	li $t1, 100
	slt $t0, $t0, $t1
	bnez $t0, $l1

##################
# Final: exit
	li $v0, 10
	syscall
