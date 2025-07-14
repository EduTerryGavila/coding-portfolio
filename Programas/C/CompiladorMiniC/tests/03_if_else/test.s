##################
# Seccion de datos
	.data

$str1:
	.asciiz "a=6"
$str2:
	.asciiz "a==4"
$str3:
	.asciiz "a==5"
$str4:
	.asciiz "Lo siento, no conozco el valor de la variable A."
_a:
	.word 0

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 5
	sw $t0, _a
	lw $t0, _a
	li $t1, 6
	xor $t0, $t0, $t1
	sltiu $t0, $t0, 1
	beqz $t0, $l5
	li $v0, 4
	la $a0, $str1
	syscall
	b $l6
$l5:
	lw $t1, _a
	li $t2, 4
	xor $t1, $t1, $t2
	sltiu $t1, $t1, 1
	beqz $t1, $l3
	li $v0, 4
	la $a0, $str2
	syscall
	b $l4
$l3:
	lw $t2, _a
	li $t3, 5
	xor $t2, $t2, $t3
	sltiu $t2, $t2, 1
	beqz $t2, $l1
	li $v0, 4
	la $a0, $str3
	syscall
	b $l2
$l1:
	li $v0, 4
	la $a0, $str4
	syscall
$l2:
$l4:
$l6:

##################
# Final: exit
	li $v0, 10
	syscall
