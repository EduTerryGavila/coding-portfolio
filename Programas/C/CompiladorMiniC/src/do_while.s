###################
# Datos
	.data

$str1:
	.asciiz "\n"
_n:
	.word 0
###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 10
	sw $t0, _n
$l1:
	lw $t0, _n
	li $t1, 5
	mul $t0, $t0, $t1
	sw $t0, _n
	lw $t0, _n
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t0, _n
	li $t1, 1000
	slt $t0, $t0, $t1
	bnez $t0, $l1
# Exit
	li $v0, 10
	syscall
