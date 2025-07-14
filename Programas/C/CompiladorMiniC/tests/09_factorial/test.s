##################
# Seccion de datos
	.data

$str1:
	.asciiz "Fact("
$str2:
	.asciiz ") = "
_n:
	.word 0
_fact:
	.word 0
_i:
	.word 0

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 5
	sw $t0, _n
	li $t0, 1
	sw $t0, _fact
	li $t0, 1
	sw $t0, _i
$l1:
	lw $t0, _i
	lw $t1, _n
	sle $t0, $t0, $t1
	beqz $t0, $l2
	lw $t1, _fact
	lw $t2, _i
	mul $t1, $t1, $t2
	sw $t1, _fact
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
	lw $t0, _fact
	li $v0, 1
	move $a0, $t0
	syscall

##################
# Final: exit
	li $v0, 10
	syscall
