##################
# Seccion de datos
	.data

$str1:
	.asciiz "Suma: "
$str2:
	.asciiz "\n"
$str3:
	.asciiz "Resta: "
$str4:
	.asciiz "\n"
$str5:
	.asciiz "Multiplicacion: "
$str6:
	.asciiz "\n"
$str7:
	.asciiz "Division: "
$str8:
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
	li $t0, 5
	sw $t0, _a
	li $t0, 2
	sw $t0, _b
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t0, _a
	lw $t1, _b
	add $t0, $t0, $t1
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str2
	syscall
	li $v0, 4
	la $a0, $str3
	syscall
	lw $t0, _a
	lw $t1, _b
	sub $t0, $t0, $t1
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str4
	syscall
	li $v0, 4
	la $a0, $str5
	syscall
	lw $t0, _a
	lw $t1, _b
	mul $t0, $t0, $t1
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str6
	syscall
	li $v0, 4
	la $a0, $str7
	syscall
	lw $t0, _a
	lw $t1, _b
	div $t0, $t0, $t1
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
	la $a0, $str8
	syscall

##################
# Final: exit
	li $v0, 10
	syscall
