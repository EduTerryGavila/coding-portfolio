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
	li $t1, 20
	slt $t0, $t0, $t1
	beqz $t0, $l2
	lw $t1, _a
	li $v0, 1
	move $a0, $t1
	syscall
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t1, _a
	li $t2, 2
	add $t1, $t1, $t2
	sw $t1, _a
	b $l1
$l2:

##################
# Final: exit
	li $v0, 10
	syscall
