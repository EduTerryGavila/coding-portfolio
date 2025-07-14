###################
# Datos
	.data

$str1:
	.asciiz "Factorial("
$str2:
	.asciiz ") = "
_n:
	.word 0
_factorial:
	.word 0
_i:
	.word 0
###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 10
	sw $t0, _n
	li $t0, 1
	sw $t0, _factorial
	li $t0, 1
	sw $t0, _i
$l1:
	lw $t0, _i
	lw $t1, _n
	sle $t0, $t0, $t1
	beqz $t0, $l2
	lw $t1, _factorial
	lw $t2, _i
	mul $t1, $t1, $t2
	sw $t1, _factorial
	lw $t1, _i
	li $t2, 1
	add $t1, $t1, $t2
	sw $t1, _i
	b $l1
$l2:
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t0, _n
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str2
	syscall
	lw $t0, _factorial
	li $v0, 1
	move $a0, $t0
	syscall
# Exit
	li $v0, 10
	syscall
