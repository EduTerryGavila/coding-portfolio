##################
# Seccion de datos
	.data

$str1:
	.asciiz "a="
$str2:
	.asciiz "\n"
$str3:
	.asciiz "b="
$str4:
	.asciiz "\n"
$str5:
	.asciiz "c="
$str6:
	.asciiz "\n"
_a:
	.word 0
_b:
	.word 0
_c:
	.word 0

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 0
	li $t1, 10
	li $t2, 2
$l1:
	bgt $t0, $t1, $l2
	sw $t0, _a
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t3, _a
	li $v0, 1
	move $a0, $t3
	syscall
	li $v0, 4
	la $a0, $str2
	syscall
	add $t0, $t0, $t2
	b $l1
$l2:
	li $t0, 0
	li $t1, 5
$l3:
	bgt $t0, $t1, $l4
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
	li $t0, 3
	lw $t1, _c
	li $t2, 20
	sgt $t1, $t1, $t2
$l5:
	lw $t1, _c
	li $t2, 20
	sgt $t1, $t1, $t2
	bnez $t1, $l6
	sw $t0, _c
	li $v0, 4
	la $a0, $str5
	syscall
	lw $t2, _c
	li $v0, 1
	move $a0, $t2
	syscall
	li $v0, 4
	la $a0, $str6
	syscall
	lw $t2, _c
	li $t3, 3
	mul $t2, $t2, $t3
	sw $t2, _c
	addi $t0, $t0, 1
	b $l5
$l6:

##################
# Final: exit
	li $v0, 10
	syscall
