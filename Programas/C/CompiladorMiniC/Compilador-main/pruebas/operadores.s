###################
# Datos
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
$str9:
	.asciiz "\n"
$str10:
	.asciiz "\n"
$str11:
	.asciiz "a==2"
$str12:
	.asciiz "Valor final de a: "
_a:
	.word 0
_b:
	.word 0
###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 4
	sw $t0, _a
	li $t0, 3
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
	lw $t0, _a
	li $t1, 2
	xor $t0, $t0, $t1
	beqz $t0, $l8
	lw $t1, _a
	li $t2, 5
	slt $t1, $t1, $t2
	beqz $t1, $l6
$l1:
	lw $t2, _a
	li $t3, 2
	slt $t2, $t2, $t3
	beqz $t2, $l2
	lw $t3, _a
	li $v0, 1
	move $a0, $t3
	syscall
	li $v0, 4
	la $a0, $str9
	syscall
	lw $t3, _a
	li $t4, 1
	add $t3, $t3, $t4
	sw $t3, _a
	b $l1
$l2:
	b $l7
$l6:
	lw $t2, _a
	li $t3, 2
	sgt $t2, $t2, $t3
	beqz $t2, $l5
$l3:
	lw $t3, _a
	li $t4, 5
	sgt $t3, $t3, $t4
	beqz $t3, $l4
	lw $t4, _a
	li $v0, 1
	move $a0, $t4
	syscall
	li $v0, 4
	la $a0, $str10
	syscall
	lw $t4, _a
	li $t5, 1
	sub $t4, $t4, $t5
	sw $t4, _a
	b $l3
$l4:
$l5:
$l7:
	b $l9
$l8:
	li $v0, 4
	la $a0, $str11
	syscall
$l9:
	li $v0, 4
	la $a0, $str12
	syscall
	lw $t0, _a
	li $v0, 1
	move $a0, $t0
	syscall
# Exit
	li $v0, 10
	syscall
