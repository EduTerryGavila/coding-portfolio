##################
# Seccion de datos
	.data

$str1:
	.asciiz "Suma de 500 primeros numeros pares: "
_suma:
	.word 0
_j:
	.word 0

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $t0, 0
	li $t1, 1000
	li $t2, 2
$l1:
	bgt $t0, $t1, $l2
	sw $t0, _j
	lw $t3, _suma
	lw $t4, _j
	add $t3, $t3, $t4
	sw $t3, _suma
	add $t0, $t0, $t2
	b $l1
$l2:
	li $v0, 4
	la $a0, $str1
	syscall
	lw $t0, _suma
	li $v0, 1
	move $a0, $t0
	syscall

##################
# Final: exit
	li $v0, 10
	syscall
