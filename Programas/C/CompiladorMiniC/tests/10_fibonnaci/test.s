##################
# Seccion de datos
	.data

$str1:
	.asciiz "1"
$str2:
	.asciiz "\n"
_n:
	.word 0
_fib:
	.word 0
_prevFib:
	.word 0
_i:
	.word 0
_temp:
	.word 0

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 20
	sw $t0, _n
	li $t0, 1
	sw $t0, _fib
	li $t0, 1
	sw $t0, _prevFib
	lw $t0, _n
	li $t1, 1
	sle $t0, $t0, $t1
	beqz $t0, $l3
	li $v0, 4
	la $a0, $str1
	syscall
	b $l4
$l3:
	li $t1, 2
	lw $t2, _n
$l1:
	bgt $t1, $t2, $l2
	sw $t1, _i
	lw $t3, _fib
	sw $t3, _temp
	lw $t3, _fib
	lw $t4, _prevFib
	add $t3, $t3, $t4
	sw $t3, _fib
	lw $t3, _temp
	sw $t3, _prevFib
	lw $t3, _fib
	li $v0, 1
	move $a0, $t3
	syscall
	li $v0, 4
	la $a0, $str2
	syscall
	addi $t1, $t1, 1
	b $l1
$l2:
$l4:

##################
# Final: exit
	li $v0, 10
	syscall
