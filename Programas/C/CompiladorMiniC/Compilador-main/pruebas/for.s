###################
# Datos
	.data

$str1:
	.asciiz "a="
$str2:
	.asciiz "\n"
$str3:
	.asciiz "b="
$str4:
	.asciiz "\n"
_a:
	.word 0
_b:
	.word 0
###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 0
	li $t1, 10
$l1:
	bgt $t0, $t1, $l2
	sw $t0, _a
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t2, _a
	li $v0, 1
	move $a0, $t2
	syscall
	li $v0, 4
	la $a0, $str2
	syscall
	addi $t0, $t0, 1
	b $l1
$l2:
	li $t0, 10
	lw $t1, _b
	li $t2, 15
	sgt $t1, $t1, $t2
$l3:
	lw $t1, _b
	li $t2, 15
	sgt $t1, $t1, $t2
	bnez $t1, $l4
	sw $t0, _b
	li $v0, 4
	la $a0, $str3
	syscall
	lw $t2, _b
	li $v0, 1
	move $a0, $t2
	syscall
	li $v0, 4
	la $a0, $str4
	syscall
	addi $t0, $t0, 1
	b $l3
$l4:
# Exit
	li $v0, 10
	syscall
